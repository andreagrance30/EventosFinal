package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.UnidadDeMedida;

/**
 * 
 */
@Stateless
public class UnidadMedidaEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(UnidadDeMedida entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(UnidadDeMedida entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(UnidadDeMedida entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<UnidadDeMedida> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM unidad_de_medida ORDER BY descripcion", UnidadDeMedida.class);
        List listaUnidadDeMedida = new ArrayList<UnidadDeMedida>();
        listaUnidadDeMedida = (List<UnidadDeMedida>) query.getResultList();
        return listaUnidadDeMedida;
    }
      
    public UnidadDeMedida getUnidadDeMedidaPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM unidad_de_medida WHERE ID = " + id, UnidadDeMedida.class);
        UnidadDeMedida unidadMedida = (UnidadDeMedida) query.getSingleResult();
        return unidadMedida;
    }

}
