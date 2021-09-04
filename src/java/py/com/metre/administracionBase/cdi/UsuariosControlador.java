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
import py.com.metre.administracionBase.ejb.UsuarioEJB;
import py.com.metre.administracionBase.jpa.PerfilUsuario;
import py.com.metre.administracionBase.jpa.Usuario;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author
 */
@Named
@SessionScoped
public class UsuariosControlador implements Serializable {

    Logger logger = Logger.getLogger(Usuario.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private UsuarioEJB usuariosEJB;
    private Usuario usuarioSeleccionado;
    private Usuario usuarioNuevo;
    private List<Usuario> listaUsuarios;
    private PerfilUsuario perfilUsuarioSeleccionado;
    private List<PerfilUsuario> listaPerfilUsuario;
    
    @EJB
    private PerfilUsuarioEJB PerfilUsuarioEJB;
    private
    @Inject
    LoginControlador loginControlador;

    public Usuario getUsuarioNuevo() {
        if (usuarioNuevo == null) {
            this.usuarioNuevo = new Usuario();
            //this.usuarioNuevo.setActivo(true);
        }
        return this.usuarioNuevo;
    }

    public void setUsuarioNuevo(Usuario usuario) {
        if (usuario != null) {
            this.usuarioNuevo = usuario;
        }
    }

    public Usuario getUsuarioSeleccionado() {
        if (usuarioSeleccionado == null) {
            usuarioSeleccionado = new Usuario();
            return usuarioSeleccionado;
        }
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuario) {
        if (usuarioSeleccionado != null) {
            this.usuarioSeleccionado = usuario;
        }
    }

    public List<Usuario> getListaUsuarios() {
        listaUsuarios = usuariosEJB.listarTodos();
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    
     public List<PerfilUsuario> getPerfilUsuario() {
        try {
            listaPerfilUsuario = PerfilUsuarioEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de PerfilUsuario:" + ex.getMessage());
            return null;
        }
        return listaPerfilUsuario;
    }
       public PerfilUsuario getPerfilUsuarioSeleccionado() {
        return perfilUsuarioSeleccionado;
    }

    public void setPerfilUsuarioSeleccionado(PerfilUsuario perfilUsuarioSeleccionado) {
        if (perfilUsuarioSeleccionado != null) {
            this.perfilUsuarioSeleccionado = perfilUsuarioSeleccionado;
        }
    }

    public void update() {
        try {
            if (this.usuarioSeleccionado != null) {
                usuariosEJB.actualizar(usuarioSeleccionado);
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
             usuarioNuevo.setPerfilUsuarioid(perfilUsuarioSeleccionado);
            usuariosEJB.insertar(usuarioNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            System.out.println("EXCEPTION: " + ex.getMessage());
            resetearCampos();
        }
    }

    public void delete() {
        try {
            if (this.usuarioSeleccionado != null) {
                usuariosEJB.eliminar(usuarioSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        usuarioSeleccionado = null;
        usuarioNuevo = null;
        perfilUsuarioSeleccionado= null;
    }
}
