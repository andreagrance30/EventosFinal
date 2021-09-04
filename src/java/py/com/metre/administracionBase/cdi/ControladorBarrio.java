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
import py.com.metre.administracionBase.ejb.BarrioEJB; /* Se importan las clases a ser utilizadas en el controlador*/
import py.com.metre.administracionBase.ejb.CiudadEJB; /* Desde cada controlador se puede acceder a todas las tablas de la base de datos mediante las clases correspondientes */
import py.com.metre.administracionBase.jpa.Barrio;
import py.com.metre.administracionBase.jpa.Ciudad;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author Andrea
 */
@Named
@SessionScoped
public class ControladorBarrio implements Serializable {

    Logger logger = Logger.getLogger(Barrio.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private BarrioEJB barrioEJB;
    private Barrio barrioSeleccionado;
    private Barrio barrioNuevo;
    private List<Barrio> listaBarrio;
    private Ciudad ciudadSeleccionado;
    private List<Ciudad> listaCiudad;
    
    @EJB
    private CiudadEJB CiudadEJB;
    
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
        if (barrio != null) {
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

     public List<Ciudad> getCiudad() {
        try {
            listaCiudad = CiudadEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Ciudad:" + ex.getMessage());
            return null;
        }
        return listaCiudad;
    }
       public Ciudad getCiudadSeleccionado() {
        return ciudadSeleccionado;
    }

    public void setCiudadSeleccionado(Ciudad ciudadSeleccionado) {
        if (ciudadSeleccionado != null) {
            this.ciudadSeleccionado = ciudadSeleccionado;
            barrioSeleccionado.setCiudadid(ciudadSeleccionado);
        }
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
     public void listar() {
            if (this.barrioSeleccionado != null) {
                barrioEJB.getBarrioPorId(barrioSeleccionado);
            }
    }

    public void add() {
        try {
            barrioNuevo.setCiudadid(ciudadSeleccionado);
            barrioEJB.insertar(barrioNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }
    private boolean validar() {
        if (ciudadSeleccionado == null || ciudadSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Ciudad", "Debe seleccionar un ciudad");
            return false;
        }
         return true;
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
        ciudadSeleccionado= null;
    }
}
