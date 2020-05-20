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
@Table(name = "Documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Documentos.findAll", query = "SELECT d FROM Documentos_EC d"),
    @NamedQuery(name = "Documentos.findByHashDocumentos", query = "SELECT d FROM Documentos_EC d WHERE d.hashDocumentos = :hashDocumentos"),
    @NamedQuery(name = "Documentos.findByNombre", query = "SELECT d FROM Documentos_EC d WHERE d.nombre = :nombre")})
public class Documentos_EC implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "hashDocumentos")
    private Integer hashDocumentos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;

    public Documentos_EC() {
    }

    public Documentos_EC(Integer hashDocumentos) {
        this.hashDocumentos = hashDocumentos;
    }

    public Documentos_EC(Integer hashDocumentos, String nombre) {
        this.hashDocumentos = hashDocumentos;
        this.nombre = nombre;
    }

    public Integer getHashDocumentos() {
        return hashDocumentos;
    }

    public void setHashDocumentos(Integer hashDocumentos) {
        this.hashDocumentos = hashDocumentos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hashDocumentos != null ? hashDocumentos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Documentos_EC)) {
            return false;
        }
        Documentos_EC other = (Documentos_EC) object;
        if ((this.hashDocumentos == null && other.hashDocumentos != null) || (this.hashDocumentos != null && !this.hashDocumentos.equals(other.hashDocumentos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Documentos[ hashDocumentos=" + hashDocumentos + " ]";
    }
    
}
