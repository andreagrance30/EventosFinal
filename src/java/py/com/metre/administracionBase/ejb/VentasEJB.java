package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.Ventas;

/**
 * 
 */
@Stateless
public class VentasEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(Ventas entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(Ventas entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(Ventas entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<Ventas> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM ventas ORDER BY nro_fact", Ventas.class);
        List listaVentas = new ArrayList<Ventas>();
        listaVentas = (List<Ventas>) query.getResultList();
        return listaVentas;
    }
      
    public Ventas getVentasPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM ventas WHERE ID = " + id, Ventas.class);
        Ventas ventas = (Ventas) query.getSingleResult();
        return ventas;
    }

}
