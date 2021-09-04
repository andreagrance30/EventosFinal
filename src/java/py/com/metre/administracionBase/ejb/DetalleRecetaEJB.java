package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.DetalleReceta;

/**
 * 
 */
@Stateless
public class DetalleRecetaEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(DetalleReceta entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(DetalleReceta entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(DetalleReceta entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<DetalleReceta> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM detalle_receta ORDER BY productoid", DetalleReceta.class);
        List listaDetalleReceta = new ArrayList<DetalleReceta>();
        listaDetalleReceta = (List<DetalleReceta>) query.getResultList();
        return listaDetalleReceta;
    }
      
    public DetalleReceta getDetalleRecetaPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM detalle_receta WHERE ID = " + id, DetalleReceta.class);
        DetalleReceta detalleReceta = (DetalleReceta) query.getSingleResult();
        return detalleReceta;
    }

}
