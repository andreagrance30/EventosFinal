/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.metre.administracionBase.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
    , @NamedQuery(name = "Producto.findById", query = "SELECT p FROM Producto p WHERE p.id = :id")
    , @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio")
    , @NamedQuery(name = "Producto.findByPrecioMulta", query = "SELECT p FROM Producto p WHERE p.precioMulta = :precioMulta")
    , @NamedQuery(name = "Producto.findByCantidad", query = "SELECT p FROM Producto p WHERE p.cantidad = :cantidad")})

public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
  //  @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "clasificacion_productoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ClasificacionProducto clasificacionProductoid;
    @JoinColumn(name = "tipo_productoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoProducto tipoProductoid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoid")
    private List<DetallePresupuesto> detallePresupuestoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoid")
    private List<DetalleVentas> detalleVentasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoid")
    private List<DetalleReceta> detalleRecetaList;
 /*   @OneToMany(mappedBy = "productoid")
    private List<Ajustes> ajustesList;*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoid")
    private List<DetalleCompras> detalleComprasList;
    @OneToMany(mappedBy = "productoid")
    private List<Ajustes> ajustesList;
    @Column(name = "precio")
    private BigDecimal precio;
    @Column(name = "precio_multa")
    private BigDecimal precioMulta;
    @Column(name = "cantidad")
    private BigDecimal cantidad;

    
    public Producto() {
    }

    public Producto(Integer id) {
        this.id = id;
    }

    public Producto(Integer id, String descripcion) {
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
    
    public ClasificacionProducto getClasificacionProductoid() {
        return clasificacionProductoid;
    }

    public void setClasificacionProductoid(ClasificacionProducto clasificacionProductoid) {
        this.clasificacionProductoid = clasificacionProductoid;
    }

    public TipoProducto getTipoProductoid() {
        return tipoProductoid;
    }

    public void setTipoProductoid(TipoProducto tipoProductoid) {
        this.tipoProductoid = tipoProductoid;
    }

    @XmlTransient
    public List<DetallePresupuesto> getDetallePresupuestoList() {
        return detallePresupuestoList;
    }

    public void setDetallePresupuestoList(List<DetallePresupuesto> detallePresupuestoList) {
        this.detallePresupuestoList = detallePresupuestoList;
    }

    @XmlTransient
    public List<DetalleVentas> getDetalleVentasList() {
        return detalleVentasList;
    }

    public void setDetalleVentasList(List<DetalleVentas> detalleVentasList) {
        this.detalleVentasList = detalleVentasList;
    }

    @XmlTransient
    public List<DetalleReceta> getDetalleRecetaList() {
        return detalleRecetaList;
    }

    public void setDetalleRecetaList(List<DetalleReceta> detalleRecetaList) {
        this.detalleRecetaList = detalleRecetaList;
    }
/*
    @XmlTransient
    public List<Ajustes> getAjustesList() {
        return ajustesList;
    }

    public void setAjustesList(List<Ajustes> ajustesList) {
        this.ajustesList = ajustesList;
    }
*/
    @XmlTransient
    public List<DetalleCompras> getDetalleComprasList() {
        return detalleComprasList;
    }

    public void setDetalleComprasList(List<DetalleCompras> detalleComprasList) {
        this.detalleComprasList = detalleComprasList;
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
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.Producto[ id=" + id + " ]";
    }


    public BigDecimal getPrecioMulta() {
        return precioMulta;
    }

    public void setPrecioMulta(BigDecimal precioMulta) {
        this.precioMulta = precioMulta;
    }


    @XmlTransient
    public List<Ajustes> getAjustesList() {
        return ajustesList;
    }

    public void setAjustesList(List<Ajustes> ajustesList) {
        this.ajustesList = ajustesList;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
}
