package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.Compras;

/**
 *
 
 */
@Stateless
public class ComprasEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(Compras entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(Compras entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(Compras entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<Compras> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM compras ORDER BY ID", Compras.class);
        List listaCompras = new ArrayList<Compras>();
        listaCompras = (List<Compras>) query.getResultList();
        return listaCompras;
    }
}
