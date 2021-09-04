package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.AperturaCierreCaja;

/**
 *
 * 
 */
@Stateless
public class AperturaCierreCajaEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(AperturaCierreCaja entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(AperturaCierreCaja entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(AperturaCierreCaja entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<AperturaCierreCaja> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM apertura_cierre_caja ORDER BY nombre", AperturaCierreCaja.class);
        List listaAperCierreCab = new ArrayList<AperturaCierreCaja>();
        listaAperCierreCab = (List<AperturaCierreCaja>) query.getResultList();
        return listaAperCierreCab;
    }

    public AperturaCierreCaja getInsumoPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM apertura_cierre_caja WHERE ID = " + id, AperturaCierreCaja.class);
        AperturaCierreCaja aperCierreCab = (AperturaCierreCaja) query.getSingleResult();
        return aperCierreCab;
    }
}
