/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tecio
 */
@Embeddable
public class PosteoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "hashTermino")
    private int hashTer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hashDocumentos")
    private int hashDoc;

    public PosteoPK() {
    }

    public PosteoPK(int hashTer, int hashDoc) {
        this.hashTer = hashTer;
        this.hashDoc = hashDoc;
    }

    public int getHashTer() {
        return hashTer;
    }

    public void setHashTer(int hashTer) {
        this.hashTer = hashTer;
    }

    public int getHashDoc() {
        return hashDoc;
    }

    public void setHashDoc(int hashDoc) {
        this.hashDoc = hashDoc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) hashTer;
        hash += (int) hashDoc;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PosteoPK)) {
            return false;
        }
        PosteoPK other = (PosteoPK) object;
        if (this.hashTer != other.hashTer) {
            return false;
        }
        if (this.hashDoc != other.hashDoc) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.PosteoPK[ hashTer=" + hashTer + ", hashDoc=" + hashDoc + " ]";
    }
    
}
