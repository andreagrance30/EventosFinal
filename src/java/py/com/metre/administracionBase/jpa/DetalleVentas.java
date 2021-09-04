/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.metre.administracionBase.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "detalle_ventas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleVentas.findAll", query = "SELECT d FROM DetalleVentas d")
    , @NamedQuery(name = "DetalleVentas.findById", query = "SELECT d FROM DetalleVentas d WHERE d.id = :id")
    , @NamedQuery(name = "DetalleVentas.findByCantidad", query = "SELECT d FROM DetalleVentas d WHERE d.cantidad = :cantidad")
    , @NamedQuery(name = "DetalleVentas.findByTipoiva", query = "SELECT d FROM DetalleVentas d WHERE d.tipoiva = :tipoiva")
    , @NamedQuery(name = "DetalleVentas.findByPrecioUnitario", query = "SELECT d FROM DetalleVentas d WHERE d.precioUnitario = :precioUnitario")
    , @NamedQuery(name = "DetalleVentas.findByDescripcion", query = "SELECT d FROM DetalleVentas d WHERE d.descripcion = :descripcion")
    , @NamedQuery(name = "DetalleVentas.findBySubtotal", query = "SELECT d FROM DetalleVentas d WHERE d.subtotal = :subtotal")})
public class DetalleVentas implements Serializable {

    @Column(name = "cantidad")
    private BigDecimal cantidad;
    @Column(name = "tipoiva")
    private BigDecimal tipoiva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;
    @Column(name = "subtotal")
    private BigDecimal subtotal;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "productoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Producto productoid;
    @JoinColumn(name = "ventasid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ventas ventasid;

    public DetalleVentas() {
    }

    public DetalleVentas(Integer id) {
        this.id = id;
    }

    public DetalleVentas(Integer id, BigDecimal precioUnitario) {
        this.id = id;
        this.precioUnitario = precioUnitario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof DetalleVentas)) {
            return false;
        }
        DetalleVentas other = (DetalleVentas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.DetalleVentas[ id=" + id + " ]";
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTipoiva() {
        return tipoiva;
    }

    public void setTipoiva(BigDecimal tipoiva) {
        this.tipoiva = tipoiva;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
    
}
