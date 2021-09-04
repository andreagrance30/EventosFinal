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
import py.com.metre.administracionBase.ejb.ClasiProductoEJB;
import py.com.metre.administracionBase.jpa.ClasificacionProducto;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorClasificacion implements Serializable {

    Logger logger = Logger.getLogger(ClasificacionProducto.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private ClasiProductoEJB clasiProductoEJB;
    private ClasificacionProducto clasiProdSeleccionado;
    private ClasificacionProducto clasiProductoNuevo;
    private List<ClasificacionProducto> listaClasiProd;
    private
    @Inject
    LoginControlador loginControlador;

    public ClasificacionProducto getClasiProductoNuevo() {
        if (clasiProductoNuevo == null) {
            this.clasiProductoNuevo = new ClasificacionProducto();
        }
        return this.clasiProductoNuevo;
    }

    public void setClasiProductoNuevo(ClasificacionProducto clasiProducto) {
        if (clasiProducto != null) {
            this.clasiProductoNuevo = clasiProducto;
        }
    }

    public ClasificacionProducto getClasiProdSeleccionado() {
        if (clasiProdSeleccionado == null) {
            clasiProdSeleccionado = new ClasificacionProducto();
            return clasiProdSeleccionado;
        }
        return clasiProdSeleccionado;
    }

    public void setClasiProdSeleccionado(ClasificacionProducto clasiProducto) {
        if (clasiProdSeleccionado != null) {
            this.clasiProdSeleccionado = clasiProducto;
        }
    }

    public List<ClasificacionProducto> getListaClasiProd() {
        listaClasiProd = clasiProductoEJB.listarTodos();
        return listaClasiProd;
    }

    public void setListaClasiProd(List<ClasificacionProducto> listaClasiProd) {
        this.listaClasiProd = listaClasiProd;
    }

    public void update() {
        try {
            if (this.clasiProdSeleccionado != null) {
                clasiProductoEJB.actualizar(clasiProdSeleccionado);
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
            clasiProductoEJB.insertar(clasiProductoNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }

    public void delete() {
        try {
            if (this.clasiProdSeleccionado != null) {
                clasiProductoEJB.eliminar(clasiProdSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        clasiProdSeleccionado = null;
        clasiProductoNuevo = null;
    }
}
