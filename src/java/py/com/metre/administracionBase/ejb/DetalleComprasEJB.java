package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.DetalleCompras;

/**
 *
 * @author 
 */
@Stateless
public class DetalleComprasEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(DetalleCompras entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(DetalleCompras entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(DetalleCompras entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<DetalleCompras> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM detalle_compras ORDER BY ID", DetalleCompras.class);
        List listaDetalles = new ArrayList<DetalleCompras>();
        listaDetalles = (List<DetalleCompras>) query.getResultList();
        return listaDetalles;
    }
}
