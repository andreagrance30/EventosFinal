package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
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
import py.com.metre.administracionBase.util.ImprimirReporte;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class FacturacionPeriodo implements Serializable {

    private ImprimirReporte imprimirreporte;
    Logger logger = Logger.getLogger(FacturacionPeriodo.class);
    @PersistenceContext
    protected EntityManager em;

    private
    @Inject
    LoginControlador loginControlador;

    private Date fechaInicial;
    private Date fechaFinal;
    
    public FacturacionPeriodo(){
        imprimirreporte = new ImprimirReporte();
    }

    public void actualizarResultado(){
        
    }

    public void generarPDF(){
        String condicion = "";
        String idPedidoInicial = getIdPedidoInicial();
        String idPedidoFinal = getIdPedidoFinal();
        if(!idPedidoInicial.isEmpty()){
            condicion += " AND dp.id_pedido >= " + idPedidoInicial;
            
        }
        if(!idPedidoFinal.isEmpty()){
            condicion += " AND dp.id_pedido <= " + idPedidoFinal; //////
        }

        /* En este caso, se consultaron pedidos de fechas sin actividad, entonces debe resultar vacío
         * Esto es un hardcode temporal, contradigo la última condición para vaciar el resultado.
         * Mejorar.
         */
        if(idPedidoInicial.isEmpty() && idPedidoFinal.isEmpty()){
            condicion += " AND dp.cancelado = TRUE ";
        }
        // Llamar al generador del reporte
        Map<String, Object> parametros = new HashMap<String, Object>();
        //imprimirreporte.setNombreReporte("Facturacion");
        condicion = "t.creado between " + fechaInicialQuery() + " and " + fechaFinalQuery() + " ";
        System.out.println("CONDICION: " + condicion);
        imprimirreporte.setNombreReporte("Transacciones");
        parametros.put("condicion", condicion);
        parametros.put("desde", this.getFechaInicial());
        parametros.put("hasta", this.getFechaFinal());
        parametros.put("impresoPor", loginControlador.getUsuario().getNombre());
        imprimirreporte.setParametros(parametros);
        imprimirreporte.imprimir();
    }

    private String fechaInicialQuery(){
        Calendar fechaIni = Calendar.getInstance();
        fechaIni.setTime(this.getFechaInicial());
        fechaIni.set(Calendar.HOUR_OF_DAY, 12);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = "'" + sdf.format(fechaIni.getTime()) + "'";
        return result;
    }

    private String fechaFinalQuery(){
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.setTime(this.getFechaFinal());
        fechaFin.add(Calendar.DAY_OF_MONTH, 1);
        fechaFin.set(Calendar.HOUR_OF_DAY, 12);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = "'" + sdf.format(fechaFin.getTime()) + "'";
        return result;
    }

    public void setPeriodoSemana(){
        Calendar hoy = Calendar.getInstance();
        Calendar fechaIni = Calendar.getInstance();
        // 604800000 = Cantidad de milisegundos en una semana
        fechaIni.setTimeInMillis(hoy.getTimeInMillis() - 604800000);
        setFechaInicial(fechaIni.getTime());
        setFechaFinal(hoy.getTime());
    }

    public void setPeriodoMes(){
        Calendar fechaIni = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        fechaIni.set(Calendar.DAY_OF_MONTH, fechaIni.getActualMinimum(Calendar.DAY_OF_MONTH));
        fechaFin.set(Calendar.DAY_OF_MONTH, fechaIni.getActualMaximum(Calendar.DAY_OF_MONTH));
        setFechaInicial(fechaIni.getTime());
        setFechaFinal(fechaFin.getTime());
    }

    public void setPeriodoAnho(){
        Calendar fechaIni = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        fechaIni.set(Calendar.MONTH, fechaIni.getActualMinimum(Calendar.MONTH));
        fechaIni.set(Calendar.DAY_OF_MONTH, fechaIni.getActualMinimum(Calendar.DAY_OF_MONTH));
        fechaFin.set(Calendar.MONTH, fechaIni.getActualMaximum(Calendar.MONTH));
        fechaFin.set(Calendar.DAY_OF_MONTH, fechaIni.getActualMaximum(Calendar.DAY_OF_MONTH));
        setFechaInicial(fechaIni.getTime());
        setFechaFinal(fechaFin.getTime());
    }

    public String getFacturacionPeriodo() {
        String result = "0";
        try {
            Query q = em.createNativeQuery(consultaTotal());
            result = q.getSingleResult().toString();
            em.clear();
            em.close();
        } catch (Exception ex) {
            System.out.println("Total Periodo: " + ex.getMessage());
        }
        return result;
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
                BigInteger aux = new BigInteger(max.getSingleResult().toString());
                aux = aux.add(BigInteger.ONE);
                result = aux.toString();
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

    private String consultaTotal(){
        String idPedidoInicial = getIdPedidoInicial();
        String idPedidoFinal = getIdPedidoFinal();
        System.out.println("ID INICIAL: " + idPedidoInicial);
        System.out.println("ID FINAL: " + idPedidoFinal);
        String query = "SELECT sum (dp.precio) AS TOTAL " +
        "FROM detalle_factura df, detalle_pedido dp, factura f " +
        "WHERE f.id_factura = df.id_factura " +
        "AND df.id_detalle_pedido = dp.id_detalle " +
        "AND dp.cancelado = FALSE ";
        if(!idPedidoInicial.isEmpty()){
            query += " AND dp.id_pedido >= " + idPedidoInicial;
        }
        if(!idPedidoFinal.isEmpty()){
            query += " AND dp.id_pedido <= " + idPedidoFinal;
        }
        // En este caso, se consultaron pedidos de fechas sin actividad, entonces debe resultar cero
        System.out.println("QUERY CONSULTA TOTAL: " + query);
        if(idPedidoInicial.isEmpty() && idPedidoFinal.isEmpty()){
            return "SELECT 0";
        }
        return query;
    }

    public Date getFechaFinal() {
        if(fechaFinal == null){
            fechaFinal = new Date();
        }
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        if(fechaFinal != null){
            this.fechaFinal = fechaFinal;
        }
    }

    public Date getFechaInicial() {
        if(fechaInicial == null){
            fechaInicial = new Date();
        }
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        if(fechaInicial != null){
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