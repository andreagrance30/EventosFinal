package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import py.com.metre.administracionBase.ejb.CajaEJB;
import py.com.metre.administracionBase.jpa.Caja;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorCaja implements Serializable {

    Logger logger = Logger.getLogger(Caja.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private CajaEJB cajaEJB;
    private Caja cajaSeleccionado;
    private Caja cajaNuevo;
    private List<Caja> listaCaja;
    private Boolean deshabilitar;
    private
    @Inject
    LoginControlador loginControlador;

    public Caja getCajaNuevo() {
        if (cajaNuevo == null) {
            this.cajaNuevo = new Caja();
        }
        return this.cajaNuevo;
    }

    public void setCajaNuevo(Caja caja) {
        if (caja != null) {
            this.cajaNuevo = caja;
        }
    }

    public Caja getCajaSeleccionado() {
        if (cajaSeleccionado == null) {
            cajaSeleccionado = new Caja();
            return cajaSeleccionado;
        }
        return cajaSeleccionado;
    }

    public void setCajaSeleccionado(Caja caja) {
        if (cajaSeleccionado != null) {
            this.cajaSeleccionado = caja;
        }
    }

    public List<Caja> getListaCaja() {
        listaCaja = cajaEJB.listarTodos();
        return listaCaja;
    }

    public void setListaCaja(List<Caja> listaCaja) {
        this.listaCaja = listaCaja;
    }

    public void update() {
        try {
            if (this.cajaSeleccionado != null) {
                cajaEJB.actualizar(cajaSeleccionado);
                JsfUtil.agregarMensajeExito("Se ha actualizado correctamente.");
                resetearCampos();
            }
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "No se pudo actualizar: " + ex);
            resetearCampos();
        }
    }

    public void add() {
        try {
            cajaEJB.insertar(cajaNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
             deshabilitar=true; 
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }

    public void delete() {
        try {
            if (this.cajaSeleccionado != null) {
                cajaEJB.eliminar(cajaSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        cajaSeleccionado = null;
        cajaNuevo = null;
    }

    public Boolean getDeshabilitar() {
        return deshabilitar;
    }

    public void setDeshabilitar(Boolean deshabilitar) {
         this.deshabilitar = deshabilitar; 
    }
    
}
