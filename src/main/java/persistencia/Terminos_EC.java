/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tecio
 */
@Entity
@Table(name = "Terminos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Terminos.findAll", query = "SELECT t FROM Terminos_EC t"),
    @NamedQuery(name = "Terminos.findByHashTermino", query = "SELECT t FROM Terminos_EC t WHERE t.hashTermino = :hashTermino"),
    @NamedQuery(name = "Terminos.findByNombre", query = "SELECT t FROM Terminos_EC t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Terminos.findByMaxTermFrec", query = "SELECT t FROM Terminos_EC t WHERE t.maxTermFrec = :maxTermFrec"),
    @NamedQuery(name = "Terminos.findByCantDocumentos", query = "SELECT t FROM Terminos_EC t WHERE t.cantDocumentos = :cantDocumentos")})
public class Terminos_EC implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "hashTermino")
    private Integer hashTermino;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "maxTermFrec")
    private Integer maxTermFrec;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantDocumentos")
    private Integer cantDocumentos;

    public Terminos_EC() {
    }

    public Terminos_EC(Integer hashTermino) {
        this.hashTermino = hashTermino;
    }

    public Terminos_EC(int hashCode, String nom, int mtf, int cantDocs) {
        hashTermino  = hashCode;
        nombre = nom;
        maxTermFrec = mtf;
        cantDocumentos = cantDocs;
    }

    public Integer getHashTermino() {
        return hashTermino;
    }

    public void setHashTermino(Integer hashTermino) {
        this.hashTermino = hashTermino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getMaxTermFrec() {
        return maxTermFrec;
    }

    public void setMaxTermFrec(Integer maxTermFrec) {
        this.maxTermFrec = maxTermFrec;
    }

    public Integer getCantDocumentos() {
        return cantDocumentos;
    }

    public void setCantDocumentos(Integer cantDocumentos) {
        this.cantDocumentos = cantDocumentos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hashTermino != null ? hashTermino.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Terminos_EC)) {
            return false;
        }
        Terminos_EC other = (Terminos_EC) object;
        if ((this.hashTermino == null && other.hashTermino != null) || (this.hashTermino != null && !this.hashTermino.equals(other.hashTermino))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Terminos[ hashTermino=" + hashTermino + " ]";
    }
    
}
