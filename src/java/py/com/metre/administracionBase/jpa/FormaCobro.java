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
@Table(name = "forma_cobro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormaCobro.findAll", query = "SELECT f FROM FormaCobro f")
    , @NamedQuery(name = "FormaCobro.findById", query = "SELECT f FROM FormaCobro f WHERE f.id = :id")
    , @NamedQuery(name = "FormaCobro.findByDescripcion", query = "SELECT f FROM FormaCobro f WHERE f.descripcion = :descripcion")})
public class FormaCobro implements Serializable {

    @OneToMany(mappedBy = "formaCobroid")
    private List<Ventas> ventasList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formaCobroid")
    private List<DetalleAperturaCierre> detalleAperturaCierreList;
    @OneToMany(mappedBy = "formaCobroid")
    private List<DetalleCobros> detalleCobrosList;

    public FormaCobro() {
    }

    public FormaCobro(Integer id) {
        this.id = id;
    }

    public FormaCobro(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
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

    @XmlTransient
    public List<DetalleAperturaCierre> getDetalleAperturaCierreList() {
        return detalleAperturaCierreList;
    }

    public void setDetalleAperturaCierreList(List<DetalleAperturaCierre> detalleAperturaCierreList) {
        this.detalleAperturaCierreList = detalleAperturaCierreList;
    }

    @XmlTransient
    public List<DetalleCobros> getDetalleCobrosList() {
        return detalleCobrosList;
    }

    public void setDetalleCobrosList(List<DetalleCobros> detalleCobrosList) {
        this.detalleCobrosList = detalleCobrosList;
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
        if (!(object instanceof FormaCobro)) {
            return false;
        }
        FormaCobro other = (FormaCobro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.FormaCobro[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Ventas> getVentasList() {
        return ventasList;
    }

    public void setVentasList(List<Ventas> ventasList) {
        this.ventasList = ventasList;
    }
    
}
