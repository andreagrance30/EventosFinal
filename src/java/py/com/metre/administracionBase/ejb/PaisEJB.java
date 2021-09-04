package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.Pais;

/**
 * 
 */
@Stateless
public class PaisEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(Pais entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(Pais entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(Pais entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<Pais> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM pais ORDER BY descripcion", Pais.class);
        List listaPaises = new ArrayList<Pais>();
        listaPaises = (List<Pais>) query.getResultList();
        return listaPaises;
    }
      
    public Pais getCiudadPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM pais WHERE ID = " + id, Pais.class);
        Pais pais = (Pais) query.getSingleResult();
        return pais;
    }
}
