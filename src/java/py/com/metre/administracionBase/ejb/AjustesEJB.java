package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.Ajustes;

/**
 * 
 */
@Stateless
public class AjustesEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(Ajustes entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(Ajustes entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(Ajustes entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<Ajustes> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM ajustes ORDER BY descripcion", Ajustes.class);
        List listaAjustes = new ArrayList<Ajustes>();
        listaAjustes = (List<Ajustes>) query.getResultList();
        return listaAjustes;
    }
      
    public Ajustes getAjustesPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM ciudad WHERE ID = " + id, Ajustes.class);
        Ajustes ciudad = (Ajustes) query.getSingleResult();
        return ciudad;
    }
}
