package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.Sucursal;

/**
 * 
 */
@Stateless
public class SucursalEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(Sucursal entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(Sucursal entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(Sucursal entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<Sucursal> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM sucursal ORDER BY descripcion", Sucursal.class);
        List listaSucursal = new ArrayList<Sucursal>();
        listaSucursal = (List<Sucursal>) query.getResultList();
        return listaSucursal;
    }
      
    public Sucursal getSucursalPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM sucursal WHERE ID = " + id, Sucursal.class);
        Sucursal sucursal = (Sucursal) query.getSingleResult();
        return sucursal;
    }

}
