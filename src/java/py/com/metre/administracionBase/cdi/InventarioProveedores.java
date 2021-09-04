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
import py.com.metre.administracionBase.ejb.ProveedorEJB;
import py.com.metre.administracionBase.jpa.Proveedor;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * 
 */
@Named
@SessionScoped
public class InventarioProveedores implements Serializable {

    Logger logger = Logger.getLogger(Proveedor.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private ProveedorEJB proveedoresEJB;
    private Proveedor proveedorSeleccionado;
    private Proveedor proveedorNuevo;
    private List<Proveedor> listaProveedores;
    private
    @Inject
    LoginControlador loginControlador;

    public Proveedor getProveedorNuevo() {
        if (proveedorNuevo == null) {
            this.proveedorNuevo = new Proveedor();
        }
        return this.proveedorNuevo;
    }

    public void setProveedorNuevo(Proveedor proveedor) {
        if (proveedor != null) {
            this.proveedorNuevo = proveedor;
        }
    }

    public Proveedor getProveedorSeleccionado() {
        if (proveedorSeleccionado == null) {
            proveedorSeleccionado = new Proveedor();
            return proveedorSeleccionado;
        }
        return proveedorSeleccionado;
    }

    public void setProveedorSeleccionado(Proveedor insumo) {
        if (proveedorSeleccionado != null) {
            this.proveedorSeleccionado = insumo;
        }
    }

    public List<Proveedor> getListaProveedores() {
        listaProveedores = proveedoresEJB.listarTodos();
        return listaProveedores;
    }

    public void update() {
        try {
            if (this.proveedorSeleccionado != null) {
                proveedoresEJB.actualizar(proveedorSeleccionado);
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
            proveedoresEJB.insertar(proveedorNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }

    public void delete() {
        try {
            if (this.proveedorSeleccionado != null) {
                proveedoresEJB.eliminar(proveedorSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        proveedorSeleccionado = null;
        proveedorNuevo = null;
    }

}
