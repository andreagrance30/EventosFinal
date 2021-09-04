/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.metre.administracionBase.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "sucursal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sucursal.findAll", query = "SELECT s FROM Sucursal s")
    , @NamedQuery(name = "Sucursal.findById", query = "SELECT s FROM Sucursal s WHERE s.id = :id")
    , @NamedQuery(name = "Sucursal.findByDescripcion", query = "SELECT s FROM Sucursal s WHERE s.descripcion = :descripcion")
    , @NamedQuery(name = "Sucursal.findByNroSucursal", query = "SELECT s FROM Sucursal s WHERE s.nroSucursal = :nroSucursal")})
public class Sucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nro_sucursal")
    private int nroSucursal;

    public Sucursal() {
    }

    public Sucursal(Integer id) {
        this.id = id;
    }

    public Sucursal(Integer id, String descripcion, int nroSucursal) {
        this.id = id;
        this.descripcion = descripcion;
        this.nroSucursal = nroSucursal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNroSucursal() {
        return nroSucursal;
    }

    public void setNroSucursal(int nroSucursal) {
        this.nroSucursal = nroSucursal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sucursal)) {
            return false;
        }
        Sucursal other = (Sucursal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.Sucursal[ id=" + id + " ]";
    }
    
}
