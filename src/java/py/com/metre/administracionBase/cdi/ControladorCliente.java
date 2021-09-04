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
import py.com.metre.administracionBase.ejb.ClienteEJB; 
import py.com.metre.administracionBase.ejb.BarrioEJB;
import py.com.metre.administracionBase.jpa.Cliente;
import py.com.metre.administracionBase.jpa.Barrio;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorCliente implements Serializable {

    Logger logger = Logger.getLogger(Cliente.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private ClienteEJB clienteEJB;
    private Cliente clienteSeleccionado;
    private Cliente clienteNuevo;
    private List<Cliente> listaCliente;
    private Barrio barrioSeleccionado;
    private List<Barrio> listaBarrio;
    
    @EJB
    private BarrioEJB BarrioEJB;
    
    private
    @Inject
    LoginControlador loginControlador;

    
    public Cliente getClienteNuevo() {
        if (clienteNuevo == null) {
            this.clienteNuevo = new Cliente();
        }
        return this.clienteNuevo;
    }

    public void setClienteNuevo(Cliente cliente) {
        if (cliente != null) {
            this.clienteNuevo = cliente;
        }
    }

    public Cliente getClienteSeleccionado() {
        if (clienteSeleccionado == null) {
            clienteSeleccionado = new Cliente();
            return clienteSeleccionado;
        }
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente cliente) {
        if (cliente != null) {
            this.clienteSeleccionado = cliente;
        }
    }

    public List<Cliente> getListaCliente() {
        listaCliente = clienteEJB.listarTodos();
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
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
            if(this.clienteSeleccionado != null){
                this.clienteSeleccionado.setBarrioid(barrioSeleccionado);
            }
        }
    }
    
    public void update() {
        try {
            if (this.clienteSeleccionado != null ) {
                clienteEJB.actualizar(clienteSeleccionado);
                //clienteSeleccionado.setIdBarrio(barrioSeleccionado);
                //clienteEJB.actualizar(clienteSeleccionado);
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
            clienteNuevo.setBarrioid(barrioSeleccionado);
            clienteEJB.insertar(clienteNuevo);
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
            if (this.clienteSeleccionado != null) {
                clienteEJB.eliminar(clienteSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        clienteSeleccionado = null;
        clienteNuevo = null;
        barrioSeleccionado= null;
    }
}
