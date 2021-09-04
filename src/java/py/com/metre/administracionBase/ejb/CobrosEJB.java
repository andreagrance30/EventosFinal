package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.Cobros;

/**
 *
 * 
 */
@Stateless
public class CobrosEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(Cobros entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(Cobros entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(Cobros entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<Cobros> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM cobros ORDER BY nombre", Cobros.class);
        List listaCobros = new ArrayList<Cobros>();
        listaCobros = (List<Cobros>) query.getResultList();
        return listaCobros;
    }

    public Cobros getInsumoPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM cobros WHERE ID = " + id, Cobros.class);
        Cobros cobroCabecera = (Cobros) query.getSingleResult();
        return cobroCabecera;
    }
}
