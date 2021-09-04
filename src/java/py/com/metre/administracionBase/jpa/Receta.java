/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.metre.administracionBase.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "receta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Receta.findAll", query = "SELECT r FROM Receta r")
    , @NamedQuery(name = "Receta.findById", query = "SELECT r FROM Receta r WHERE r.id = :id")
    , @NamedQuery(name = "Receta.findByNombreReceta", query = "SELECT r FROM Receta r WHERE r.nombreReceta = :nombreReceta")
    , @NamedQuery(name = "Receta.findByDescripcionPreparacion", query = "SELECT r FROM Receta r WHERE r.descripcionPreparacion = :descripcionPreparacion")})
public class Receta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre_receta")
    private String nombreReceta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion_preparacion")
    private String descripcionPreparacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recetaid")
    private List<DetalleReceta> detalleRecetaList;

    public Receta() {
    }

    public Receta(Integer id) {
        this.id = id;
    }

    public Receta(Integer id, String nombreReceta, String descripcionPreparacion) {
        this.id = id;
        this.nombreReceta = nombreReceta;
        this.descripcionPreparacion = descripcionPreparacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreReceta() {
        return nombreReceta;
    }

    public void setNombreReceta(String nombreReceta) {
        this.nombreReceta = nombreReceta;
    }

    public String getDescripcionPreparacion() {
        return descripcionPreparacion;
    }

    public void setDescripcionPreparacion(String descripcionPreparacion) {
        this.descripcionPreparacion = descripcionPreparacion;
    }

    @XmlTransient
    public List<DetalleReceta> getDetalleRecetaList() {
        return detalleRecetaList;
    }

    public void setDetalleRecetaList(List<DetalleReceta> detalleRecetaList) {
        this.detalleRecetaList = detalleRecetaList;
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
        if (!(object instanceof Receta)) {
            return false;
        }
        Receta other = (Receta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.Receta[ id=" + id + " ]";
    }
    
}
