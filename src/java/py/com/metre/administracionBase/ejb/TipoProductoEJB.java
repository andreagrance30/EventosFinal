package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.TipoProducto;

/**
 *
 * 
 */
@Stateless
public class TipoProductoEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(TipoProducto entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(TipoProducto entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(TipoProducto entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<TipoProducto> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM tipo_producto ORDER BY descripcion", TipoProducto.class);
        List listaTipoProducto = new ArrayList<TipoProducto>();
        listaTipoProducto = (List<TipoProducto>) query.getResultList();
        return listaTipoProducto;
    }

    public TipoProducto getInsumoPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM tipo_producto WHERE ID = " + id, TipoProducto.class);
        TipoProducto tipoProducto = (TipoProducto) query.getSingleResult();
        return tipoProducto;
    }

}
