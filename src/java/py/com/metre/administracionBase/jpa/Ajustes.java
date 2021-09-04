/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.metre.administracionBase.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "ajustes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ajustes.findAll", query = "SELECT a FROM Ajustes a")
    , @NamedQuery(name = "Ajustes.findById", query = "SELECT a FROM Ajustes a WHERE a.id = :id")
    , @NamedQuery(name = "Ajustes.findByFechaAjuste", query = "SELECT a FROM Ajustes a WHERE a.fechaAjuste = :fechaAjuste")
    , @NamedQuery(name = "Ajustes.findByDescripcion", query = "SELECT a FROM Ajustes a WHERE a.descripcion = :descripcion")
    , @NamedQuery(name = "Ajustes.findByCantidad", query = "SELECT a FROM Ajustes a WHERE a.cantidad = :cantidad")
    , @NamedQuery(name = "Ajustes.findByTipoajuste", query = "SELECT a FROM Ajustes a WHERE a.tipoajuste = :tipoajuste")})
public class Ajustes implements Serializable {

    @Column(name = "cantidad")
    private BigDecimal cantidad;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_ajuste")
    @Temporal(TemporalType.DATE)
    private Date fechaAjuste;
 //   @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
 //   @Size(max = 2147483647)
    @Column(name = "tipoajuste")
    private String tipoajuste;
    @JoinColumn(name = "productoid", referencedColumnName = "id")
    @ManyToOne
    private Producto productoid;
    @JoinColumn(name = "ventasid", referencedColumnName = "id")
    @ManyToOne
    private Ventas ventasid;

    public Ajustes() {
    }

    public Ajustes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaAjuste() {
        return fechaAjuste;
    }

    public void setFechaAjuste(Date fechaAjuste) {
        this.fechaAjuste = fechaAjuste;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getTipoajuste() {
        return tipoajuste;
    }

    public void setTipoajuste(String tipoajuste) {
        this.tipoajuste = tipoajuste;
    }

    public Producto getProductoid() {
        return productoid;
    }

    public void setProductoid(Producto productoid) {
        this.productoid = productoid;
    }

    public Ventas getVentasid() {
        return ventasid;
    }

    public void setVentasid(Ventas ventasid) {
        this.ventasid = ventasid;
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
        if (!(object instanceof Ajustes)) {
            return false;
        }
        Ajustes other = (Ajustes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.Ajustes[ id=" + id + " ]";
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
    
}
