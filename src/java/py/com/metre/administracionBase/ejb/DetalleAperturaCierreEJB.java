package py.com.metre.administracionBase.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.jpa.DetalleAperturaCierre;

/**
 *
 * 
 */
@Stateless
public class DetalleAperturaCierreEJB {

    @PersistenceContext
    private EntityManager entidadManager;

    private
    @Inject
    LoginControlador loginControlador;


    public void insertar(DetalleAperturaCierre entidad){
        entidadManager.persist(entidad);
    }

    public void actualizar(DetalleAperturaCierre entidadNueva){
        entidadManager.merge(entidadNueva);
    }

    public void eliminar(DetalleAperturaCierre entidad){
        entidadManager.remove(entidadManager.contains(entidad) ? entidad : entidadManager.merge(entidad));
    }

    public List<DetalleAperturaCierre> listarTodos(){
        Query query = entidadManager.createNativeQuery("SELECT * FROM detalle_apertura_cierre ORDER BY nombre", DetalleAperturaCierre.class);
        List listaApertCierrDet = new ArrayList<DetalleAperturaCierre>();
        listaApertCierrDet = (List<DetalleAperturaCierre>) query.getResultList();
        return listaApertCierrDet;
    }

    public DetalleAperturaCierre getInsumoPorId(String id){
        Query query = entidadManager.createNativeQuery("SELECT * FROM detalle_apertura_cierre WHERE ID = " + id, DetalleAperturaCierre.class);
        DetalleAperturaCierre apertCierrDet = (DetalleAperturaCierre) query.getSingleResult();
        return apertCierrDet;
    }

}
