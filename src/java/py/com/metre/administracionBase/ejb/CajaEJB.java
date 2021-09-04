package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.Caja;

/**
 * 
 */
@Stateless
public class CajaEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(Caja entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(Caja entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(Caja entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<Caja> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM caja ORDER BY descripcion", Caja.class);
        List listaCaja = new ArrayList<Caja>();
        listaCaja = (List<Caja>) query.getResultList();
        return listaCaja;
    }
      
    public Caja getCajaPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM caja WHERE ID = " + id, Caja.class);
        Caja caja = (Caja) query.getSingleResult();
        return caja;
    }

}
