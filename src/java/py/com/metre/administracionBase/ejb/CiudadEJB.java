package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.Ciudad;

/**
 * 
 */
@Stateless
public class CiudadEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(Ciudad entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(Ciudad entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(Ciudad entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<Ciudad> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM ciudad ORDER BY descripcion", Ciudad.class);
        List listaCiudad = new ArrayList<Ciudad>();
        listaCiudad = (List<Ciudad>) query.getResultList();
        return listaCiudad;
    }
      
    public Ciudad getCiudadPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM ciudad WHERE ID = " + id, Ciudad.class);
        Ciudad ciudad = (Ciudad) query.getSingleResult();
        return ciudad;
    }

}
