package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.Producto;

/**
 *
 * 
 */
@Stateless
public class ProductoEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(Producto entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(Producto entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(Producto entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<Producto> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM producto ORDER BY descripcion", Producto.class);
        List listaProducto = new ArrayList<Producto>();
        listaProducto = (List<Producto>) query.getResultList();
        return listaProducto;
    }

    public Producto getProductoPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM producto WHERE ID = " + id, Producto.class);
        Producto producto = (Producto) query.getSingleResult();
        return producto;
    }
}
