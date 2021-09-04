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
@Table(name = "detalle_apertura_cierre")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleAperturaCierre.findAll", query = "SELECT d FROM DetalleAperturaCierre d")
    , @NamedQuery(name = "DetalleAperturaCierre.findById", query = "SELECT d FROM DetalleAperturaCierre d WHERE d.id = :id")
    , @NamedQuery(name = "DetalleAperturaCierre.findByMontoCierre", query = "SELECT d FROM DetalleAperturaCierre d WHERE d.montoCierre = :montoCierre")})
public class DetalleAperturaCierre implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "monto_cierre")
    private BigDecimal montoCierre;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "apertura_cierre_cajaid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AperturaCierreCaja aperturaCierreCajaid;
    @JoinColumn(name = "forma_cobroid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FormaCobro formaCobroid;

    public DetalleAperturaCierre() {
    }

    public DetalleAperturaCierre(Integer id) {
        this.id = id;
    }

    public DetalleAperturaCierre(Integer id, BigDecimal montoCierre) {
        this.id = id;
        this.montoCierre = montoCierre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMontoCierre() {
        return montoCierre;
    }

    public void setMontoCierre(BigDecimal montoCierre) {
        this.montoCierre = montoCierre;
    }

    public AperturaCierreCaja getAperturaCierreCajaid() {
        return aperturaCierreCajaid;
    }

    public void setAperturaCierreCajaid(AperturaCierreCaja aperturaCierreCajaid) {
        this.aperturaCierreCajaid = aperturaCierreCajaid;
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
        if (!(object instanceof DetalleAperturaCierre)) {
            return false;
        }
        DetalleAperturaCierre other = (DetalleAperturaCierre) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.metre.administracionBase.jpa.DetalleAperturaCierre[ id=" + id + " ]";
    }
    
}
