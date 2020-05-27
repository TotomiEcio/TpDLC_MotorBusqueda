
package Funciones;

import java.util.*;
import javax.persistence.*;
import javax.persistence.Persistence;
import logicaHash.*;
import persistencia.*;

/**
 *
 * @author tecio
 */
public class buscarDocumento {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("doc_PU");
    private static TerminosJpaController terJpa;
    private static PosteoJpaController postJpa;
    private static DocumentosJpaController docJpa;
    
    private static Vocabulario voc = new Vocabulario();
    private static Map<Integer, Documento> listDocs = new TreeMap<Integer, Documento>();

    public static void main(String[] args) {
        cargarVocabulario();
        
        String str = "tom and sawyer";   
        int cantDoc = 5;

        buscarTextoIngresado(str, cantDoc);
    }
    // Carga el vocabulario con todos los terminos que hay en la BDD
    private static void cargarVocabulario() {
        terJpa = new TerminosJpaController(emf);
        docJpa = new DocumentosJpaController(emf);
        try {
            List<Terminos_EC> listaTerminos = terJpa.findTerminosEntities();
            listaTerminos.stream().map((ter) -> new Termino(ter)).forEachOrdered((t) -> {
                voc.put(t);
            });
            voc.setCantTotalDocs(docJpa.getDocumentosCount());
        } catch (Exception e) {
            System.out.println("Error al cargar vocabulario. " + e.getMessage());
        }
    }
    
    // Recorre un string y devuelve las listas de posteo de cada uno, excluyendo las stopwords
    private static void buscarTextoIngresado(String str, int r){
        Scanner sc = new Scanner(str);
        while(sc.hasNext()){
            String pal = sc.next();
            Termino t = new Termino(pal);
            buscarTermino(t, r);
        }
    }
        
    // Busca de la lista de posteo de un termino los r docs con mayor frecuencia y los agrega al vocabulario
    private static void buscarTermino(Termino t, int r){
        boolean stopWord = validarStopWord(t);
        if(!stopWord){
            postJpa = new PosteoJpaController(emf);
            try {
                List<Posteo_EC> posteo = postJpa.findPosteoForTermino(t, r);
                System.out.println("Prueba");
                cargarListaDocumentos(posteo);
            } catch (Exception e) {
                System.out.println("Error al buscar la lista de posteo para el termino: " + t.getNom() + " \nEl error es: " + e.getMessage() );
            }
            System.out.println("El termino: '" + t.getNom() + "' NO es stopword");
        }else{System.out.println("El termino: '" + t.getNom() + "' es stopword");}
    }
    
    // Valida si un termino es stopword o no
    private static boolean validarStopWord(Termino t) {
        boolean stopword = false;
        int apariciones = 0;
        try {
            apariciones = ((Termino)voc.getVocabulario().get(t.hashCode())).getCantDocs();
        } catch (Exception e) {
            return false;
        }
        int cantDocs = voc.getCantTotalDocs();
    
        // Defino el porcentaje maximo de documentos en los que puede aparecer un termino para que no sea STOPWORD
        double porcentaje = 0.5;
        double idfMin = cantDocs/(cantDocs*porcentaje);
        double idfCalc = cantDocs/apariciones;
        
        if(idfCalc == 0){return false;}
        if(idfMin > idfCalc){stopword = true;}        
        return stopword;
    }

    private static void cargarListaDocumentos(List<Posteo_EC> posteo) {
        for(Object o : posteo){
            Object[] post = (Object[]) o;
            Documento d = new Documento(post[0].toString());
            Termino ter = new Termino(post[1].toString());
            int frec = (int)post[2];
            int apariciones = voc.getCantTotalDocs();
        try {
            apariciones = ((Termino)voc.getVocabulario().get(ter.hashCode())).getCantDocs();
        } catch (Exception e) { System.out.println("Error al buscar las apariciones de un termino");}
        
            double peso = frec * Math.log(voc.getCantTotalDocs()/apariciones);
            d.setIdr(peso);
            insertarLD(d);
        }
        System.out.println(listDocs.toString());
    }

    private static void insertarLD(Documento d) {
        if(listDocs.get(d.hashCode()) == null){
            listDocs.put(d.hashCode(), d);
        }
        else{
            Documento doc = listDocs.get(d.hashCode());
            d.setIdr(doc.getIdr() + d.getIdr());
            listDocs.replace(doc.hashCode(), doc, d);
        }
    }

}
