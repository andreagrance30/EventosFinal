package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.DetalleCobros;

/**
 *
 * 
 */
@Stateless
public class DetalleCobrosEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(DetalleCobros entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(DetalleCobros entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(DetalleCobros entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<DetalleCobros> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM detalle_cobros ORDER BY id", DetalleCobros.class);
        List listaDetalleCobros = new ArrayList<DetalleCobros>();
        listaDetalleCobros = (List<DetalleCobros>) query.getResultList();
        return listaDetalleCobros;
    }

    public DetalleCobros getInsumoPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM detalle_cobros  WHERE ID = " + id, DetalleCobros.class);
        DetalleCobros cobroDetalle = (DetalleCobros) query.getSingleResult();
        return cobroDetalle;
    }
    
}
