package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
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
 * @author Adolfo
 */
@Named
@SessionScoped
public class UtilizacionMesas implements Serializable {

    Logger logger = Logger.getLogger(UtilizacionMesas.class);
    @PersistenceContext
    protected EntityManager em;

    private
    @Inject
    LoginControlador loginControlador;

    public class UtilizacionMesa{
        private String mesa;
        private String ocupaciones;

        public String getMesa() {
            return mesa;
        }

        public void setMesa(String mesa) {
            this.mesa = mesa;
        }

        public String getOcupaciones() {
            return ocupaciones;
        }

        public void setOcupaciones(String ocupaciones) {
            this.ocupaciones = ocupaciones;
        }
    };

    private List<UtilizacionMesa> rankingUtilizacion;
    private Date fechaInicial;
    private Date fechaFinal;
    
    public UtilizacionMesas(){

    }

    public void actualizarResultado(){
        
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

    public List<UtilizacionMesa> getRankingUtilizacion() {
        rankingUtilizacion = new ArrayList<UtilizacionMesa>();
        try {
            Query q = em.createNativeQuery(consultaMesas());
            List<Object[]> resultado = q.getResultList();
            for (Object[] registro : resultado) {
                UtilizacionMesa item = new UtilizacionMesa();
                item.setMesa(registro[0].toString());
                item.setOcupaciones(registro[1].toString());
                rankingUtilizacion.add(item);
            }
        } catch (Exception ex) {
            System.out.println("Ranking Utilizacion:" + ex.getMessage());
            return null;
        }
        return rankingUtilizacion;
    }

    private String consultaMesas(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaIni = sdf.format(getFechaInicial());
        String fechaFin = sdf.format(getFechaFinal());
	String query = "SELECT mesa, count(id_pedido) as CANTIDAD " +
        "FROM pedido " +
        "WHERE fecha between '" + fechaIni + "' and '" + fechaFin + "' " +
        "GROUP BY mesa " +
        "ORDER BY CANTIDAD desc";
        return query;
    }

     public Date getFechaFinal() {
        if(fechaFinal == null){
            Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), 11, 31);
            fechaFinal = cal.getTime();
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
            Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), 0, 1);
            fechaInicial = cal.getTime();
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