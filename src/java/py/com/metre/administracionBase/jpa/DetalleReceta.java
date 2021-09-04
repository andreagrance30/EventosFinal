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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "detalle_receta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleReceta.findAll", query = "SELECT d FROM DetalleReceta d")
    , @NamedQuery(name = "DetalleReceta.findById", query = "SELECT d FROM DetalleReceta d WHERE d.id = :id")
    , @NamedQuery(name = "DetalleReceta.findByCantidadPorReceta", query = "SELECT d FROM DetalleReceta d WHERE d.cantidadPorReceta = :cantidadPorReceta")
    , @NamedQuery(name = "DetalleReceta.findByCantidadPorXpersona", query = "SELECT d FROM DetalleReceta d WHERE d.cantidadPorXpersona = :cantidadPorXpersona")
    , @NamedQuery(name = "DetalleReceta.findByRaciones", query = "SELECT d FROM DetalleReceta d WHERE d.raciones = :raciones")
    , @NamedQuery(name = "DetalleReceta.findByCantidadPersonas", query = "SELECT d FROM DetalleReceta d WHERE d.cantidadPersonas = :cantidadPersonas")})
public class DetalleReceta implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_por_receta")
    private BigDecimal cantidadPorReceta;
    @Column(name = "cantidad_por_xpersona")
    private BigDecimal cantidadPorXpersona;
    @Column(name = "raciones")
    private BigDecimal raciones;
    @Column(name = "cantidad_personas")
    private BigDecimal cantidadPersonas;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "productoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Producto productoid;
    @JoinColumn(name = "recetaid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Receta recetaid;
    @JoinColumn(name = "unidad_de_medidaid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UnidadDeMedida unidadDeMedidaid;

    public DetalleReceta() {
    }

    public DetalleReceta(Integer id) {
        this.id = id;
    }

    public DetalleReceta(Integer id, BigDecimal cantidadPorReceta) {
        this.id = id;
        this.cantidadPorReceta = cantidadPorReceta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(BigDecimal cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public Producto getProductoid() {
        return productoid;
    }

    public void setProductoid(Producto productoid) {
        this.productoid = productoid;
    }

    public Receta getRecetaid() {
        return recetaid;
    }

    public void setRecetaid(Receta recetaid) {
        this.recetaid = recetaid;
    }

    public UnidadDeMedida getUnidadDeMedidaid() {
        return unidadDeMedidaid;
    }

    public void setUnidadDeMedidaid(UnidadDeMedida unidadDeMedidaid) {
        this.unidadDeMedidaid = unidadDeMedidaid;
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
        if (!(object instanceof DetalleReceta)) {
            return false;
        }
        DetalleReceta other = (DetalleReceta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.DetalleReceta[ id=" + id + " ]";
    }

    public BigDecimal getCantidadPorReceta() {
        return cantidadPorReceta;
    }

    public void setCantidadPorReceta(BigDecimal cantidadPorReceta) {
        this.cantidadPorReceta = cantidadPorReceta;
    }

    public BigDecimal getCantidadPorXpersona() {
        return cantidadPorXpersona;
    }

    public void setCantidadPorXpersona(BigDecimal cantidadPorXpersona) {
        this.cantidadPorXpersona = cantidadPorXpersona;
    }

    public BigDecimal getRaciones() {
        return raciones;
    }

    public void setRaciones(BigDecimal raciones) {
        this.raciones = raciones;
    }
    
}
