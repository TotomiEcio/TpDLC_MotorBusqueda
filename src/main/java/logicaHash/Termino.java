/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicaHash;

import java.util.Hashtable;
import java.util.Objects;

/**
 *
 * @author tecio
 */
public class Termino {
    private String nomTerm;
    private Hashtable<Integer, Documento> posteo;
    private int maxTermFrec;

    public Termino(String nomTerm) {
        this.nomTerm = nomTerm;
        this.posteo = new Hashtable();
        maxTermFrec = 0;
    }
    
    public String getNom() {
        return nomTerm;
    }

    public Hashtable getPosteo() {
        return posteo;
    }

    public void setDocumento(Hashtable posteo) {
        this.posteo = posteo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.nomTerm);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Termino other = (Termino) obj;
        if (!Objects.equals(this.nomTerm, other.nomTerm)) {
            return false;
        }
        return true;
    }
    
    
    public void sumarFrec(Documento d){
        if(posteo.get(d.hashCode()) != null){
            posteo.get(d.hashCode()).addCant();
        }else{
            posteo.put(d.hashCode(), d);
            posteo.get(d.hashCode()).addCant();
        }
    }

    @Override
    public String toString() {
        return "\nTermino{" + "nomTerm=" + nomTerm + ", posteo=" + posteo.toString() + ", maxTermFrec=" + maxTermFrec + '}';
    }

    
}
