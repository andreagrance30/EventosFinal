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
@Table(name = "ventas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ventas.findAll", query = "SELECT v FROM Ventas v")
    , @NamedQuery(name = "Ventas.findById", query = "SELECT v FROM Ventas v WHERE v.id = :id")
    , @NamedQuery(name = "Ventas.findByFechaFact", query = "SELECT v FROM Ventas v WHERE v.fechaFact = :fechaFact")
    , @NamedQuery(name = "Ventas.findByNroFact", query = "SELECT v FROM Ventas v WHERE v.nroFact = :nroFact")
    , @NamedQuery(name = "Ventas.findByCondicionVenta", query = "SELECT v FROM Ventas v WHERE v.condicionVenta = :condicionVenta")
    , @NamedQuery(name = "Ventas.findByTotalIva", query = "SELECT v FROM Ventas v WHERE v.totalIva = :totalIva")
    , @NamedQuery(name = "Ventas.findByTotalPagar", query = "SELECT v FROM Ventas v WHERE v.totalPagar = :totalPagar")})
public class Ventas implements Serializable {

    @Column(name = "total_iva")
    private BigDecimal totalIva;
    @Column(name = "total_pagar")
    private BigDecimal totalPagar;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ventasid")
    private List<Cobros> cobrosList;
    @OneToMany(mappedBy = "ventasid")
    private List<Ajustes> ajustesList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_fact")
    @Temporal(TemporalType.DATE)
    private Date fechaFact;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nro_fact")
    private String nroFact;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "condicion_venta")
    private String condicionVenta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ventasid")
    private List<DetalleVentas> detalleVentasList;
    @JoinColumn(name = "clienteid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente clienteid;
    @JoinColumn(name = "forma_cobroid", referencedColumnName = "id")
    @ManyToOne
    private FormaCobro formaCobroid;
    @JoinColumn(name = "usuarioid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioid;

    public Ventas() {
    }

    public Ventas(Integer id) {
        this.id = id;
    }

    public Ventas(Integer id, Date fechaFact, String nroFact, String condicionVenta) {
        this.id = id;
        this.fechaFact = fechaFact;
        this.nroFact = nroFact;
        this.condicionVenta = condicionVenta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaFact() {
        return fechaFact;
    }

    public void setFechaFact(Date fechaFact) {
        this.fechaFact = fechaFact;
    }

    public String getNroFact() {
        return nroFact;
    }

    public void setNroFact(String nroFact) {
        this.nroFact = nroFact;
    }

    public String getCondicionVenta() {
        return condicionVenta;
    }

    public void setCondicionVenta(String condicionVenta) {
        this.condicionVenta = condicionVenta;
    }

    public BigDecimal getTotalIva() {
        return totalIva;
    }

    public void setTotalIva(BigDecimal totalIva) {
        this.totalIva = totalIva;
    }

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    @XmlTransient
    public List<DetalleVentas> getDetalleVentasList() {
        return detalleVentasList;
    }

    public void setDetalleVentasList(List<DetalleVentas> detalleVentasList) {
        this.detalleVentasList = detalleVentasList;
    }

    public Cliente getClienteid() {
        return clienteid;
    }

    public void setClienteid(Cliente clienteid) {
        this.clienteid = clienteid;
    }

    public FormaCobro getFormaCobroid() {
        return formaCobroid;
    }

    public void setFormaCobroid(FormaCobro formaCobroid) {
        this.formaCobroid = formaCobroid;
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
        if (!(object instanceof Ventas)) {
            return false;
        }
        Ventas other = (Ventas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.Ventas[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Cobros> getCobrosList() {
        return cobrosList;
    }

    public void setCobrosList(List<Cobros> cobrosList) {
        this.cobrosList = cobrosList;
    }

    @XmlTransient
    public List<Ajustes> getAjustesList() {
        return ajustesList;
    }

    public void setAjustesList(List<Ajustes> ajustesList) {
        this.ajustesList = ajustesList;
    }
    
}
