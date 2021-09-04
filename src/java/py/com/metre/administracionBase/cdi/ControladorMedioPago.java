package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext; /*
import py.com.metre.administracionBase.ejb.MedioPagoEJB; 
import py.com.metre.administracionBase.jpa.MedioPago; */
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author Andrea
 */
@Named
@SessionScoped
public class ControladorMedioPago implements Serializable {
/*
    Logger logger = Logger.getLogger(MedioPago.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private MedioPagoEJB medioPagoEJB;
    private MedioPago medioPagoSeleccionado;
    private MedioPago medioPagoNuevo;
    private List<MedioPago> listaMedioPago;
    private
    @Inject
    LoginControlador loginControlador;

    public MedioPago getMedioPagoNuevo() {
        if (medioPagoNuevo == null) {
            this.medioPagoNuevo = new MedioPago();
        }
        return this.medioPagoNuevo;
    }

    public void setMedioPagoNuevo(MedioPago medioPago) {
        if (medioPago != null) {
            this.medioPagoNuevo = medioPago;
        }
    }

    public MedioPago getMedioPagoSeleccionado() {
        if (medioPagoSeleccionado == null) {
            medioPagoSeleccionado = new MedioPago();
            return medioPagoSeleccionado;
        }
        return medioPagoSeleccionado;
    }

    public void setMedioPagoSeleccionado(MedioPago medioPago) {
        if (medioPagoSeleccionado != null) {
            this.medioPagoSeleccionado = medioPago;
        }
    }

    public List<MedioPago> getListaMedioPago() {
        listaMedioPago = medioPagoEJB.listarTodos();
        return listaMedioPago;
    }

    public void setListaMedioPago(List<MedioPago> listaMedioPago) {
        this.listaMedioPago = listaMedioPago;
    }

    public void update() {
        try {
            if (this.medioPagoSeleccionado != null) {
                medioPagoEJB.actualizar(medioPagoSeleccionado);
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
            medioPagoEJB.insertar(medioPagoNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }

    public void delete() {
        try {
            if (this.medioPagoSeleccionado != null) {
                medioPagoEJB.eliminar(medioPagoSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        medioPagoSeleccionado = null;
        medioPagoNuevo = null;
    }*/
}
