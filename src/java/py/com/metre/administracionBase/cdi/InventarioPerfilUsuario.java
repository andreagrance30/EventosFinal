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
import py.com.metre.administracionBase.ejb.PerfilUsuarioEJB;
import py.com.metre.administracionBase.jpa.PerfilUsuario;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * 
 */
@Named
@SessionScoped
public class InventarioPerfilUsuario implements Serializable {

    Logger logger = Logger.getLogger(PerfilUsuario.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private PerfilUsuarioEJB perfilUsuarioEJB;
    private PerfilUsuario perfilUsuarioeleccionado;
    private PerfilUsuario perfilUsuarioNuevo;
    private List<PerfilUsuario> listaPerfilUsuario;
    private
    @Inject
    LoginControlador loginControlador;

    public PerfilUsuario getInsumoNuevo() {
        if (perfilUsuarioNuevo == null) {
            this.perfilUsuarioNuevo = new PerfilUsuario();
        }
        return this.perfilUsuarioNuevo;
    }

    public void setInsumoNuevo(PerfilUsuario perfilUsuario) {
        if (perfilUsuario != null) {
            this.perfilUsuarioNuevo = perfilUsuario;
        }
    }

    public PerfilUsuario getInsumoSeleccionado() {
        if (perfilUsuarioeleccionado == null) {
            perfilUsuarioeleccionado = new PerfilUsuario();
            return perfilUsuarioeleccionado;
        }
        return perfilUsuarioeleccionado;
    }

    public void setInsumoSeleccionado(PerfilUsuario perfilUsuario) {
        if (perfilUsuarioeleccionado != null) {
            this.perfilUsuarioeleccionado = perfilUsuario;
        }
    }

    public List<PerfilUsuario> getListaPerfilUsuario() {
        listaPerfilUsuario = perfilUsuarioEJB.listarTodos();
        return listaPerfilUsuario;
    }

    public void setListaPerfilUsuario(List<PerfilUsuario> listaPerfilUsuario) {
        this.listaPerfilUsuario = listaPerfilUsuario;
    }

    public void update() {
        try {
            if (this.perfilUsuarioeleccionado != null) {
                perfilUsuarioEJB.actualizar(perfilUsuarioeleccionado);
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
            perfilUsuarioEJB.insertar(perfilUsuarioNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }

    public void delete() {
        try {
            if (this.perfilUsuarioeleccionado != null) {
                perfilUsuarioEJB.eliminar(perfilUsuarioeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        perfilUsuarioeleccionado = null;
        perfilUsuarioNuevo = null;
    }

}
