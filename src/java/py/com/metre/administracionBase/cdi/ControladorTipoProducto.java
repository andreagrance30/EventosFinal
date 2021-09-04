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
import py.com.metre.administracionBase.ejb.TipoProductoEJB;
import py.com.metre.administracionBase.jpa.TipoProducto;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorTipoProducto implements Serializable {

    Logger logger = Logger.getLogger(TipoProducto.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private TipoProductoEJB tipoProductoEJB;
    private TipoProducto tipoProductoSeleccionado;
    private TipoProducto tipoProductoNuevo;
    private List<TipoProducto> listaTipoProducto;
    private
    @Inject
    LoginControlador loginControlador;

    public TipoProducto getTipoProductoNuevo() {
        if (tipoProductoNuevo == null) {
            this.tipoProductoNuevo = new TipoProducto();
        }
        return this.tipoProductoNuevo;
    }

    public void setTipoProductoNuevo(TipoProducto tipoProducto) {
        if (tipoProducto != null) {
            this.tipoProductoNuevo = tipoProducto;
        }
    }

    public TipoProducto getTipoProductoSeleccionado() {
        if (tipoProductoSeleccionado == null) {
            tipoProductoSeleccionado = new TipoProducto();
            return tipoProductoSeleccionado;
        }
        return tipoProductoSeleccionado;
    }

    public void setTipoProductoSeleccionado(TipoProducto tipoProducto) {
        if (tipoProductoSeleccionado != null) {
            this.tipoProductoSeleccionado = tipoProducto;
        }
    }

    public List<TipoProducto> getListaTipoProducto() {
        listaTipoProducto = tipoProductoEJB.listarTodos();
        return listaTipoProducto;
    }

    public void setListaTipoProducto(List<TipoProducto> listaTipoProducto) {
        this.listaTipoProducto = listaTipoProducto;
    }

    public void update() {
        try {
            if (this.tipoProductoSeleccionado != null) {
                tipoProductoEJB.actualizar(tipoProductoSeleccionado);
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
            tipoProductoEJB.insertar(tipoProductoNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }

    public void delete() {
        try {
            if (this.tipoProductoSeleccionado != null) {
                tipoProductoEJB.eliminar(tipoProductoSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        tipoProductoSeleccionado = null;
        tipoProductoNuevo = null;
    }
}
