/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.metre.administracionBase.cdi;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import py.com.metre.administracionBase.ejb.UsuarioEJB;
import py.com.metre.administracionBase.jpa.Usuario;
/**
 *
 * @author adolfo
 */
@Named
@SessionScoped
public class CambioClaveControlador implements Serializable{

    Logger logger = Logger.getLogger(CambioClaveControlador.class);
    @PersistenceContext
    protected EntityManager em;
    private String paginaAct;
    @Inject
    Conversation conversation;

    @EJB
    private UsuarioEJB usuariosEJB;

    private Usuario usuarioLogueado;
    private String claveVieja;
    private String claveNueva;
    private String confirmacionClave;
    private String mensajeError;
    private String mensajeExito;

    private boolean mostrarError;
    private boolean mostrarMensaje;

    private
    @Inject
    LoginControlador loginControlador;

    public CambioClaveControlador(){
        resetearCampos();
    }

    public void resetearCampos(){
        claveVieja = "";
        claveNueva= "";
        confirmacionClave= "";
        mensajeError= "";
        mostrarError= false;
        mensajeExito= "";
        mostrarMensaje= false;
    }

    public Usuario getUsuarioLogueado() {
        this.usuarioLogueado = loginControlador.getUsuario();
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    public String getClaveNueva() {
        return claveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        if(claveNueva != null)
            this.claveNueva = claveNueva;
    }

    public String getClaveVieja() {
        return claveVieja;
    }

    public void setClaveVieja(String claveVieja) {
        if(claveVieja != null)
            this.claveVieja = claveVieja;
    }

    public String getConfirmacionClave() {
        return confirmacionClave;
    }

    public void setConfirmacionClave(String confirmacionClave) {
        if(confirmacionClave != null)
            this.confirmacionClave = confirmacionClave;
    }

    public String getMensajeError(){
        return this.mensajeError;
    }

    public void setMensajeError(String mensajeError){
        this.mensajeError = mensajeError;
    }

    public String getMensajeExito(){
        return this.mensajeExito;
    }

    public void setMensajeExito(String mensajeExito){
        this.mensajeExito = mensajeExito;
    }

    public boolean isMostrarError() {
        return mostrarError;
    }

    public void setMostrarError(boolean mostrarError) {
        this.mostrarError = mostrarError;
    }

    public boolean isMostrarMensaje() {
        return mostrarMensaje;
    }

    public void setMostrarMensaje(boolean mostrarMensaje) {
        this.mostrarMensaje = mostrarMensaje;
    }

    public void cambiar() {
        if(claveVieja == null){
            mensajeError = "Debe introducir su contraseña actual";
            mostrarError = true;
            return;
        }
        if(claveNueva == null){
            mensajeError = "Debe introducir la contraseña nueva";
            mostrarError = true;
            return;
        }
        if(claveNueva.isEmpty()){
            mensajeError = "Introduzca una contraseña válida";
            mostrarError = true;
            return;
        }
        if(confirmacionClave == null){
            mensajeError = "Debe introducir la confirmación de su nueva contraseña";
            mostrarError = true;
            return;
        }
        if(claveNueva.compareTo(confirmacionClave) != 0){
            mensajeError = "La nueva contraseña y su confirmación no coinciden. Por favor ingrese nuevamente";
            mostrarError = true;
            return;
        }

        if(loginControlador.getUsuario().getPassword().compareTo(claveVieja) != 0){
            mensajeError = "Su contraseña actual no es correcta. Ingrese de nuevo";
            mostrarError = true;
            return;
        }
        loginControlador.getUsuario().setPassword(claveNueva);
        try{
            usuariosEJB.actualizar(loginControlador.getUsuario());
            resetearCampos();
            mensajeExito = "Se ha cambiado la clave para el Usuario " + loginControlador.getUsuario().getNombre();
            mostrarMensaje = true;
        }catch(Exception ex){
            logger.error("Error al cambiar clave para Usuario " + loginControlador.getUsuario().getNombre()+ ex);
            mensajeError = "Error al cambiar clave para Usuario ";
            mostrarError = true;
        }
    }

}
