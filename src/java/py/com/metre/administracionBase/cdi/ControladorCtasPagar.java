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
import py.com.metre.administracionBase.ejb.CuentasPagarEJB; 
import py.com.metre.administracionBase.ejb.ComprasEJB;
import py.com.metre.administracionBase.jpa.CuentasPagar;
import py.com.metre.administracionBase.jpa.Compras;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorCtasPagar implements Serializable {

    Logger logger = Logger.getLogger(CuentasPagar.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private CuentasPagarEJB cuentasEJB;
    private CuentasPagar cuentasSeleccionado;
    private CuentasPagar cuentasNuevo;
    private List<CuentasPagar> listaCuentasPagar;
    private Compras compraSeleccionado;
    private List<Compras> listaCompras;
    private Boolean estaPagado;
    
    @EJB
    private ComprasEJB ComprasEJB;
    
    private
    @Inject
    LoginControlador loginControlador;

    
    public CuentasPagar getCuentasNuevo() {
        if (cuentasNuevo == null) {
            this.cuentasNuevo = new CuentasPagar();
        }
        return this.cuentasNuevo;
    }

    public void setCuentasNuevo(CuentasPagar cuentas) {
        if (cuentas != null) {
            this.cuentasNuevo = cuentas;
        }
    }

    public CuentasPagar getCuentasSeleccionado() {
        if (cuentasSeleccionado == null) {
            cuentasSeleccionado = new CuentasPagar();
            return cuentasSeleccionado;
        }
        return cuentasSeleccionado;
    }

    public void setCuentasSeleccionado(CuentasPagar cuentas) {
        if (cuentas != null) {
            this.cuentasSeleccionado = cuentas;
            if (cuentasSeleccionado.getEstado().equals("pagado")){
            estaPagado= true;
            }else if (cuentasSeleccionado.getEstado().equals("pendiente")){
                estaPagado= false;
            }
        }
    }

    public List<CuentasPagar> getListaCuentasPagar() {
        listaCuentasPagar = cuentasEJB.listarTodos();
        return listaCuentasPagar;
    }

    public void setListaCuentasPagar(List<CuentasPagar> listaCuentasPagar) {
        this.listaCuentasPagar = listaCuentasPagar;
    }

     public List<Compras> getCompras() {
        try {
            listaCompras = ComprasEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Compras:" + ex.getMessage());
            return null;
        }
        return listaCompras;
    }
       public Compras getComprasSeleccionado() {
        return compraSeleccionado;
    }

    public void setComprasSeleccionado(Compras compraSeleccionado) {
        if (compraSeleccionado != null) {
            this.compraSeleccionado = compraSeleccionado;
            if(this.cuentasSeleccionado != null){
                this.cuentasSeleccionado.setComprasid(compraSeleccionado);
            }
        }
    }
    
    public void update() {
        try {
            if (this.cuentasSeleccionado != null ) {
                cuentasEJB.actualizar(cuentasSeleccionado);
                //cuentasSeleccionado.setIdCompras(compraSeleccionado);
                //cuentasEJB.actualizar(cuentasSeleccionado);
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
            cuentasNuevo.setComprasid(compraSeleccionado);
            cuentasEJB.insertar(cuentasNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }
    private boolean validar() {
        if (compraSeleccionado == null || compraSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Compras", "Debe seleccionar un compra");
            return false;
        }
         return true;
    }

    public void delete() {
        try {
            if (this.cuentasSeleccionado != null) {
                cuentasEJB.eliminar(cuentasSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        cuentasSeleccionado = null;
        cuentasNuevo = null;
        compraSeleccionado= null;
    }

    public Boolean getEstaPagado() {
        return estaPagado;
    }

    public void setEstaPagado(Boolean estaPagado) {
        this.estaPagado = estaPagado;
    }
    
    
}





