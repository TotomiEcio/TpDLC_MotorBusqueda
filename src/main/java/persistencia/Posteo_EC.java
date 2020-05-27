/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tecio
 */
@Entity
@Table(name = "Posteo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Posteo.findAll", query = "SELECT p FROM Posteo_EC p"),
    @NamedQuery(name = "Posteo.findByHashTer", query = "SELECT p FROM Posteo_EC p WHERE p.posteoPK.hashTer = :hashTer"),
    @NamedQuery(name = "Posteo.findByHashTerMTF",
            query = "SELECT p FROM Posteo_EC p WHERE p.posteoPK.hashTer = :hashTer ORDER BY p.cant DESC"),
    @NamedQuery(name = "Posteo.findByHashDoc", query = "SELECT p FROM Posteo_EC p WHERE p.posteoPK.hashDoc = :hashDoc"),
    @NamedQuery(name = "Posteo.findByCant", query = "SELECT p FROM Posteo_EC p WHERE p.cant = :cant")})
public class Posteo_EC implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PosteoPK posteoPK;
    @Column(name = "cant")
    private Integer cant;

    public Posteo_EC() {
    }

    public Posteo_EC(PosteoPK posteoPK) {
        this.posteoPK = posteoPK;
    }

    public Posteo_EC(int hashTer, int hashDoc) {
        this.posteoPK = new PosteoPK(hashTer, hashDoc);
    }

    public Posteo_EC(int hashTer, int hashDoc, Integer cant) {
        this.posteoPK = new PosteoPK(hashTer, hashDoc);
        this.cant = cant;
    }

    public PosteoPK getPosteoPK() {
        return posteoPK;
    }

    public void setPosteoPK(PosteoPK posteoPK) {
        this.posteoPK = posteoPK;
    }

    public Integer getCant() {
        return cant;
    }

    public void setCant(Integer cant) {
        this.cant = cant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (posteoPK != null ? posteoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Posteo_EC)) {
            return false;
        }
        Posteo_EC other = (Posteo_EC) object;
        if ((this.posteoPK == null && other.posteoPK != null) || (this.posteoPK != null && !this.posteoPK.equals(other.posteoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Posteo[ posteoPK=" + posteoPK + " ]";
    }
    
}
