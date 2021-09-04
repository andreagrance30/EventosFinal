package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.PerfilUsuario;

/**
 *
 * 
 */
@Stateless
public class PerfilUsuarioEJB {

    @PersistenceContext
    private EntityManager entidadManager;
    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(PerfilUsuario entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(PerfilUsuario entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(PerfilUsuario entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<PerfilUsuario> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM perfil_usuario ORDER BY descripcion", PerfilUsuario.class);
        List listaPerfilUsuario = new ArrayList<PerfilUsuario>();
        listaPerfilUsuario = (List<PerfilUsuario>) query.getResultList();
        return listaPerfilUsuario;
    }

    public PerfilUsuario getInsumoPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM perfil_usuario  WHERE ID = " + id, PerfilUsuario.class);
        PerfilUsuario perfilUsuario = (PerfilUsuario) query.getSingleResult();
        return perfilUsuario;
    }
}
