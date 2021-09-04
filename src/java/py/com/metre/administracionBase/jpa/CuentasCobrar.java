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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "cuentas_cobrar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentasCobrar.findAll", query = "SELECT c FROM CuentasCobrar c")
    , @NamedQuery(name = "CuentasCobrar.findById", query = "SELECT c FROM CuentasCobrar c WHERE c.id = :id")
    , @NamedQuery(name = "CuentasCobrar.findByNroFactura", query = "SELECT c FROM CuentasCobrar c WHERE c.nroFactura = :nroFactura")
    , @NamedQuery(name = "CuentasCobrar.findByFechaEmision", query = "SELECT c FROM CuentasCobrar c WHERE c.fechaEmision = :fechaEmision")
    , @NamedQuery(name = "CuentasCobrar.findByFechaVencimiento", query = "SELECT c FROM CuentasCobrar c WHERE c.fechaVencimiento = :fechaVencimiento")
    , @NamedQuery(name = "CuentasCobrar.findByDescripcion", query = "SELECT c FROM CuentasCobrar c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "CuentasCobrar.findByMonto", query = "SELECT c FROM CuentasCobrar c WHERE c.monto = :monto")
    , @NamedQuery(name = "CuentasCobrar.findByEstado", query = "SELECT c FROM CuentasCobrar c WHERE c.estado = :estado")})
public class CuentasCobrar implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "monto")
    private BigDecimal monto;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nro_factura")
    private String nroFactura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "cobroid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cobros cobroid;

    public CuentasCobrar() {
    }

    public CuentasCobrar(Integer id) {
        this.id = id;
    }

    public CuentasCobrar(Integer id, String nroFactura, Date fechaEmision, Date fechaVencimiento, BigDecimal monto, String estado) {
        this.id = id;
        this.nroFactura = nroFactura;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.monto = monto;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cobros getCobroid() {
        return cobroid;
    }

    public void setCobroid(Cobros cobroid) {
        this.cobroid = cobroid;
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
        if (!(object instanceof CuentasCobrar)) {
            return false;
        }
        CuentasCobrar other = (CuentasCobrar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.CuentasCobrar[ id=" + id + " ]";
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
}
