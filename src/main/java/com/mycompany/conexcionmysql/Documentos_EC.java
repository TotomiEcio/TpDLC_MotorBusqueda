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
@Table(name = "documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Documentos.findAll", query = "SELECT d FROM Documentos d"),
    @NamedQuery(name = "Documentos.findByHashDocumento", query = "SELECT d FROM Documentos d WHERE d.hashDocumento = :hashDocumento"),
    @NamedQuery(name = "Documentos.findByNomDocumento", query = "SELECT d FROM Documentos d WHERE d.nomDocumento = :nomDocumento")})
public class Documentos_EC implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "hashDocumento")
    private Integer hashDocumento;
    @Basic(optional = false)
    @Column(name = "nomDocumento")
    private String nomDocumento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentos")
    private Collection<Posteos_EC> posteosCollection;

    public Documentos_EC() {
    }

    public Documentos_EC(Integer hashDocumento) {
        this.hashDocumento = hashDocumento;
    }

    public Documentos_EC(Integer hashDocumento, String nomDocumento) {
        this.hashDocumento = hashDocumento;
        this.nomDocumento = nomDocumento;
    }

    public Integer getHashDocumento() {
        return hashDocumento;
    }

    public void setHashDocumento(Integer hashDocumento) {
        this.hashDocumento = hashDocumento;
    }

    public String getNomDocumento() {
        return nomDocumento;
    }

    public void setNomDocumento(String nomDocumento) {
        this.nomDocumento = nomDocumento;
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
        hash += (hashDocumento != null ? hashDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Documentos_EC)) {
            return false;
        }
        Documentos_EC other = (Documentos_EC) object;
        if ((this.hashDocumento == null && other.hashDocumento != null) || (this.hashDocumento != null && !this.hashDocumento.equals(other.hashDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.conexcionmysql.Documentos[ hashDocumento=" + hashDocumento + " ]";
    }
    
}
