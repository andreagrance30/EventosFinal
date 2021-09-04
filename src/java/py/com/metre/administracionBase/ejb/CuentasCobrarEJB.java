package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.CuentasCobrar;

/**
 * 
 */
@Stateless
public class CuentasCobrarEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(CuentasCobrar entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(CuentasCobrar entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(CuentasCobrar entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<CuentasCobrar> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM cuentas_cobrar ORDER BY descripcion", CuentasCobrar.class);
        List listaCuentasCobrar = new ArrayList<CuentasCobrar>();
        listaCuentasCobrar = (List<CuentasCobrar>) query.getResultList();
        return listaCuentasCobrar;
    }
      
    public CuentasCobrar getCuentasCobrarPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM cuentas_cobrar WHERE ID = " + id, CuentasCobrar.class);
        CuentasCobrar ciudad = (CuentasCobrar) query.getSingleResult();
        return ciudad;
    }

}
