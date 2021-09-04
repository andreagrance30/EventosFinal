package py.com.metre.administracionBase.cdi;

/**
 *
 * @author
 */
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import py.com.metre.administracionBase.ejb.LoginEJB;
import py.com.metre.administracionBase.jpa.Usuario;
import py.com.metre.administracionBase.jsf.JsfUtil;
import py.com.metre.administracionBase.util.ApplicationConstant;

@Named
@SessionScoped
public class LoginControlador implements Serializable {

    Logger logger = Logger.getLogger(LoginControlador.class);
    private Usuario usuario;
    private String username = "";
    private String password = "";
    @EJB
    LoginEJB loginEJB;
    private String menus = "";

    public LoginControlador() {
    }

    /**
     * @return the menus
     */
    public String getMenus() throws SQLException {
        System.out.println("este es el menu:" + menus);
        return menus;
    }

    public boolean getLogueado() {
        return getUsuario() != null;
    }

    public String login() {
        ExternalContext ctx =
                FacesContext.getCurrentInstance().getExternalContext();
        HttpSession hSession = null;

        try {
            this.setUsuario(loginEJB.login(username));

            if (getUsuario() != null) // estaba usuario....
            {
                if (usuario.getPassword() != null && password != null) {
                    if (usuario.getPassword().compareTo(password) != 0) {
                        return loginFail();
                    }
                } else {
                    return loginFail();
                }
                /*
                 * Se carga el usuario logueado en memoria asociando con su
                 * ID Session
                 */
                hSession = (HttpSession) ctx.getSession(false);
                hSession.setAttribute(ApplicationConstant.USERNAME, getUsuario().getNombre());
                //return "inicio";
                return "inicioAdministracionBase";
            } else {
                return loginFail();
            }
        } catch (Exception ex) {
            logger.error("Error al intentar ingresar a la aplicacion", ex);
            return loginFail();
        }
    }

    private String loginFail() {
        this.username = "";
        JsfUtil.agregarMensajeErrorCampo("Usuario/Password", "Usuario o password incorrectos");
        return "login";
    }

    public String logout() {
        ExternalContext ctx =
                FacesContext.getCurrentInstance().getExternalContext();

        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
        try {
            ((HttpSession) ctx.getSession(false)).invalidate();


            ctx.redirect(ctxPath + "/faces/administracionBase/login.xhtml");
        } catch (IOException ex) {
            logger.error("Error cerrando la sesion", ex);
        } finally {
            setUsuario(null);
            return "/index";
        }
    }

    public String getImagen() {
        return getPathHome()+"faces/recursos/img/logo.png";
    }

    public String getPathHome() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":" + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/";
    }

    public String getUserNombre(){
        return usuario.getNombre();
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
