
package Funciones;

import java.io.File;
import java.util.*;
import javax.persistence.*;
import javax.persistence.Persistence;
import logicaHash.*;
import persistencia.*;

/**
 *
 * @author tecio
 * 
 * 
 * TODO 
 *      controlar palabras que no existen
 *      ver que pasa cuando editas un documento
 * 
 * 
 */
public class buscarDocumento {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("doc_PU");
    private static TerminosJpaController terJpa;
    private static PosteoJpaController postJpa;
    private static DocumentosJpaController docJpa;

    // Defino el porcentaje maximo de documentos en los que puede aparecer un termino para que no sea STOPWORD
    private static double porcentaje = 0.5;
    // Variable para el algoritmo de busqueda
    private static boolean encontro = false;
    
    private static Vocabulario voc = new Vocabulario();
    private static SortedMap<Integer, Documento> listDocs = new TreeMap<Integer, Documento>();
    private static SortedSet<Map.Entry<Integer, Documento>> listaDocsOrdenada = new TreeSet<Map.Entry<Integer, Documento>>(
            (Map.Entry<Integer, Documento> o1, Map.Entry<Integer, Documento> o2) -> {
                Documento d1 = o1.getValue();
                Documento d2 = o2.getValue();
                return (int)((d2.getIdr() * 10000) - (d1.getIdr() * 10000));
    });

    public static void main(String[] args) {
        Object[] resultado = buscar("pedro tomi juan", 5);
        System.out.println(imprimirResultado(resultado));
    }
    
    public static Object[] buscar(String str, int r){
        cargarVocabulario();
        buscarTextoIngresado(str, r);
        return listaDocsOrdenada.toArray();
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
        listaDocsOrdenada.addAll(listDocs.entrySet());
    }
        
    // Busca de la lista de posteo de un termino los r docs con mayor frecuencia y los agrega al vocabulario
    private static void buscarTermino(Termino t, int r){
        // Me fijo si es o no una stopWord
        boolean stopWord = validarStopWord(t);
        if(!stopWord){
            postJpa = new PosteoJpaController(emf);
            try {
                List<Posteo_EC> posteo = postJpa.findPosteoForTermino(t, r);
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
        int apariciones;
        try {
            apariciones = ((Termino)voc.getVocabulario().get(t.hashCode())).getCantDocs();
        } catch (Exception e) {
            return false;
        }
        int cantDocs = voc.getCantTotalDocs();
    
        double idfMin = cantDocs/(cantDocs*porcentaje);
        double idfCalc = cantDocs/apariciones;
        
        if(idfCalc == 0){return false;}
        if(idfMin > idfCalc){stopword = true;}        
        return stopword;
    }

    // Carga una lista provisoria con todos los documentos obtenidos para un terino
    private static void cargarListaDocumentos(List<Posteo_EC> posteo) {
        for(Object o : posteo){
            Object[] post = (Object[]) o;
            Documento d = new Documento(post[0].toString());
            Termino ter = new Termino(post[1].toString());
            int frec = (int)post[2];
            int apariciones = voc.getCantTotalDocs();
        try {
            apariciones = ((Termino)voc.getVocabulario().get(ter.hashCode())).getCantDocs();
        } catch (Exception e) { System.out.println("Error al buscar las apariciones de un termino. " + e.getMessage());}
        
            double peso = frec * Math.log(voc.getCantTotalDocs()/apariciones);
            d.setIdr(peso);
            insertarLD(d);
        }
    }

    // Ordena la lista segun el indice de relevancia
    private static void insertarLD(Documento d) {
        try {
            String p = buscarPath(d.getNombre());
            d.setPath(p);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        if(listDocs.get(d.hashCode()) == null){
            listDocs.put(d.hashCode(), d);
        }
        else{
            Documento doc = listDocs.get(d.hashCode());
            d.setIdr(doc.getIdr() + d.getIdr());
            listDocs.replace(doc.hashCode(), doc, d);
        }
    }

    // Devuelve el resultado en un string
    private static String imprimirResultado(Object[] resultado) {
        String str = "";
        for(Object o : resultado)
        {
            str += o.toString();
        }
        return str;
    }
    
    
    private static String buscarPath(String nomDoc){
        // Directorio donde se pueden agregar los archivos
        String rutaInicial  = "D:\\Tomi\\Facultad\\4To\\DLC\\Motor de Busqueda";
        
        String str = buscarPath(rutaInicial, rutaInicial, nomDoc);
        encontro = false;
        return str;
    }
    
    private static String buscarPath(String path0, String path1, String nomDoc){
        String prevPath = path0;
        String finalPath = path1;
        File rutaOriginal = new File(finalPath);
        File[] files = rutaOriginal.listFiles();
        for(File f : files){
            if(f.getName().equals(nomDoc)){
                encontro = true;
                return finalPath.concat("\\" + f.getName());
            }else if(f.isDirectory()){
                finalPath += "\\" + f.getName();
                finalPath = buscarPath(prevPath, finalPath, nomDoc);
                File fi = new File(finalPath);
                if(fi.getName().equals(nomDoc)){
                    break;
                }
            }
        }
        if(encontro){
            return finalPath;
        }
        return prevPath;
    }
}
