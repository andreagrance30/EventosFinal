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
import py.com.metre.administracionBase.ejb.CiudadEJB;
import py.com.metre.administracionBase.jpa.Ciudad;
import py.com.metre.administracionBase.jsf.JsfUtil;


/**
 *
 * @author 
 */
@Named
@SessionScoped
public class InventarioCiudad implements Serializable {

    Logger logger = Logger.getLogger(Ciudad.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private CiudadEJB ciudadEJB;
    private Ciudad ciudadSeleccionado;
    private Ciudad ciudadNuevo;
    private List<Ciudad> listaCiudad;
    private
    @Inject
    LoginControlador loginControlador;

    public Ciudad getCiudadNuevo() {
        if (ciudadNuevo == null) {
            this.ciudadNuevo = new Ciudad();
        }
        return this.ciudadNuevo;
    }

    public void setCiudadNuevo(Ciudad ciudad) {
        if (ciudad != null) {
            this.ciudadNuevo = ciudad;
        }
    }

    public Ciudad getCiudadSeleccionado() {
        if (ciudadSeleccionado == null) {
            ciudadSeleccionado = new Ciudad();
            return ciudadSeleccionado;
        }
        return ciudadSeleccionado;
    }

    public void setCiudadSeleccionado(Ciudad ciudad) {
        if (ciudadSeleccionado != null) {
            this.ciudadSeleccionado = ciudad;
        }
    }

    public List<Ciudad> getListaCiudad() {
        listaCiudad = ciudadEJB.listarTodos();
        return listaCiudad;
    }

    public void setListaCiudad(List<Ciudad> listaCiudad) {
        this.listaCiudad = listaCiudad;
    }

    public void update() {
        try {
            if (this.ciudadSeleccionado != null) {
                ciudadEJB.actualizar(ciudadSeleccionado);
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
            ciudadEJB.insertar(ciudadNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }

    public void delete() {
        try {
            if (this.ciudadSeleccionado != null) {
                ciudadEJB.eliminar(ciudadSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        ciudadSeleccionado = null;
        ciudadNuevo = null;
    }
}