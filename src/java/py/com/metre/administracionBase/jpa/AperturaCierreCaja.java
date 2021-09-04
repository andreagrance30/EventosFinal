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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "apertura_cierre_caja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AperturaCierreCaja.findAll", query = "SELECT a FROM AperturaCierreCaja a")
    , @NamedQuery(name = "AperturaCierreCaja.findById", query = "SELECT a FROM AperturaCierreCaja a WHERE a.id = :id")
    , @NamedQuery(name = "AperturaCierreCaja.findByMontoApertura", query = "SELECT a FROM AperturaCierreCaja a WHERE a.montoApertura = :montoApertura")
    , @NamedQuery(name = "AperturaCierreCaja.findByMontoEntrada", query = "SELECT a FROM AperturaCierreCaja a WHERE a.montoEntrada = :montoEntrada")
    , @NamedQuery(name = "AperturaCierreCaja.findByMontoSistema", query = "SELECT a FROM AperturaCierreCaja a WHERE a.montoSistema = :montoSistema")
    , @NamedQuery(name = "AperturaCierreCaja.findByMontoCaja", query = "SELECT a FROM AperturaCierreCaja a WHERE a.montoCaja = :montoCaja")
    , @NamedQuery(name = "AperturaCierreCaja.findByMontoSalida", query = "SELECT a FROM AperturaCierreCaja a WHERE a.montoSalida = :montoSalida")
    , @NamedQuery(name = "AperturaCierreCaja.findByMontoDiferencia", query = "SELECT a FROM AperturaCierreCaja a WHERE a.montoDiferencia = :montoDiferencia")
    , @NamedQuery(name = "AperturaCierreCaja.findByFecha", query = "SELECT a FROM AperturaCierreCaja a WHERE a.fecha = :fecha")})
public class AperturaCierreCaja implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "monto_apertura")
    private BigDecimal montoApertura;
    @Column(name = "monto_entrada")
    private BigDecimal montoEntrada;
    @Column(name = "monto_sistema")
    private BigDecimal montoSistema;
    @Column(name = "monto_caja")
    private BigDecimal montoCaja;
    @Column(name = "monto_salida")
    private BigDecimal montoSalida;
    @Column(name = "monto_diferencia")
    private BigDecimal montoDiferencia;

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
    @JoinColumn(name = "cajaid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Caja cajaid;
    @JoinColumn(name = "detalle_cobrosid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DetalleCobros detalleCobrosid;
    @JoinColumn(name = "usuarioid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aperturaCierreCajaid")
    private List<DetalleAperturaCierre> detalleAperturaCierreList;

    public AperturaCierreCaja() {
    }

    public AperturaCierreCaja(Integer id) {
        this.id = id;
    }

    public AperturaCierreCaja(Integer id, BigDecimal montoApertura, Date fecha) {
        this.id = id;
        this.montoApertura = montoApertura;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMontoApertura() {
        return montoApertura;
    }

    public void setMontoApertura(BigDecimal montoApertura) {
        this.montoApertura = montoApertura;
    }

    public BigDecimal getMontoEntrada() {
        return montoEntrada;
    }

    public void setMontoEntrada(BigDecimal montoEntrada) {
        this.montoEntrada = montoEntrada;
    }

    public BigDecimal getMontoSistema() {
        return montoSistema;
    }

    public void setMontoSistema(BigDecimal montoSistema) {
        this.montoSistema = montoSistema;
    }

    public BigDecimal getMontoCaja() {
        return montoCaja;
    }

    public void setMontoCaja(BigDecimal montoCaja) {
        this.montoCaja = montoCaja;
    }

    public BigDecimal getMontoSalida() {
        return montoSalida;
    }

    public void setMontoSalida(BigDecimal montoSalida) {
        this.montoSalida = montoSalida;
    }

    public BigDecimal getMontoDiferencia() {
        return montoDiferencia;
    }

    public void setMontoDiferencia(BigDecimal montoDiferencia) {
        this.montoDiferencia = montoDiferencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Caja getCajaid() {
        return cajaid;
    }

    public void setCajaid(Caja cajaid) {
        this.cajaid = cajaid;
    }

    public DetalleCobros getDetalleCobrosid() {
        return detalleCobrosid;
    }

    public void setDetalleCobrosid(DetalleCobros detalleCobrosid) {
        this.detalleCobrosid = detalleCobrosid;
    }

    public Usuario getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuario usuarioid) {
        this.usuarioid = usuarioid;
    }

    @XmlTransient
    public List<DetalleAperturaCierre> getDetalleAperturaCierreList() {
        return detalleAperturaCierreList;
    }

    public void setDetalleAperturaCierreList(List<DetalleAperturaCierre> detalleAperturaCierreList) {
        this.detalleAperturaCierreList = detalleAperturaCierreList;
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
        if (!(object instanceof AperturaCierreCaja)) {
            return false;
        }
        AperturaCierreCaja other = (AperturaCierreCaja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.AperturaCierreCaja[ id=" + id + " ]";
    }
  
}
