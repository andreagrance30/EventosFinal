package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.Receta;

/**
 *
 * 
 */
@Stateless
public class RecetaEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(Receta entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(Receta entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(Receta entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<Receta> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM receta ORDER BY ID", Receta.class);
        List listaReceta = new ArrayList<Receta>();
        listaReceta = (List<Receta>) query.getResultList();
        return listaReceta;
    }
}
