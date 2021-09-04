package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.ClasificacionProducto;

/**
 * 
 */
@Stateless
public class ClasiProductoEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(ClasificacionProducto entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(ClasificacionProducto entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(ClasificacionProducto entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<ClasificacionProducto> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM clasificacion_producto ORDER BY descripcion", ClasificacionProducto.class);
        List listaClasificacionProducto = new ArrayList<ClasificacionProducto>();
        listaClasificacionProducto = (List<ClasificacionProducto>) query.getResultList();
        return listaClasificacionProducto;
    }
      
    public ClasificacionProducto getClasificacionProductoPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM clasificacion_producto WHERE ID = " + id, ClasificacionProducto.class);
        ClasificacionProducto clasi = (ClasificacionProducto) query.getSingleResult();
        return clasi;
    }

}
