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
@Table(name = "cobros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cobros.findAll", query = "SELECT c FROM Cobros c")
    , @NamedQuery(name = "Cobros.findById", query = "SELECT c FROM Cobros c WHERE c.id = :id")
    , @NamedQuery(name = "Cobros.findByFechaPres", query = "SELECT c FROM Cobros c WHERE c.fechaPres = :fechaPres")
    , @NamedQuery(name = "Cobros.findByObservacion", query = "SELECT c FROM Cobros c WHERE c.observacion = :observacion")
    , @NamedQuery(name = "Cobros.findByTotalCobro", query = "SELECT c FROM Cobros c WHERE c.totalCobro = :totalCobro")})
public class Cobros implements Serializable {

    @Column(name = "total_cobro")
    private BigDecimal totalCobro;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_pres")
    @Temporal(TemporalType.DATE)
    private Date fechaPres;
    @Size(max = 30)
    @Column(name = "observacion")
    private String observacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cobroid")
    private List<CuentasCobrar> cuentasCobrarList;
    @JoinColumn(name = "usuarioid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioid;
    @JoinColumn(name = "ventasid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ventas ventasid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cobrosid")
    private List<DetalleCobros> detalleCobrosList;

    public Cobros() {
    }

    public Cobros(Integer id) {
        this.id = id;
    }

    public Cobros(Integer id, Date fechaPres) {
        this.id = id;
        this.fechaPres = fechaPres;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaPres() {
        return fechaPres;
    }

    public void setFechaPres(Date fechaPres) {
        this.fechaPres = fechaPres;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public BigDecimal getTotalCobro() {
        return totalCobro;
    }

    public void setTotalCobro(BigDecimal totalCobro) {
        this.totalCobro = totalCobro;
    }

    @XmlTransient
    public List<CuentasCobrar> getCuentasCobrarList() {
        return cuentasCobrarList;
    }

    public void setCuentasCobrarList(List<CuentasCobrar> cuentasCobrarList) {
        this.cuentasCobrarList = cuentasCobrarList;
    }

    public Usuario getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuario usuarioid) {
        this.usuarioid = usuarioid;
    }

    public Ventas getVentasid() {
        return ventasid;
    }

    public void setVentasid(Ventas ventasid) {
        this.ventasid = ventasid;
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
        if (!(object instanceof Cobros)) {
            return false;
        }
        Cobros other = (Cobros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.Cobros[ id=" + id + " ]";
    }
    
}
