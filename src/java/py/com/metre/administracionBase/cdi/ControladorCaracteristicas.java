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
import py.com.metre.administracionBase.ejb.CaracteristicasProductoEJB; 
import py.com.metre.administracionBase.ejb.ProductoEJB;
import py.com.metre.administracionBase.ejb.AtributoTipoProductoEJB;
import py.com.metre.administracionBase.jpa.CaracteristicasProducto;
import py.com.metre.administracionBase.jpa.Producto;
import py.com.metre.administracionBase.jpa.AtributoTipoProducto;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorCaracteristicas implements Serializable {
/*
    Logger logger = Logger.getLogger(CaracteristicasProducto.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private CaracteristicasProductoEJB caracteriEJB;
    private CaracteristicasProducto caracteriSeleccionado;
    private CaracteristicasProducto caracteriNuevo;
    private List<CaracteristicasProducto> listaCaracteri;
    private Producto productoSeleccionado;
    private List<Producto> listaProducto;
     private AtributoTipoProducto atributoSeleccionado;
    private List<AtributoTipoProducto> listaAtributo;
    
    @EJB
    private ProductoEJB ProductoEJB;
     @EJB
    private AtributoTipoProductoEJB AtributoEJB;
    private
    @Inject
    LoginControlador loginControlador;

    
    public CaracteristicasProducto getCaracteriNuevo() {
        if (caracteriNuevo == null) {
            this.caracteriNuevo = new CaracteristicasProducto();
        }
        return this.caracteriNuevo;
    }

    public void setCaracteriNuevo(CaracteristicasProducto caracteri) {
        if (caracteri != null) {
            this.caracteriNuevo = caracteri;
        }
    }

    public CaracteristicasProducto getCaracteriSeleccionado() {
        if (caracteriSeleccionado == null) {
            caracteriSeleccionado = new CaracteristicasProducto();
            return caracteriSeleccionado;
        }
        return caracteriSeleccionado;
    }

    public void setCaracteriSeleccionado(CaracteristicasProducto caracteri) {
        if (caracteri != null) {
            this.caracteriSeleccionado = caracteri;
        }
    }

    public List<CaracteristicasProducto> getListaCaracteri() {
        listaCaracteri = caracteriEJB.listarTodos();
        return listaCaracteri;
    }

    public void setListaCaracteri(List<CaracteristicasProducto> listaCaracteri) {
        this.listaCaracteri = listaCaracteri;
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
     
     public List<AtributoTipoProducto> getAtributo() {
        try {
            listaAtributo = AtributoEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Producto:" + ex.getMessage());
            return null;
        }
        return listaAtributo;
    }
       public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        if (productoSeleccionado != null) {
            this.productoSeleccionado = productoSeleccionado;
            if(this.caracteriSeleccionado != null){
                this.caracteriSeleccionado.setIdProducto(productoSeleccionado);   
            }
        }
    }
       public AtributoTipoProducto getAtributoSeleccionado() {
        return atributoSeleccionado;
    }

    public void setAtributoSeleccionado(AtributoTipoProducto atributo) {
        if (atributo != null) {
            this.atributoSeleccionado = atributo;
            if(this.caracteriSeleccionado != null){
                this.caracteriSeleccionado.setIdAtributo(atributo);   
            }
        }
    }
    
    public void update() {
        try {
            if (this.caracteriSeleccionado != null ) {
                caracteriEJB.actualizar(caracteriSeleccionado);
                //caracteriSeleccionado.setIdTipoProducto(productoSeleccionado);
                //caracteriEJB.actualizar(caracteriSeleccionado);
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
            caracteriNuevo.setIdProducto(productoSeleccionado);
             caracteriNuevo.setIdAtributo(atributoSeleccionado);
            caracteriEJB.insertar(caracteriNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }
    private boolean validar() {
        if (productoSeleccionado == null || productoSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Producto", "Debe seleccionar un Producto");
            return false;
        }
        if (atributoSeleccionado == null || atributoSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Atributo", "Debe seleccionar un atributo");
            return false;
        }
         return true;
    }

    public void delete() {
        try {
            if (this.caracteriSeleccionado != null) {
                caracteriEJB.eliminar(caracteriSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        caracteriSeleccionado = null;
        caracteriNuevo = null;
        productoSeleccionado= null;
        atributoSeleccionado= null;
    } */
} 
