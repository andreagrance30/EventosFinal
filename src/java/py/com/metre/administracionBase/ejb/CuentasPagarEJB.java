package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.CuentasPagar;

/**
 * 
 */
@Stateless
public class CuentasPagarEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(CuentasPagar entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(CuentasPagar entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(CuentasPagar entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<CuentasPagar> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM cuentas_pagar ", CuentasPagar.class);
        List listaCuentasPagar = new ArrayList<CuentasPagar>();
        listaCuentasPagar = (List<CuentasPagar>) query.getResultList();
        return listaCuentasPagar;
    }
      
    public CuentasPagar getCuentasPagarPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM cuentas_pagar WHERE ID = " + id, CuentasPagar.class);
        CuentasPagar cuentas = (CuentasPagar) query.getSingleResult();
        return cuentas;
    }

}
