package py.com.metre.administracionBase.ejb;


import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.FormaPago;

/**
 * 
 */
@Stateless
public class FormaPagoEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(FormaPago entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(FormaPago entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(FormaPago entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<FormaPago> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM forma_pago ORDER BY descripcion", FormaPago.class);
        List listaFormaPago = new ArrayList<FormaPago>();
        listaFormaPago = (List<FormaPago>) query.getResultList();
        return listaFormaPago;
    }
      
    public FormaPago getFormaPagoPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM forma_pago WHERE ID = " + id, FormaPago.class);
        FormaPago formaPa = (FormaPago) query.getSingleResult();
        return formaPa;
    }
}
