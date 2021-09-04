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
import py.com.metre.administracionBase.ejb.SucursalEJB;
import py.com.metre.administracionBase.jpa.Sucursal;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorSucursal implements Serializable {

    Logger logger = Logger.getLogger(Sucursal.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private SucursalEJB sucursalEJB;
    private Sucursal sucursalSeleccionado;
    private Sucursal sucursalNuevo;
    private List<Sucursal> listaSucursales;
    private Boolean deshabilitar;
    private
    @Inject
    LoginControlador loginControlador;

    public Sucursal getSucursalNuevo() {
        if (sucursalNuevo == null) {
            this.sucursalNuevo = new Sucursal();
        }
        return this.sucursalNuevo;
    }

    public void setSucursalNuevo(Sucursal sucursal) {
        if (sucursal != null) {
            this.sucursalNuevo = sucursal;
        }
    }

    public Sucursal getSucursalSeleccionado() {
        if (sucursalSeleccionado == null) {
            sucursalSeleccionado = new Sucursal();
            return sucursalSeleccionado;
        }
        return sucursalSeleccionado;
    }

    public void setSucursalSeleccionado(Sucursal sucursal) {
        if (sucursalSeleccionado != null) {
            this.sucursalSeleccionado = sucursal;
        }
    }

    public List<Sucursal> getListaSucursal() {
        listaSucursales = sucursalEJB.listarTodos();
        return listaSucursales;
    }

    public void setListaSucursal(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public void update() {
        try {
            if (this.sucursalSeleccionado != null) {
                sucursalEJB.actualizar(sucursalSeleccionado);
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
            sucursalEJB.insertar(sucursalNuevo);
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
            if (this.sucursalSeleccionado != null) {
                sucursalEJB.eliminar(sucursalSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        sucursalSeleccionado = null;
        sucursalNuevo = null;
    }

    public Boolean getDeshabilitar() {
        return deshabilitar;
    }

    public void setDeshabilitar(Boolean deshabilitar) {
         this.deshabilitar = deshabilitar; 
    }
    
}
