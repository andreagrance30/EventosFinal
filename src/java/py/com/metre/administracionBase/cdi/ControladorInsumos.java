package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext; /*
import py.com.metre.administracionBase.ejb.InsumoEJB;
import py.com.metre.administracionBase.jpa.Insumo; */
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author Andrea
 */
@Named
@SessionScoped
public class ControladorInsumos implements Serializable {
/*
    Logger logger = Logger.getLogger(Insumo.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private InsumoEJB insumoEJB;
    private Insumo insumoSeleccionado;
    private Insumo insumoNuevo;
    private List<Insumo> listaInsumo;
    private
    @Inject
    LoginControlador loginControlador;

    public Insumo getInsumoNuevo() {
        if (insumoNuevo == null) {
            this.insumoNuevo = new Insumo();
        }
        return this.insumoNuevo;
    }

    public void setInsumoNuevo(Insumo insumo) {
        if (insumo != null) {
            this.insumoNuevo = insumo;
        }
    }

    public Insumo getInsumoSeleccionado() {
        if (insumoSeleccionado == null) {
            insumoSeleccionado = new Insumo();
            return insumoSeleccionado;
        }
        return insumoSeleccionado;
    }

    public void setInsumoSeleccionado(Insumo insumo) {
        if (insumo != null) {
            this.insumoSeleccionado = insumo;
        }
    }

    public List<Insumo> getListaInsumo() {
        listaInsumo = insumoEJB.listarTodos();
        return listaInsumo;
    }

    public void setListaInsumo(List<Insumo> listaInsumo) {
        this.listaInsumo = listaInsumo;
    }

    public void update() {
        try {
            if (this.insumoSeleccionado != null) {
                insumoEJB.actualizar(insumoSeleccionado);
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
            insumoEJB.insertar(insumoNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }

    public void delete() {
        try {
            if (this.insumoSeleccionado != null) {
                insumoEJB.eliminar(insumoSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        insumoSeleccionado = null;
        insumoNuevo = null;
    }  */
}
