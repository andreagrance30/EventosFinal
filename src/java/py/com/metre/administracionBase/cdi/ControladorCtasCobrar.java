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
import py.com.metre.administracionBase.ejb.CuentasCobrarEJB; 
import py.com.metre.administracionBase.ejb.CobrosEJB;
import py.com.metre.administracionBase.jpa.CuentasCobrar;
import py.com.metre.administracionBase.jpa.Cobros;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorCtasCobrar implements Serializable {

    Logger logger = Logger.getLogger(CuentasCobrar.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private CuentasCobrarEJB cuentasEJB;
    private CuentasCobrar cuentasSeleccionado;
    private CuentasCobrar cuentasNuevo;
    private List<CuentasCobrar> listaCuentasCobrar;
    private Cobros ventaSeleccionado;
    private List<Cobros> listaCobros;
    private Boolean estaPagado;
    
    @EJB
    private CobrosEJB CobrosEJB;
    
    private
    @Inject
    LoginControlador loginControlador;

    
    public CuentasCobrar getCuentasNuevo() {
        if (cuentasNuevo == null) {
            this.cuentasNuevo = new CuentasCobrar();
        }
        return this.cuentasNuevo;
    }

    public void setCuentasNuevo(CuentasCobrar cuentas) {
        if (cuentas != null) {
            this.cuentasNuevo = cuentas;
        }
    }

    public CuentasCobrar getCuentasSeleccionado() {
        if (cuentasSeleccionado == null) {
            cuentasSeleccionado = new CuentasCobrar();
            return cuentasSeleccionado;
        }
        return cuentasSeleccionado;
    }

    public void setCuentasSeleccionado(CuentasCobrar cuentas) {
        if (cuentas != null) {
            this.cuentasSeleccionado = cuentas;
            if (cuentasSeleccionado.getEstado().equals("pagado")){
            estaPagado= true;
            }else if (cuentasSeleccionado.getEstado().equals("pendiente")){
                estaPagado= false;
            }
        }
    }

    public List<CuentasCobrar> getListaCuentasCobrar() {
        listaCuentasCobrar = cuentasEJB.listarTodos();
        return listaCuentasCobrar;
    }

    public void setListaCuentasCobrar(List<CuentasCobrar> listaCuentasCobrar) {
        this.listaCuentasCobrar = listaCuentasCobrar;
    }

     public List<Cobros> getCobros() {
        try {
            listaCobros = CobrosEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Cobros:" + ex.getMessage());
            return null;
        }
        return listaCobros;
    }
       public Cobros getCobrosSeleccionado() {
        return ventaSeleccionado;
    }

    public void setCobrosSeleccionado(Cobros ventaSeleccionado) {
        if (ventaSeleccionado != null) {
            this.ventaSeleccionado = ventaSeleccionado;
            if(this.cuentasSeleccionado != null){
           //     this.cuentasSeleccionado.setCobrosid(ventaSeleccionado);
            }
        }
    }
    
    public void update() {
        try {
            if (this.cuentasSeleccionado != null ) {
                cuentasEJB.actualizar(cuentasSeleccionado);
                //cuentasSeleccionado.setIdCobros(ventaSeleccionado);
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
//            cuentasNuevo.setCobrosid(ventaSeleccionado);
            cuentasEJB.insertar(cuentasNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }
    private boolean validar() {
        if (ventaSeleccionado == null || ventaSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Cobros", "Debe seleccionar un venta");
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
        ventaSeleccionado= null;
    }

    public Boolean getEstaPagado() {
        return estaPagado;
    }

    public void setEstaPagado(Boolean estaPagado) {
        this.estaPagado = estaPagado;
    }
}





