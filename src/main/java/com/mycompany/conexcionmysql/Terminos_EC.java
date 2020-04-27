/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.conexcionmysql;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tecio
 */
@Entity
@Table(name = "terminos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Terminos.findAll", query = "SELECT t FROM Terminos t"),
    @NamedQuery(name = "Terminos.insertTermino", query = "INSERT INTO "),
    @NamedQuery(name = "Terminos.findByHashTermino", query = "SELECT t FROM Terminos t WHERE t.hashTermino = :hashTermino"),
    @NamedQuery(name = "Terminos.findByNomTermino", query = "SELECT t FROM Terminos t WHERE t.nomTermino = :nomTermino")})
public class Terminos_EC implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "hashTermino")
    private Integer hashTermino;
    @Basic(optional = false)
    @Column(name = "nomTermino")
    private String nomTermino;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "terminos")
    private Collection<Posteos_EC> posteosCollection;

    public Terminos_EC() {
    }

    public Terminos_EC(Integer hashTermino) {
        this.hashTermino = hashTermino;
    }

    public Terminos_EC(Integer hashTermino, String nomTermino) {
        this.hashTermino = hashTermino;
        this.nomTermino = nomTermino;
    }

    public Integer getHashTermino() {
        return hashTermino;
    }

    public void setHashTermino(Integer hashTermino) {
        this.hashTermino = hashTermino;
    }

    public String getNomTermino() {
        return nomTermino;
    }

    public void setNomTermino(String nomTermino) {
        this.nomTermino = nomTermino;
    }

    @XmlTransient
    public Collection<Posteos_EC> getPosteosCollection() {
        return posteosCollection;
    }

    public void setPosteosCollection(Collection<Posteos_EC> posteosCollection) {
        this.posteosCollection = posteosCollection;
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
        return "com.mycompany.conexcionmysql.Terminos[ hashTermino=" + hashTermino + " ]";
    }
    
}
