/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.conexcionmysql;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tecio
 */
@Entity
@Table(name = "posteos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Posteos.findAll", query = "SELECT p FROM Posteos p"),
    @NamedQuery(name = "Posteos.findByHashTer", query = "SELECT p FROM Posteos p WHERE p.posteosPK.hashTer = :hashTer"),
    @NamedQuery(name = "Posteos.findByHashDoc", query = "SELECT p FROM Posteos p WHERE p.posteosPK.hashDoc = :hashDoc"),
    @NamedQuery(name = "Posteos.findByCant", query = "SELECT p FROM Posteos p WHERE p.cant = :cant")})
public class Posteos_EC implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PosteosPK_EC posteosPK;
    @Basic(optional = false)
    @Column(name = "cant")
    private int cant;
    @JoinColumn(name = "hashDoc", referencedColumnName = "hashDocumento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Documentos_EC documentos;
    @JoinColumn(name = "hashTer", referencedColumnName = "hashTermino", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Terminos_EC terminos;

    public Posteos_EC() {
    }

    public Posteos_EC(PosteosPK_EC posteosPK) {
        this.posteosPK = posteosPK;
    }

    public Posteos_EC(PosteosPK_EC posteosPK, int cant) {
        this.posteosPK = posteosPK;
        this.cant = cant;
    }

    public Posteos_EC(int hashTer, int hashDoc) {
        this.posteosPK = new PosteosPK_EC(hashTer, hashDoc);
    }

    public PosteosPK_EC getPosteosPK() {
        return posteosPK;
    }

    public void setPosteosPK(PosteosPK_EC posteosPK) {
        this.posteosPK = posteosPK;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public Documentos_EC getDocumentos() {
        return documentos;
    }

    public void setDocumentos(Documentos_EC documentos) {
        this.documentos = documentos;
    }

    public Terminos_EC getTerminos() {
        return terminos;
    }

    public void setTerminos(Terminos_EC terminos) {
        this.terminos = terminos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (posteosPK != null ? posteosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Posteos_EC)) {
            return false;
        }
        Posteos_EC other = (Posteos_EC) object;
        if ((this.posteosPK == null && other.posteosPK != null) || (this.posteosPK != null && !this.posteosPK.equals(other.posteosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.conexcionmysql.Posteos[ posteosPK=" + posteosPK + " ]";
    }
    
}
