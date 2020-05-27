
package Funciones;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import logicaHash.*;
import persistencia.*;

/**
 *
 * @author tecio
 */
public class buscarDocumento {
    private static TerminosJpaController terJpa;

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("doc_PU");
    private static Vocabulario voc = new Vocabulario();

    public static void main(String[] args) {
        String str = "tomi";   
        cargarVocabulario();
        buscarTermino(str);
    }
    
    private static void cargarVocabulario() {
        terJpa = new TerminosJpaController(emf);
        try {
            List<Terminos_EC> listaTerminos = terJpa.findTerminosEntities();
            listaTerminos.stream().map((ter) -> new Termino(ter)).forEachOrdered((t) -> {
                voc.put(t);
            });
        } catch (Exception e) {
            System.out.println("Error al cargar vocabulario. " + e.getMessage());
        }        
    }
    
    private static void buscarTermino(String str){
        ArrayList LD = new ArrayList();
        // mientras que queden terminos sin procesar....   Voy a hacer que solo te pueda meter un termino para que sea mas facil, desp vemos mas terminos
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
