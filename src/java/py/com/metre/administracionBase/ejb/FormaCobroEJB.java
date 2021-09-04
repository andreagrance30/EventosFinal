package py.com.metre.administracionBase.ejb;


import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.FormaCobro;

/**
 * 
 */
@Stateless
public class FormaCobroEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(FormaCobro entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(FormaCobro entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(FormaCobro entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<FormaCobro> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM forma_cobro ORDER BY descripcion", FormaCobro.class);
        List listaFormaCobro = new ArrayList<FormaCobro>();
        listaFormaCobro = (List<FormaCobro>) query.getResultList();
        return listaFormaCobro;
    }
      
    public FormaCobro getFormaCobroPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM forma_cobro WHERE ID = " + id, FormaCobro.class);
        FormaCobro formaCo = (FormaCobro) query.getSingleResult();
        return formaCo;
    }
}
