/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.metre.administracionBase.jpa;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id")
    , @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 30)
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioid")
    private List<Compras> comprasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioid")
    private List<Presupuesto> presupuestoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioid")
    private List<Cobros> cobrosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioid")
    private List<AperturaCierreCaja> aperturaCierreCajaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioid")
    private List<Ventas> ventasList;
    @JoinColumn(name = "perfil_usuarioid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PerfilUsuario perfilUsuarioid;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public List<Compras> getComprasList() {
        return comprasList;
    }

    public void setComprasList(List<Compras> comprasList) {
        this.comprasList = comprasList;
    }

    @XmlTransient
    public List<Presupuesto> getPresupuestoList() {
        return presupuestoList;
    }

    public void setPresupuestoList(List<Presupuesto> presupuestoList) {
        this.presupuestoList = presupuestoList;
    }

    @XmlTransient
    public List<Cobros> getCobrosList() {
        return cobrosList;
    }

    public void setCobrosList(List<Cobros> cobrosList) {
        this.cobrosList = cobrosList;
    }

    @XmlTransient
    public List<AperturaCierreCaja> getAperturaCierreCajaList() {
        return aperturaCierreCajaList;
    }

    public void setAperturaCierreCajaList(List<AperturaCierreCaja> aperturaCierreCajaList) {
        this.aperturaCierreCajaList = aperturaCierreCajaList;
    }

    @XmlTransient
    public List<Ventas> getVentasList() {
        return ventasList;
    }

    public void setVentasList(List<Ventas> ventasList) {
        this.ventasList = ventasList;
    }

    public PerfilUsuario getPerfilUsuarioid() {
        return perfilUsuarioid;
    }

    public void setPerfilUsuarioid(PerfilUsuario perfilUsuarioid) {
        this.perfilUsuarioid = perfilUsuarioid;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.Usuario[ id=" + id + " ]";
    }
    
}
