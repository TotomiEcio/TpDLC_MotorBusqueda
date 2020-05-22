
package Funciones;

import java.util.ArrayList;
import java.util.Hashtable;
import logicaHash.*;

/**
 *
 * @author tecio
 */
public class BuscarDocumento {
    Vocabulario voc = new Vocabulario();
    
    public static void main(String[] args) {
        String str = "Hola tomas";   
        cargarVocabulario();
        buscarTermino(str);
    }
    
    private static void cargarVocabulario() {
        Hashtable posteo = new Hashtable();
        
        // TODO cargar el vocabulario que es parecido al metodo del scanner. 
        
    }
    
    private static void buscarTermino(String str){
        ArrayList LD = new ArrayList();
        // mientras que queden terminos sin procesar....   Voy a hacer que solo te pueda meter un termino para que sea mas facil, desp vemos mas terminos
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
