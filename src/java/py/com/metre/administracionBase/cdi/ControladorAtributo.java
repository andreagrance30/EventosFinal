package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;  /*
import py.com.metre.administracionBase.ejb.AtributoTipoProductoEJB; 
import py.com.metre.administracionBase.ejb.TipoProductoEJB;
import py.com.metre.administracionBase.jpa.AtributoTipoProducto;
import py.com.metre.administracionBase.jpa.TipoProducto;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorAtributo implements Serializable {
   /*
    Logger logger = Logger.getLogger(AtributoTipoProducto.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private AtributoTipoProductoEJB atributoEJB;
    private AtributoTipoProducto atributoSeleccionado;
    private AtributoTipoProducto atributoNuevo;
    private List<AtributoTipoProducto> listaAtributo;
    private TipoProducto tipoProductoSeleccionado;
    private List<TipoProducto> listaTipoProducto;
    
    @EJB
    private TipoProductoEJB TipoProductoEJB;
    
    private
    @Inject
    LoginControlador loginControlador;

    
    public AtributoTipoProducto getAtributoNuevo() {
        if (atributoNuevo == null) {
            this.atributoNuevo = new AtributoTipoProducto();
        }
        return this.atributoNuevo;
    }

    public void setAtributoNuevo(AtributoTipoProducto atributo) {
        if (atributo != null) {
            this.atributoNuevo = atributo;
        }
    }

    public AtributoTipoProducto getAtributoSeleccionado() {
        if (atributoSeleccionado == null) {
            atributoSeleccionado = new AtributoTipoProducto();
            return atributoSeleccionado;
        }
        return atributoSeleccionado;
    }

    public void setAtributoSeleccionado(AtributoTipoProducto atributo) {
        if (atributo != null) {
            this.atributoSeleccionado = atributo;
        }
    }

    public List<AtributoTipoProducto> getListaAtributo() {
        listaAtributo = atributoEJB.listarTodos();
        return listaAtributo;
    }

    public void setListaAtributo(List<AtributoTipoProducto> listaAtributo) {
        this.listaAtributo = listaAtributo;
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
            if(this.atributoSeleccionado != null){
                this.atributoSeleccionado.setIdTipoProducto(tipoProductoSeleccionado);
            }
        }
    }
    
    public void update() {
        try {
            if (this.atributoSeleccionado != null ) {
                atributoEJB.actualizar(atributoSeleccionado);
                //atributoSeleccionado.setIdTipoProducto(tipoProductoSeleccionado);
                //atributoEJB.actualizar(atributoSeleccionado);
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
            atributoNuevo.setIdTipoProducto(tipoProductoSeleccionado);
            atributoEJB.insertar(atributoNuevo);
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
         return true;
    }

    public void delete() {
        try {
            if (this.atributoSeleccionado != null) {
                atributoEJB.eliminar(atributoSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        atributoSeleccionado = null;
        atributoNuevo = null;
        tipoProductoSeleccionado= null;
    }*/
}
