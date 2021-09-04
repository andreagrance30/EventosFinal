package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import py.com.metre.administracionBase.ejb.BarrioEJB;
import py.com.metre.administracionBase.jpa.Barrio;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author Adolfo
 */
@Named
@SessionScoped
public class InventarioBarrio implements Serializable {

    Logger logger = Logger.getLogger(Barrio.class);

    @EJB
    private BarrioEJB barrioEJB;
    private Barrio barrioSeleccionado;
    private Barrio barrioNuevo;
    private List<Barrio> listaBarrio;
    private
    @Inject
    LoginControlador loginControlador;

    public Barrio getBarrioNuevo() {
        if (barrioNuevo == null) {
            this.barrioNuevo = new Barrio();
        }
        return this.barrioNuevo;
    }

    public void setBarrioNuevo(Barrio barrio) {
        if (barrio != null) {
            this.barrioNuevo = barrio;
        }
    }

    public Barrio getBarrioSeleccionado() {
        if (barrioSeleccionado == null) {
            barrioSeleccionado = new Barrio();
            return barrioSeleccionado;
        }
        return barrioSeleccionado;
    }

    public void setBarrioSeleccionado(Barrio barrio) {
        if (barrioSeleccionado != null) {
            this.barrioSeleccionado = barrio;
        }
    }

    public List<Barrio> getListaBarrio() {
        listaBarrio = barrioEJB.listarTodos();
        return listaBarrio;
    }

    public void setListaBarrio(List<Barrio> listaBarrio) {
        this.listaBarrio = listaBarrio;
    }

    public void update() {
        try {
            if (this.barrioSeleccionado != null) {
                barrioEJB.actualizar(barrioSeleccionado);
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
            barrioEJB.insertar(barrioNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }

    public void delete() {
        try {
            if (this.barrioSeleccionado != null) {
                barrioEJB.eliminar(barrioSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        barrioSeleccionado = null;
        barrioNuevo = null;
    }

}
