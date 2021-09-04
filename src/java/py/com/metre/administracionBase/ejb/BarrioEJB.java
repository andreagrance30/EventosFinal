package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.Barrio;

/**
 * 
 */
@Stateless
public class BarrioEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(Barrio entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(Barrio entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(Barrio entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<Barrio> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM barrio ORDER BY descripcion", Barrio.class);
        List listaBarrio = new ArrayList<Barrio>();
        listaBarrio = (List<Barrio>) query.getResultList();
        return listaBarrio;
    }
      
    public Barrio getBarrioPorId(Barrio id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM barrio WHERE ID = " + id, Barrio.class);
        Barrio barrio = (Barrio) query.getSingleResult();
        return barrio;
    }
}
