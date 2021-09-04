/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.metre.administracionBase.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compras.findAll", query = "SELECT c FROM Compras c")
    , @NamedQuery(name = "Compras.findById", query = "SELECT c FROM Compras c WHERE c.id = :id")
    , @NamedQuery(name = "Compras.findByFecha", query = "SELECT c FROM Compras c WHERE c.fecha = :fecha")
    , @NamedQuery(name = "Compras.findByNroFactura", query = "SELECT c FROM Compras c WHERE c.nroFactura = :nroFactura")
    , @NamedQuery(name = "Compras.findByMontoTotal", query = "SELECT c FROM Compras c WHERE c.montoTotal = :montoTotal")})
public class Compras implements Serializable {

    @Column(name = "monto_total")
    private BigDecimal montoTotal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comprasid")
    private List<CuentasPagar> cuentasPagarList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comprasid")
    private List<DetalleCompras> detalleComprasList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 2147483647)
    @Column(name = "nro_factura")
    private String nroFactura;
    @JoinColumn(name = "forma_pagoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FormaPago formaPagoid;
    @JoinColumn(name = "proveedorid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Proveedor proveedorid;
    @JoinColumn(name = "usuarioid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioid;

    public Compras() {
    }

    public Compras(Integer id) {
        this.id = id;
    }

    public Compras(Integer id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public FormaPago getFormaPagoid() {
        return formaPagoid;
    }

    public void setFormaPagoid(FormaPago formaPagoid) {
        this.formaPagoid = formaPagoid;
    }

    public Proveedor getProveedorid() {
        return proveedorid;
    }

    public void setProveedorid(Proveedor proveedorid) {
        this.proveedorid = proveedorid;
    }

    public Usuario getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuario usuarioid) {
        this.usuarioid = usuarioid;
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
        if (!(object instanceof Compras)) {
            return false;
        }
        Compras other = (Compras) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.Compras[ id=" + id + " ]";
    }

    @XmlTransient
    public List<CuentasPagar> getCuentasPagarList() {
        return cuentasPagarList;
    }

    public void setCuentasPagarList(List<CuentasPagar> cuentasPagarList) {
        this.cuentasPagarList = cuentasPagarList;
    }

    @XmlTransient
    public List<DetalleCompras> getDetalleComprasList() {
        return detalleComprasList;
    }

    public void setDetalleComprasList(List<DetalleCompras> detalleComprasList) {
        this.detalleComprasList = detalleComprasList;
    }
    
}
