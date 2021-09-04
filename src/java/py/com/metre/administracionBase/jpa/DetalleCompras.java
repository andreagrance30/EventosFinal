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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "detalle_compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleCompras.findAll", query = "SELECT d FROM DetalleCompras d")
    , @NamedQuery(name = "DetalleCompras.findById", query = "SELECT d FROM DetalleCompras d WHERE d.id = :id")
    , @NamedQuery(name = "DetalleCompras.findByCostoUnitario", query = "SELECT d FROM DetalleCompras d WHERE d.costoUnitario = :costoUnitario")
    , @NamedQuery(name = "DetalleCompras.findByCostoTotal", query = "SELECT d FROM DetalleCompras d WHERE d.costoTotal = :costoTotal")
    , @NamedQuery(name = "DetalleCompras.findByCantidad", query = "SELECT d FROM DetalleCompras d WHERE d.cantidad = :cantidad")})
public class DetalleCompras implements Serializable {

    @Column(name = "costo_unitario")
    private BigDecimal costoUnitario;
    @Column(name = "costo_total")
    private BigDecimal costoTotal;
    @Column(name = "cantidad")
    private BigDecimal cantidad;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "comprasid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Compras comprasid;
    @JoinColumn(name = "productoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Producto productoid;

    public DetalleCompras() {
    }

    public DetalleCompras(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(BigDecimal costoTotal) {
        this.costoTotal = costoTotal;
    }


    public Compras getComprasid() {
        return comprasid;
    }

    public void setComprasid(Compras comprasid) {
        this.comprasid = comprasid;
    }

    public Producto getProductoid() {
        return productoid;
    }

    public void setProductoid(Producto productoid) {
        this.productoid = productoid;
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
        if (!(object instanceof DetalleCompras)) {
            return false;
        }
        DetalleCompras other = (DetalleCompras) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.DetalleCompras[ id=" + id + " ]";
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
    
}
