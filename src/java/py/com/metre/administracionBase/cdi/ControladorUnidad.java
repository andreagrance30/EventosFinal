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
import py.com.metre.administracionBase.ejb.UnidadMedidaEJB;
import py.com.metre.administracionBase.jpa.UnidadDeMedida;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorUnidad implements Serializable {

    Logger logger = Logger.getLogger(UnidadDeMedida.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private UnidadMedidaEJB unidadMedidaEJB;
    private UnidadDeMedida unidadMedidaSeleccionado;
    private UnidadDeMedida unidadMedidaNuevo;
    private List<UnidadDeMedida> listaUnidadDeMedida;
    private Boolean deshabilitar;
    private
    @Inject
    LoginControlador loginControlador;

    public UnidadDeMedida getUnidadDeMedidaNuevo() {
        if (unidadMedidaNuevo == null) {
            this.unidadMedidaNuevo = new UnidadDeMedida();
        }
        return this.unidadMedidaNuevo;
    }

    public void setUnidadDeMedidaNuevo(UnidadDeMedida unidadMedida) {
        if (unidadMedida != null) {
            this.unidadMedidaNuevo = unidadMedida;
        }
    }

    public UnidadDeMedida getUnidadDeMedidaSeleccionado() {
        if (unidadMedidaSeleccionado == null) {
            unidadMedidaSeleccionado = new UnidadDeMedida();
            return unidadMedidaSeleccionado;
        }
        return unidadMedidaSeleccionado;
    }

    public void setUnidadDeMedidaSeleccionado(UnidadDeMedida unidadMedida) {
        if (unidadMedidaSeleccionado != null) {
            this.unidadMedidaSeleccionado = unidadMedida;
        }
    }

    public List<UnidadDeMedida> getListaUnidadDeMedida() {
        listaUnidadDeMedida = unidadMedidaEJB.listarTodos();
        return listaUnidadDeMedida;
    }

    public void setListaUnidadDeMedida(List<UnidadDeMedida> listaUnidadDeMedida) {
        this.listaUnidadDeMedida = listaUnidadDeMedida;
    }

    public void update() {
        try {
            if (this.unidadMedidaSeleccionado != null) {
                unidadMedidaEJB.actualizar(unidadMedidaSeleccionado);
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
            unidadMedidaEJB.insertar(unidadMedidaNuevo);
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
            if (this.unidadMedidaSeleccionado != null) {
                unidadMedidaEJB.eliminar(unidadMedidaSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        unidadMedidaSeleccionado = null;
        unidadMedidaNuevo = null;
    }

    public Boolean getDeshabilitar() {
        return deshabilitar;
    }

    public void setDeshabilitar(Boolean deshabilitar) {
         this.deshabilitar = deshabilitar; 
    }
}
