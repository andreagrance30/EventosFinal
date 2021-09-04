package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.DetallePresupuesto;

/**
 * 
 */
@Stateless
public class DetallePresupuestoEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(DetallePresupuesto entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(DetallePresupuesto entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(DetallePresupuesto entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<DetallePresupuesto> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM detalle_presupuesto", DetallePresupuesto.class);
        List listaDetallePresupuesto = new ArrayList<DetallePresupuesto>();
        listaDetallePresupuesto = (List<DetallePresupuesto>) query.getResultList();
        return listaDetallePresupuesto;
    }
      
    public DetallePresupuesto getDetallePresupuestoPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM detalle_presupuesto WHERE ID = " + id, DetallePresupuesto.class);
        DetallePresupuesto detallePresupuesto = (DetallePresupuesto) query.getSingleResult();
        return detallePresupuesto;
    }
}
