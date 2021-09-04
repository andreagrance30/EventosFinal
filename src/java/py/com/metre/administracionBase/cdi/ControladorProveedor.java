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
import py.com.metre.administracionBase.ejb.BarrioEJB;
import py.com.metre.administracionBase.jpa.Proveedor;
import py.com.metre.administracionBase.jpa.Barrio;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorProveedor implements Serializable {

    Logger logger = Logger.getLogger(Proveedor.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private ProveedorEJB proveedorEJB;
    private Proveedor proveedorSeleccionado;
    private Proveedor proveedorNuevo;
    private List<Proveedor> listaProveedor;
    private Barrio barrioSeleccionado;
    private List<Barrio> listaBarrio;
    
    @EJB
    private BarrioEJB BarrioEJB;
    
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

    public void setProveedorSeleccionado(Proveedor proveedor) {
        if (proveedor != null) {
            this.proveedorSeleccionado = proveedor;
        }
    }

    public List<Proveedor> getListaProveedor() {
        listaProveedor = proveedorEJB.listarTodos();
        return listaProveedor;
    }

    public void setListaProveedor(List<Proveedor> listaProveedor) {
        this.listaProveedor = listaProveedor;
    }

     public List<Barrio> getBarrio() {
        try {
            listaBarrio = BarrioEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Barrio:" + ex.getMessage());
            return null;
        }
        return listaBarrio;
    }
       public Barrio getBarrioSeleccionado() {
        return barrioSeleccionado;
    }

    public void setBarrioSeleccionado(Barrio barrioSeleccionado) {
        if (barrioSeleccionado != null) {
            this.barrioSeleccionado = barrioSeleccionado;
            if(this.proveedorSeleccionado != null){
                this.proveedorSeleccionado.setBarrioid(barrioSeleccionado);
            }
        }
    }
    
    public void update() {
        try {
            if (this.proveedorSeleccionado != null ) {
                proveedorEJB.actualizar(proveedorSeleccionado);
                //proveedorSeleccionado.setIdBarrio(barrioSeleccionado);
                //proveedorEJB.actualizar(proveedorSeleccionado);
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
            proveedorNuevo.setBarrioid(barrioSeleccionado);
            proveedorEJB.insertar(proveedorNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }
    private boolean validar() {
        if (barrioSeleccionado == null || barrioSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Barrio", "Debe seleccionar un barrio");
            return false;
        }
         return true;
    }

    public void delete() {
        try {
            if (this.proveedorSeleccionado != null) {
                proveedorEJB.eliminar(proveedorSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        proveedorSeleccionado = null;
        proveedorNuevo = null;
        barrioSeleccionado= null;
    }
}
