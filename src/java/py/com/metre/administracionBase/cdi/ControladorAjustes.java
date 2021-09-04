package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.event.DateSelectEvent;
import py.com.metre.administracionBase.ejb.AjustesEJB;
import py.com.metre.administracionBase.jpa.Ajustes;
import py.com.metre.administracionBase.ejb.ProductoEJB;
import py.com.metre.administracionBase.ejb.VentasEJB;
import py.com.metre.administracionBase.jpa.Producto;
import py.com.metre.administracionBase.jpa.Ventas;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorAjustes implements Serializable {

    Logger logger = Logger.getLogger(Ajustes.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
   
    private AjustesEJB ajustesEJB;
    private Ajustes ajustesSeleccionado;
    private Ajustes ajustesNuevo;
    private List<Ajustes> listaAjustes;
    private Producto productoSeleccionado;
    private List<Producto> listaProducto;
    private Ventas ventasSeleccionado;
    private List<Ventas> listaVentas;
    
    @EJB
    private ProductoEJB ProductoEJB;
    @EJB
    private VentasEJB VentasEJB;
    
    
    private
    @Inject
    LoginControlador loginControlador;

    
    public Ajustes getAjustesNuevo() {
        if (ajustesNuevo == null) {
            this.ajustesNuevo = new Ajustes();
        }
        return this.ajustesNuevo;
    }

    public void setAjustesNuevo(Ajustes ajustes) {
        if (ajustes != null) {
            this.ajustesNuevo = ajustes;
        }
    }

    public Ajustes getAjustesSeleccionado() {
        if (ajustesSeleccionado == null) {
            ajustesSeleccionado = new Ajustes();
            return ajustesSeleccionado;
        }
        return ajustesSeleccionado;
    }

    public void setAjustesSeleccionado(Ajustes ajustes) {
        if (ajustes != null) {
            this.ajustesSeleccionado = ajustes;
        }
    }

    public List<Ajustes> getListaAjustes() {
        listaAjustes = ajustesEJB.listarTodos();
        return listaAjustes;
    }

    public void setListaAjustes(List<Ajustes> listaAjustes) {
        this.listaAjustes = listaAjustes;
    }
    
        public List<Producto> getProducto() {
        try {
            listaProducto = ProductoEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Producto:" + ex.getMessage());
            return null;
        }
        return listaProducto;
    }
       public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        if (productoSeleccionado != null) {
            this.productoSeleccionado = productoSeleccionado;
            ajustesSeleccionado.setProductoid(productoSeleccionado);
        }
    }
    
          public List<Ventas> getVentas() {
        try {
            listaVentas = VentasEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Producto:" + ex.getMessage());
            return null;
        }
        return listaVentas;
    }
       public Ventas getVentasSeleccionado() {
        return ventasSeleccionado;
    }

    public void setVentasSeleccionado(Ventas ventaseleccionado) {
        if (ventasSeleccionado != null) {
            this.ventasSeleccionado = ventasSeleccionado;
            ajustesSeleccionado.setVentasid(ventasSeleccionado);
        }
    }
    /*
    public void update() {
        try {
            if (this.ajustesSeleccionado != null ) {
                ajustesEJB.actualizar(ajustesSeleccionado);
                JsfUtil.agregarMensajeExito("Se ha actualizado correctamente.");
                resetearCampos();
            }
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "No se pudo actualizar: " + ex);
            resetearCampos();
        }
    }
*/
    public void add() {
        
        try {
            ajustesNuevo.setProductoid(productoSeleccionado);
            ajustesNuevo.setVentasid(ventasSeleccionado);
            ajustesEJB.insertar(ajustesNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }
    /*
    private boolean validar() {
        if (productoSeleccionado == null || productoSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Producto", "Debe seleccionar un producto");
            return false;
        }
         if (ventasSeleccionado == null || ventasSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Ventas", "Debe seleccionar una venta");
            return false;
        }
         return true;
    } */

    public void delete() {
        try {
            if (this.ajustesSeleccionado != null) {
                ajustesEJB.eliminar(ajustesSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        ajustesSeleccionado = null;
        ajustesNuevo = null;
        productoSeleccionado= null;
        ventasSeleccionado= null;
    }
    
}
