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
import py.com.metre.administracionBase.ejb.ProductoEJB; 
import py.com.metre.administracionBase.ejb.ClasiProductoEJB;
import py.com.metre.administracionBase.ejb.TipoProductoEJB;
import py.com.metre.administracionBase.jpa.Producto;
import py.com.metre.administracionBase.jpa.ClasificacionProducto;
import py.com.metre.administracionBase.jpa.TipoProducto;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorProducto implements Serializable {

    Logger logger = Logger.getLogger(Producto.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private ProductoEJB productoEJB;
    private Producto productoSeleccionado;
    private Producto productoNuevo;
    private List<Producto> listaProducto;
    private TipoProducto tipoProductoSeleccionado;
    private List<TipoProducto> listaTipoProducto;
    private ClasificacionProducto clasiSeleccionado;
    private List<ClasificacionProducto> listaClasi;
    
    @EJB
    private TipoProductoEJB TipoProductoEJB;
    @EJB     /// nunca nunca olvidar estos putos @EJB de otra manera no funciona el metodo "listarTodos()"
    private ClasiProductoEJB clasiEJB;
    
    private
    @Inject
    LoginControlador loginControlador;

    
    public Producto getProductoNuevo() {
        if (productoNuevo == null) {
            this.productoNuevo = new Producto();
        }
        return this.productoNuevo;
    }

    public void setProductoNuevo(Producto producto) {
        if (producto != null) {
            this.productoNuevo = producto;
        }
    }

    public Producto getProductoSeleccionado() {
        if (productoSeleccionado == null) {
            productoSeleccionado = new Producto();
            return productoSeleccionado;
        }
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto producto) {
        if (producto != null) {
            this.productoSeleccionado = producto;
        }
    }

    public List<Producto> getListaProducto() {
        listaProducto = productoEJB.listarTodos();
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

     public List<TipoProducto> getTipoProducto() {
        try {
            listaTipoProducto = TipoProductoEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de TipoProducto:" + ex.getMessage());
            return null;
        }
        return listaTipoProducto;
    }
       public TipoProducto getTipoProductoSeleccionado() {
        return tipoProductoSeleccionado;
    }

    public void setTipoProductoSeleccionado(TipoProducto tipoProductoSeleccionado) {
        if (tipoProductoSeleccionado != null) {
            this.tipoProductoSeleccionado = tipoProductoSeleccionado;
            if(this.productoSeleccionado != null){
                this.productoSeleccionado.setTipoProductoid(tipoProductoSeleccionado);
            }
        }
    }
     public List<ClasificacionProducto> getListaClasi() {
        try {
            listaClasi = clasiEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Clasificacion:" + ex.getMessage());
            return null;
        }
        return listaClasi;
    }
    
      public ClasificacionProducto getClasiSeleccionado() {
        return clasiSeleccionado;
    }
      
        public void setClasiSeleccionado(ClasificacionProducto clasiSeleccionado) {
        if (clasiSeleccionado != null) {
            this.clasiSeleccionado = clasiSeleccionado;
            if(this.productoSeleccionado != null){
               this.productoSeleccionado.setClasificacionProductoid(clasiSeleccionado);
            }
        }
    }
    
    public void update() {
        try {
            if (this.productoSeleccionado != null ) {
                productoEJB.actualizar(productoSeleccionado);
                productoSeleccionado.setTipoProductoid(tipoProductoSeleccionado);
                productoEJB.actualizar(productoSeleccionado);
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
            productoNuevo.setTipoProductoid(tipoProductoSeleccionado);
           productoNuevo.setClasificacionProductoid(clasiSeleccionado);
      //      productoNuevo.setSucursalid(sucursalSeleccionado);
            productoEJB.insertar(productoNuevo); 
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }
    private boolean validar() {
        if (tipoProductoSeleccionado == null || tipoProductoSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("TipoProducto", "Debe seleccionar un tipoProducto");
            return false;
        }
        if (clasiSeleccionado == null || clasiSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Clasificacion", "Debe seleccionar una clasificacion");
            return false;
        }
         return true;
    }

    public void delete() {
        try {
            if (this.productoSeleccionado != null) {
                productoEJB.eliminar(productoSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        productoSeleccionado = null;
        productoNuevo = null;
        tipoProductoSeleccionado= null;
         clasiSeleccionado= null;
    }
}
