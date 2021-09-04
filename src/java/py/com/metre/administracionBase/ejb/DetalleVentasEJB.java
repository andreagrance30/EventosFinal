package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.DetalleVentas;

/**
 * 
 */
@Stateless
public class DetalleVentasEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(DetalleVentas entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(DetalleVentas entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(DetalleVentas entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<DetalleVentas> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM detalle_ventas ORDER BY ID", DetalleVentas.class);
        List listaDetalleVentas = new ArrayList<DetalleVentas>();
        listaDetalleVentas = (List<DetalleVentas>) query.getResultList();
        return listaDetalleVentas;
    }

}
