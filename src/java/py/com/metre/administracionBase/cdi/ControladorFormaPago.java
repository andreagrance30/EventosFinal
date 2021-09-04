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
import py.com.metre.administracionBase.ejb.FormaPagoEJB;
import py.com.metre.administracionBase.jpa.FormaPago;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorFormaPago implements Serializable {

    Logger logger = Logger.getLogger(FormaPago.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private FormaPagoEJB formaPagoEJB;
    private FormaPago formaPagoSeleccionado;
    private FormaPago formaPagoNuevo;
    private List<FormaPago> listaFormaPago;
    private Boolean deshabilitar;
    private
    @Inject
    LoginControlador loginControlador;

    public FormaPago getFormaPagoNuevo() {
        if (formaPagoNuevo == null) {
            this.formaPagoNuevo = new FormaPago();
        }
        return this.formaPagoNuevo;
    }

    public void setFormaPagoNuevo(FormaPago formaPago) {
        if (formaPago != null) {
            this.formaPagoNuevo = formaPago;
        }
    }

    public FormaPago getFormaPagoSeleccionado() {
        if (formaPagoSeleccionado == null) {
            formaPagoSeleccionado = new FormaPago();
            return formaPagoSeleccionado;
        }
        return formaPagoSeleccionado;
    }

    public void setFormaPagoSeleccionado(FormaPago formaPago) {
        if (formaPagoSeleccionado != null) {
            this.formaPagoSeleccionado = formaPago;
        }
    }

    public List<FormaPago> getListaFormaPago() {
        listaFormaPago = formaPagoEJB.listarTodos();
        return listaFormaPago;
    }

    public void setListaFormaPago(List<FormaPago> listaFormaPago) {
        this.listaFormaPago = listaFormaPago;
    }

    public void update() {
        try {
            if (this.formaPagoSeleccionado != null) {
                formaPagoEJB.actualizar(formaPagoSeleccionado);
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
            formaPagoEJB.insertar(formaPagoNuevo);
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
            if (this.formaPagoSeleccionado != null) {
                formaPagoEJB.eliminar(formaPagoSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        formaPagoSeleccionado = null;
        formaPagoNuevo = null;
    }

    public Boolean getDeshabilitar() {
        return deshabilitar;
    }

    public void setDeshabilitar(Boolean deshabilitar) {
         this.deshabilitar = deshabilitar; 
    }
}
