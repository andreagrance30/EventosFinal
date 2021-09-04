/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.metre.administracionBase.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author inv
 *
 *  Clase de utilerias para JSF
 *
 * Maneja mensajes de error y genera un vector de SelectItem
 * a partir de una lista
 */
public class JsfUtil {

     public static void agregarMensajeErrorGlobal(Exception ex, String titulo) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,titulo, ex.getLocalizedMessage());
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void agregarMensajeErrorCampo(String campo,String titulo) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo,null);
        FacesContext.getCurrentInstance().addMessage(campo, facesMsg);
    }

    public static void agregarMensajeExito(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void agregarMensaje(String msg, FacesMessage.Severity severity){
        FacesMessage facesMsg = new FacesMessage(severity, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    public static void agregarMensaje(String formaDePago, String debe_ingresar_el_numero_de_cuotas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void addErrorMessage(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
