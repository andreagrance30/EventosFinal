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
import py.com.metre.administracionBase.ejb.PaisEJB;
import py.com.metre.administracionBase.jpa.Pais;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @
 */
@Named
@SessionScoped
public class InventarioPais implements Serializable {

    Logger logger = Logger.getLogger(Pais.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private PaisEJB paisEJB;
    private Pais paisSeleccionado;
    private Pais paisNuevo;
    private List<Pais> listaPais;
    private
    @Inject
    LoginControlador loginControlador;

    public Pais getPaisNuevo() {
        if (paisNuevo == null) {
            this.paisNuevo = new Pais();
        }
        return this.paisNuevo;
    }

    public void setPaisNuevo(Pais pais) {
        if (pais != null) {
            this.paisNuevo = pais;
        }
    }

    public Pais getPaisSeleccionado() {
        if (paisSeleccionado == null) {
            paisSeleccionado = new Pais();
            return paisSeleccionado;
        }
        return paisSeleccionado;
    }

    public void setPaisSeleccionado(Pais pais) {
        if (paisSeleccionado != null) {
            this.paisSeleccionado = pais;
        }
    }

    public List<Pais> getListaPais() {
        listaPais = paisEJB.listarTodos();
        return listaPais;
    }

    public void setListaPais(List<Pais> listaPais) {
        this.listaPais = listaPais;
    }

    public void update() {
        try {
            if (this.paisSeleccionado != null) {
                paisEJB.actualizar(paisSeleccionado);
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
            paisEJB.insertar(paisNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }

    public void delete() {
        try {
            if (this.paisSeleccionado != null) {
                paisEJB.eliminar(paisSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        paisSeleccionado = null;
        paisNuevo = null;
    }
}

    