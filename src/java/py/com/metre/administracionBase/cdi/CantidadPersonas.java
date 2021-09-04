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
 * @author 
 */
@Named
@SessionScoped
public class CantidadPersonas implements Serializable {

    Logger logger = Logger.getLogger(CantidadPersonas.class);
    @PersistenceContext
    protected EntityManager em;

    private
    @Inject
    LoginControlador loginControlador;

    public class PersonasDia {
        private String fecha;
        private String total;

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    };

    private List<PersonasDia> detallePersonas;
    private Date fechaInicial;
    private Date fechaFinal;
    
    public CantidadPersonas(){

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

    public List<PersonasDia> getDetallePersonas() {
        detallePersonas = new ArrayList<PersonasDia>();
        try {
            Query q = em.createNativeQuery(consultaDetalle());
            List<Object[]> resultado = q.getResultList();
            for (Object[] registro : resultado) {
                PersonasDia item = new PersonasDia();
                item.setTotal(registro[0].toString());
                item.setFecha(registro[1].toString());
                detallePersonas.add(item);
            }
        } catch (Exception ex) {
            System.out.println("Detalle Periodo:" + ex.getMessage());
            return null;
        }
        return detallePersonas;
    }

    public String getTotalPersonas() {
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

    private String consultaTotal(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaIni = sdf.format(getFechaInicial());
        String fechaFin = sdf.format(getFechaFinal());
	String query = "SELECT SUM (cant_personas) as CANTIDAD " +
        "FROM personas_mesa " +
        "WHERE fecha between '" + fechaIni + "' and '" + fechaFin + "'";
        return query;
    }

     private String consultaDetalle(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String fechaIni = sdf.format(getFechaInicial());
        String fechaFin = sdf.format(getFechaFinal());
        String query = "SELECT SUM (cant_personas) as CANTIDAD, to_char(fecha,'DD/MM/YYYY') as FECHA " +
        "FROM personas_mesa " +
        "WHERE fecha between '" + fechaIni + "' and '" + fechaFin + "' " +
        "GROUP BY fecha " +
        "ORDER BY cantidad desc";
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