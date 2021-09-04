package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.Presupuesto;

/**
 * 
 */
@Stateless
public class PresupuestoEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(Presupuesto entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(Presupuesto entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(Presupuesto entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<Presupuesto> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM presupuesto ORDER BY descripcion", Presupuesto.class);
        List listaPresupuesto = new ArrayList<Presupuesto>();
        listaPresupuesto = (List<Presupuesto>) query.getResultList();
        return listaPresupuesto;
    }
      
    public Presupuesto getPresupuestoPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM presupuesto WHERE ID = " + id, Presupuesto.class);
        Presupuesto presupuesto = (Presupuesto) query.getSingleResult();
        return presupuesto;
    }

}
