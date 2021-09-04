/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.metre.administracionBase.ejb;

import java.util.Date;

public class Sesiones {
    String id;
    String usuario;
    Date fechainicio;
    Date ultimoacceso;

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getUltimoacceso() {
        return ultimoacceso;
    }

    public void setUltimoacceso(Date ultimoacceso) {
        this.ultimoacceso = ultimoacceso;
    }

}
