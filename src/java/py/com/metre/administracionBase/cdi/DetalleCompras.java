package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import py.com.metre.administracionBase.jpa.Compras;

/**
 *
 * @author Adolfo
 */
@Named
@SessionScoped
public class DetalleCompras implements Serializable {

    void setCompras(Compras compraNueva) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*
    private ImprimirReporte imprimirreporte;
    Logger logger = Logger.getLogger(DetalleCompras.class);
    @PersistenceContext
    protected EntityManager em;

    private
    @Inject
    LoginControlador loginControlador;

    private Date fechaInicial;
    private Date fechaFinal;
    private Proveedores proveedorSeleccionado;
    private List<Proveedores> listaProveedores;
    @EJB
    private ProveedoresEJB proveedoresEJB;

    
    public DetalleCompras(){
        imprimirreporte = new ImprimirReporte();
    }

    public void generarPDF(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String condicionFechas = " f.fecha between ";
        condicionFechas += "'" + sdf.format(this.getFechaInicial()) + "'";
        condicionFechas += " and ";
        condicionFechas += "'" + sdf.format(this.getFechaFinal()) + "'";

        String condicionProveedor = "";
        String proveedorStr = "";
        if (proveedorSeleccionado != null && proveedorSeleccionado.getId() != null) {
            condicionProveedor = " AND f.id_proveedor = " + proveedorSeleccionado.getId();
            proveedorStr += proveedorSeleccionado.getNombre();
        } else {
            condicionProveedor = " ";
            proveedorStr += "TODOS";
        }

        // Llamar al generador del reporte
        Map<String, Object> parametros = new HashMap<String, Object>();
        imprimirreporte.setNombreReporte("DetalleCompras");
        parametros.put("condicionFechas", condicionFechas);
        parametros.put("condicionProveedor", condicionProveedor);
        parametros.put("proveedor", proveedorStr);
        parametros.put("fechaDesde", this.getFechaInicial());
        parametros.put("fechaHasta", this.getFechaFinal());
        parametros.put("impresoPor", loginControlador.getUsuario().getUsername());
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

    public List<Proveedores> getProveedores() {
        try {
            listaProveedores = proveedoresEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Proveedores:" + ex.getMessage());
            return null;
        }
        return listaProveedores;
    }

    public Proveedores getProveedorSeleccionado() {
        return proveedorSeleccionado;
    }

    public void setProveedorSeleccionado(Proveedores proveedorSeleccionado) {
        if (proveedorSeleccionado != null) {
            this.proveedorSeleccionado = proveedorSeleccionado;
        }
    }*/
}