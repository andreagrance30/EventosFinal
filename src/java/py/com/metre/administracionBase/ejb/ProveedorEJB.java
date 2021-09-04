package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.Proveedor;

/**
 *
 * 
 */
@Stateless
public class ProveedorEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(Proveedor entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(Proveedor entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(Proveedor entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<Proveedor> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM proveedor ORDER BY nombre", Proveedor.class);
        List listaProveedor = new ArrayList<Proveedor>();
        listaProveedor = (List<Proveedor>) query.getResultList();
        return listaProveedor;
    }

    public Proveedor getInsumoPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM proveedor WHERE ID = " + id, Proveedor.class);
        Proveedor proveedor = (Proveedor) query.getSingleResult();
        return proveedor;
    }

}
