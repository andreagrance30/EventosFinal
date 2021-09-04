package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.Cliente;

/** 
 */
@Stateless
public class ClienteEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(Cliente entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(Cliente entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(Cliente entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<Cliente> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM cliente ORDER BY nombre", Cliente.class);
        List listaCliente = new ArrayList<Cliente>();
        listaCliente = (List<Cliente>) query.getResultList();
        return listaCliente;
    }

    public Cliente getClientePorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM cliente WHERE ID = " + id, Cliente.class);
        Cliente cliente = (Cliente) query.getSingleResult();
        return cliente;
    }
}
