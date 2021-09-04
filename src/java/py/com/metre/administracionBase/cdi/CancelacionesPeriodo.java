package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.primefaces.event.DateSelectEvent;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class CancelacionesPeriodo implements Serializable {

    Logger logger = Logger.getLogger(CancelacionesPeriodo.class);
    @PersistenceContext
    protected EntityManager em;
    private
    @Inject
    LoginControlador loginControlador;

    public class Cancelacion {

        private String nombre;
        private String cantidad;

        public String getCantidad() {
            return cantidad;
        }

        public void setCantidad(String cantidad) {
            this.cantidad = cantidad;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    };
    private List<Cancelacion> detalleCancelaciones;
    private Date fechaInicial;
    private Date fechaFinal;

    public CancelacionesPeriodo() {
    }

    public void actualizarResultado() {
    }

    public void setPeriodoSemana() {
        Calendar hoy = Calendar.getInstance();
        Calendar fechaIni = Calendar.getInstance();
        // 604800000 = Cantidad de milisegundos en una semana
        fechaIni.setTimeInMillis(hoy.getTimeInMillis() - 604800000);
        setFechaInicial(fechaIni.getTime());
        setFechaFinal(hoy.getTime());
    }

    public void setPeriodoMes() {
        Calendar fechaIni = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        fechaIni.set(Calendar.DAY_OF_MONTH, fechaIni.getActualMinimum(Calendar.DAY_OF_MONTH));
        fechaFin.set(Calendar.DAY_OF_MONTH, fechaIni.getActualMaximum(Calendar.DAY_OF_MONTH));
        setFechaInicial(fechaIni.getTime());
        setFechaFinal(fechaFin.getTime());
    }

    public void setPeriodoAnho() {
        Calendar fechaIni = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        fechaIni.set(Calendar.MONTH, fechaIni.getActualMinimum(Calendar.MONTH));
        fechaIni.set(Calendar.DAY_OF_MONTH, fechaIni.getActualMinimum(Calendar.DAY_OF_MONTH));
        fechaFin.set(Calendar.MONTH, fechaIni.getActualMaximum(Calendar.MONTH));
        fechaFin.set(Calendar.DAY_OF_MONTH, fechaIni.getActualMaximum(Calendar.DAY_OF_MONTH));
        setFechaInicial(fechaIni.getTime());
        setFechaFinal(fechaFin.getTime());
    }

    public List<Cancelacion> getDetalleCancelaciones() {
        detalleCancelaciones = new ArrayList<Cancelacion>();
        try {
            Query q = em.createNativeQuery(consultaDetalle());
            List<Object[]> resultado = q.getResultList();
            for (Object[] registro : resultado) {
                Cancelacion item = new Cancelacion();
                item.setNombre(registro[0].toString());
                item.setCantidad(registro[1].toString());
                detalleCancelaciones.add(item);
            }
        } catch (Exception ex) {
            System.out.println("Detalle Periodo:" + ex.getMessage());
            return null;
        }
        return detalleCancelaciones;
    }

    public String getCancelacionesPeriodo() {
        String result = "0";
        try {
            Query q = em.createNativeQuery(consultaTotal());
            result = q.getSingleResult().toString();
            em.clear();
            em.close();
        } catch (Exception ex) {
            System.out.println("Cantidad de cancelaciones del período: " + ex.getMessage());
        }
        return result;
    }

    private String consultaTotal() {
        String idPedidoInicial = getIdPedidoInicial();
        String idPedidoFinal = getIdPedidoFinal();
        String query = "SELECT count (dp.id_detalle) as CANTIDAD "
                + "FROM item i, detalle_pedido dp, pedido p "
                + "WHERE dp.id_item = i.id_item "
                + "AND dp.cancelado = TRUE "
                + "AND dp.id_pedido = p.id_pedido ";
        if (!idPedidoInicial.isEmpty()) {
            query += " AND dp.id_pedido >= " + idPedidoInicial;
        }
        if (!idPedidoFinal.isEmpty()) {
            query += " AND dp.id_pedido <= " + idPedidoFinal;
        }
        return query;
    }

    private String consultaDetalle() {
        String idPedidoInicial = getIdPedidoInicial();
        String idPedidoFinal = getIdPedidoFinal();
        String query = "SELECT i.nombre AS ITEM, count (i.id_item) as CANTIDAD "
                + "FROM item i, detalle_pedido dp, pedido p "
                + "WHERE dp.id_item = i.id_item "
                + "AND dp.cancelado = TRUE "
                + "AND dp.id_pedido = p.id_pedido ";
        if (!idPedidoInicial.isEmpty()) {
            query += " AND dp.id_pedido >= " + idPedidoInicial;
        }
        if (!idPedidoFinal.isEmpty()) {
            query += " AND dp.id_pedido <= " + idPedidoFinal;
        }
        query += " GROUP by i.nombre "
                + "ORDER BY CANTIDAD DESC";
        return query;
    }

    /*
     * Tomamos el id del primer pedido de la fecha de inicio
     */
    private String getIdPedidoInicial(){
        String result = "";           
        BigInteger primerIdPM, primerIdAM;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 86400000 milisegundos = 1 dia
        long tiempoDia = getFechaInicial().getTime() + 86400000;
        Date fechaSiguiente = new Date();
        fechaSiguiente.setTime(tiempoDia);
	try {
            Query queryPM = em.createNativeQuery("SELECT MIN(id_pedido) FROM pedido WHERE fecha >= '" + sdf.format(getFechaInicial()) + "' AND hora > '10:00'");
            Object resultadoPM = queryPM.getSingleResult();
            em.clear();
            Query queryAM = em.createNativeQuery("SELECT MIN(id_pedido) FROM pedido WHERE fecha >= '" + sdf.format(fechaSiguiente) + "' ");
            Object resultadoAM = queryAM.getSingleResult();
            em.clear();
            if(resultadoAM != null && resultadoPM != null){
                primerIdAM = new BigInteger(resultadoAM.toString());
                primerIdPM = new BigInteger(resultadoPM.toString());
                if(primerIdAM.compareTo(primerIdPM) < 0){
                    result = primerIdAM.toString();
                } else {
                    result = primerIdPM.toString();
                }
            }
            if(resultadoAM == null && resultadoPM != null){
                result = resultadoPM.toString();
            }
            if(resultadoPM == null && resultadoAM != null){
                result = resultadoAM.toString();
            }
            if(resultadoAM == null && resultadoPM == null){
                Query max = em.createNativeQuery("SELECT MAX(id_pedido) FROM pedido");
                BigDecimal id = (BigDecimal) max.getSingleResult();
                id = id.add(BigDecimal.ONE);
                result = id.toString();
            }
        } catch (Exception ex) {
            System.out.println("Calculo Fecha Inicio: " + ex.getMessage());
        }
        return result;
    }

    /*
     * Tomamos el id del ultimo pedido de la fecha de fin
     */
    private String getIdPedidoFinal(){
        String result = "";
        BigInteger ultimoIdPM, ultimoIdAM;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 86400000 milisegundos en un dia
        long tiempoDia = getFechaFinal().getTime() + 86400000;
        Date fechaSiguiente = new Date();
        fechaSiguiente.setTime(tiempoDia);

        try {
            Query queryAM = em.createNativeQuery("SELECT MAX(id_pedido) FROM pedido WHERE fecha <= '" + sdf.format(fechaSiguiente) + "' AND hora < '10:00'");
            Object resultadoAM = queryAM.getSingleResult();
            em.clear();
            Query queryPM = em.createNativeQuery("SELECT MAX(id_pedido) FROM pedido WHERE fecha <= '" + sdf.format(getFechaFinal()) + "' ");
            Object resultadoPM = queryPM.getSingleResult();
            em.clear();
            if(resultadoAM != null && resultadoPM != null){
                ultimoIdAM = new BigInteger(resultadoAM.toString());
                ultimoIdPM = new BigInteger(resultadoPM.toString());
                if(ultimoIdAM.compareTo(ultimoIdPM) > 0){
                    result = ultimoIdAM.toString();
                } else {
                    result = ultimoIdPM.toString();
                }
            }
            if(resultadoAM == null && resultadoPM != null){
                result = resultadoPM.toString();
            }
            if(resultadoPM == null && resultadoAM != null){
                result = resultadoAM.toString();
            }
            if(resultadoAM == null && resultadoPM == null){
                result = "0";
            }
        } catch (Exception ex) {
            System.out.println("Calculo Fecha Fin: " + ex.getMessage());
        } finally {
            return result;
        }
    }

    public Date getFechaFinal() {
        if (fechaFinal == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), 11, 31);
            fechaFinal = cal.getTime();
        }
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        if (fechaFinal != null) {
            this.fechaFinal = fechaFinal;
        }
    }

    public Date getFechaInicial() {
        if (fechaInicial == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), 0, 1);
            fechaInicial = cal.getTime();
        }
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        if (fechaInicial != null) {
            this.fechaInicial = fechaInicial;
        }
    }

    public void actualizarFechaInicio(DateSelectEvent event) {
        fechaInicial = event.getDate();
    }

    public void actualizarFechaFin(DateSelectEvent event) {
        fechaFinal = event.getDate();
    }
}
