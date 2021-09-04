
package py.com.metre.administracionBase.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.Usuario;

/**
 *
 * @author 
 */
@Stateless
public class UsuarioEJB {

    @PersistenceContext
    private EntityManager entidadManager;
    @Inject LoginControlador loginControlador;

    public void insertar(Usuario entidad) {
        entidadManager.persist(entidad);
    }

    public void actualizar(Usuario entidadNueva) {
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(Usuario entidad) {
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<Usuario> listarTodos() {
        return entidadManager.createNamedQuery("Usuario.findAll").getResultList();
    }
}
