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
import py.com.metre.administracionBase.ejb.FormaCobroEJB;
import py.com.metre.administracionBase.jpa.FormaCobro;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorFormaCobro implements Serializable {

    Logger logger = Logger.getLogger(FormaCobro.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private FormaCobroEJB formaCobroEJB;
    private FormaCobro formaCobroSeleccionado;
    private FormaCobro formaCobroNuevo;
    private List<FormaCobro> listaFormaCobro;
    private Boolean deshabilitar;
    private
    @Inject
    LoginControlador loginControlador;

    public FormaCobro getFormaCobroNuevo() {
        if (formaCobroNuevo == null) {
            this.formaCobroNuevo = new FormaCobro();
        }
        return this.formaCobroNuevo;
    }

    public void setFormaCobroNuevo(FormaCobro formaCobro) {
        if (formaCobro != null) {
            this.formaCobroNuevo = formaCobro;
        }
    }

    public FormaCobro getFormaCobroSeleccionado() {
        if (formaCobroSeleccionado == null) {
            formaCobroSeleccionado = new FormaCobro();
            return formaCobroSeleccionado;
        }
        return formaCobroSeleccionado;
    }

    public void setFormaCobroSeleccionado(FormaCobro formaCobro) {
        if (formaCobroSeleccionado != null) {
            this.formaCobroSeleccionado = formaCobro;
        }
    }

    public List<FormaCobro> getListaFormaCobro() {
        listaFormaCobro = formaCobroEJB.listarTodos();
        return listaFormaCobro;
    }

    public void setListaFormaCobro(List<FormaCobro> listaFormaCobro) {
        this.listaFormaCobro = listaFormaCobro;
    }

    public void update() {
        try {
            if (this.formaCobroSeleccionado != null) {
                formaCobroEJB.actualizar(formaCobroSeleccionado);
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
            formaCobroEJB.insertar(formaCobroNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
             deshabilitar=true; 
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }

    public void delete() {
        try {
            if (this.formaCobroSeleccionado != null) {
                formaCobroEJB.eliminar(formaCobroSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        formaCobroSeleccionado = null;
        formaCobroNuevo = null;
    }

    public Boolean getDeshabilitar() {
        return deshabilitar;
    }

    public void setDeshabilitar(Boolean deshabilitar) {
         this.deshabilitar = deshabilitar; 
    }
}
