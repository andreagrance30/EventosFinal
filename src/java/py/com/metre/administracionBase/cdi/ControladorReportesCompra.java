package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.primefaces.event.DateSelectEvent;
import py.com.metre.administracionBase.util.ImprimirReporte;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class ControladorReportesCompra implements Serializable {

    private ImprimirReporte imprimirreporte;
    Logger logger = Logger.getLogger(ControladorReportesCompra.class);
    @PersistenceContext
    protected EntityManager em;

    private
    @Inject
    LoginControlador loginControlador;

    private Date fechaInicial;
    private Date fechaFinal;
    
    public ControladorReportesCompra(){
        imprimirreporte = new ImprimirReporte();
    }

    public void generarPDF(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String condicionFechas = " f.fecha between ";
        condicionFechas += "'" + sdf.format(this.getFechaInicial()) + "'";
        condicionFechas += " and ";
        condicionFechas += "'" + sdf.format(this.getFechaFinal()) + "'";
        
        // Llamar al generador del reporte
        Map<String, Object> parametros = new HashMap<String, Object>();
        imprimirreporte.setNombreReporte("ResumenCompras");
        parametros.put("condicionFechas", condicionFechas);
        parametros.put("fechaDesde", this.getFechaInicial());
        parametros.put("fechaHasta", this.getFechaFinal());
        parametros.put("impresoPor", loginControlador.getUsuario().getNombre());
        imprimirreporte.setParametros(parametros);
        imprimirreporte.imprimir();
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