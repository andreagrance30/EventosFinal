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
@Table(name = "detalle_cobros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleCobros.findAll", query = "SELECT d FROM DetalleCobros d")
    , @NamedQuery(name = "DetalleCobros.findById", query = "SELECT d FROM DetalleCobros d WHERE d.id = :id")
    , @NamedQuery(name = "DetalleCobros.findByMontoEntregado", query = "SELECT d FROM DetalleCobros d WHERE d.montoEntregado = :montoEntregado")
    , @NamedQuery(name = "DetalleCobros.findByFechaVencimiento", query = "SELECT d FROM DetalleCobros d WHERE d.fechaVencimiento = :fechaVencimiento")
    , @NamedQuery(name = "DetalleCobros.findByFechaCobro", query = "SELECT d FROM DetalleCobros d WHERE d.fechaCobro = :fechaCobro")
    , @NamedQuery(name = "DetalleCobros.findByEstado", query = "SELECT d FROM DetalleCobros d WHERE d.estado = :estado")})
public class DetalleCobros implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "monto_entregado")
    private BigDecimal montoEntregado;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Column(name = "fecha_cobro")
    @Temporal(TemporalType.DATE)
    private Date fechaCobro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "detalleCobrosid")
    private List<AperturaCierreCaja> aperturaCierreCajaList;
    @JoinColumn(name = "cobrosid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cobros cobrosid;
    @JoinColumn(name = "forma_cobroid", referencedColumnName = "id")
    @ManyToOne
    private FormaCobro formaCobroid;

    public DetalleCobros() {
    }

    public DetalleCobros(Integer id) {
        this.id = id;
    }

    public DetalleCobros(Integer id, BigDecimal montoEntregado, Date fechaVencimiento, String estado) {
        this.id = id;
        this.montoEntregado = montoEntregado;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMontoEntregado() {
        return montoEntregado;
    }

    public void setMontoEntregado(BigDecimal montoEntregado) {
        this.montoEntregado = montoEntregado;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(Date fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<AperturaCierreCaja> getAperturaCierreCajaList() {
        return aperturaCierreCajaList;
    }

    public void setAperturaCierreCajaList(List<AperturaCierreCaja> aperturaCierreCajaList) {
        this.aperturaCierreCajaList = aperturaCierreCajaList;
    }

    public Cobros getCobrosid() {
        return cobrosid;
    }

    public void setCobrosid(Cobros cobrosid) {
        this.cobrosid = cobrosid;
    }

    public FormaCobro getFormaCobroid() {
        return formaCobroid;
    }

    public void setFormaCobroid(FormaCobro formaCobroid) {
        this.formaCobroid = formaCobroid;
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
        if (!(object instanceof DetalleCobros)) {
            return false;
        }
        DetalleCobros other = (DetalleCobros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.DetalleCobros[ id=" + id + " ]";
    }
    
}
