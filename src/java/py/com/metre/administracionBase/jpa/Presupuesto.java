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
@Table(name = "presupuesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Presupuesto.findAll", query = "SELECT p FROM Presupuesto p")
    , @NamedQuery(name = "Presupuesto.findById", query = "SELECT p FROM Presupuesto p WHERE p.id = :id")
    , @NamedQuery(name = "Presupuesto.findByHoraInicio", query = "SELECT p FROM Presupuesto p WHERE p.horaInicio = :horaInicio")
    , @NamedQuery(name = "Presupuesto.findByHoraFin", query = "SELECT p FROM Presupuesto p WHERE p.horaFin = :horaFin")
    , @NamedQuery(name = "Presupuesto.findByFechaEvento", query = "SELECT p FROM Presupuesto p WHERE p.fechaEvento = :fechaEvento")
    , @NamedQuery(name = "Presupuesto.findByFechaCotizacion", query = "SELECT p FROM Presupuesto p WHERE p.fechaCotizacion = :fechaCotizacion")
    , @NamedQuery(name = "Presupuesto.findByFechaDevolucion", query = "SELECT p FROM Presupuesto p WHERE p.fechaDevolucion = :fechaDevolucion")
    , @NamedQuery(name = "Presupuesto.findByCantidadPersonas", query = "SELECT p FROM Presupuesto p WHERE p.cantidadPersonas = :cantidadPersonas")
    , @NamedQuery(name = "Presupuesto.findByDireccion", query = "SELECT p FROM Presupuesto p WHERE p.direccion = :direccion")
    , @NamedQuery(name = "Presupuesto.findByDescripcion", query = "SELECT p FROM Presupuesto p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Presupuesto.findByTotal", query = "SELECT p FROM Presupuesto p WHERE p.total = :total")
    , @NamedQuery(name = "Presupuesto.findByEstado", query = "SELECT p FROM Presupuesto p WHERE p.estado = :estado")})
public class Presupuesto implements Serializable {

    @Column(name = "cantidad_personas")
    private BigDecimal cantidadPersonas;
    @Column(name = "total")
    private BigDecimal total;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "hora_inicio")
    @Temporal(TemporalType.TIME)
    private Date horaInicio;
    @Column(name = "hora_fin")
    @Temporal(TemporalType.TIME)
    private Date horaFin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_evento")
    @Temporal(TemporalType.DATE)
    private Date fechaEvento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_cotizacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCotizacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_devolucion")
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;
    @Size(max = 2147483647)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 2147483647)
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "presupuestoid")
    private List<DetallePresupuesto> detallePresupuestoList;
    @JoinColumn(name = "clienteid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente clienteid;
    @JoinColumn(name = "usuarioid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioid;

    public Presupuesto() {
    }

    public Presupuesto(Integer id) {
        this.id = id;
    }

    public Presupuesto(Integer id, Date fechaEvento, Date fechaCotizacion, Date fechaDevolucion) {
        this.id = id;
        this.fechaEvento = fechaEvento;
        this.fechaCotizacion = fechaCotizacion;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public Date getFechaCotizacion() {
        return fechaCotizacion;
    }

    public void setFechaCotizacion(Date fechaCotizacion) {
        this.fechaCotizacion = fechaCotizacion;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public BigDecimal getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(BigDecimal cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    @XmlTransient
    public List<DetallePresupuesto> getDetallePresupuestoList() {
        return detallePresupuestoList;
    }

    public void setDetallePresupuestoList(List<DetallePresupuesto> detallePresupuestoList) {
        this.detallePresupuestoList = detallePresupuestoList;
    }

    public Cliente getClienteid() {
        return clienteid;
    }

    public void setClienteid(Cliente clienteid) {
        this.clienteid = clienteid;
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
        if (!(object instanceof Presupuesto)) {
            return false;
        }
        Presupuesto other = (Presupuesto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.Presupuesto[ id=" + id + " ]";
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
}
