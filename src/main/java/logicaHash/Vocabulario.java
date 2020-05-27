/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicaHash;

import java.util.Hashtable;

/**
 *
 * @author tecio
 */
public class Vocabulario {
    private final Hashtable<Integer, Termino> vocabulario;

    public Vocabulario() {
        this.vocabulario = new Hashtable();
    }
    
    public void put(Termino t, Documento d){
        if(vocabulario.get(t.hashCode()) != null){
            vocabulario.get(t.hashCode()).sumarFrec(d);
        }else{
            vocabulario.put(t.hashCode(), t);
            vocabulario.get(t.hashCode()).sumarFrec(d);
        }
    }
    public void put(Termino t){
        if(vocabulario.get(t.hashCode()) == null){
            vocabulario.put(t.hashCode(), t);
        }
    }
    
    public Hashtable getVocabulario(){
        return vocabulario;
    }

    @Override
    public String toString() {
        return "Vocabulario{" + "vocabulario=" + vocabulario.toString() + '}';
    }
    
    

    
    
    
}
