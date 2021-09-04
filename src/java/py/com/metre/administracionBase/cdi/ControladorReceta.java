package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.primefaces.event.DateSelectEvent;
import py.com.metre.administracionBase.ejb.RecetaEJB;
import py.com.metre.administracionBase.ejb.DetalleRecetaEJB;
import py.com.metre.administracionBase.ejb.UnidadMedidaEJB;
import py.com.metre.administracionBase.ejb.ProductoEJB;
import py.com.metre.administracionBase.jpa.Receta;
import py.com.metre.administracionBase.jpa.DetalleReceta;
import py.com.metre.administracionBase.jpa.UnidadDeMedida;
import py.com.metre.administracionBase.jpa.Producto;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author
 */
@Named
@SessionScoped
public class ControladorReceta implements Serializable {

    Logger logger = Logger.getLogger(Receta.class);
    @PersistenceContext
    protected EntityManager em;
    private @Inject
    LoginControlador loginControlador;
    @EJB
    private RecetaEJB recetaEJB;
    private Date fechaFactura;
    private DetalleReceta detalleRecetaSeleccionado;
    private UnidadDeMedida unidadMedidaSeleccionado;
    private Producto productoSeleccionado;
    private List<DetalleReceta> detallesReceta;
    private List<UnidadDeMedida> listaUnidadDeMedida;
    private List<Producto> listaProducto;
    private BigDecimal cantidadPersonas;
    private BigDecimal raciones;
    private String nombreReceta;
    private String descripcionPreparacion;
    private BigDecimal cantidadXReceta;
    
    @EJB
    private DetalleRecetaEJB detalleRecetaEJB;
    @EJB
    private UnidadMedidaEJB unidadMedidaesEJB;
    @EJB
    private ProductoEJB productoEJB;

    public ControladorReceta() {
        raciones = BigDecimal.ZERO;
        detallesReceta = new ArrayList<DetalleReceta>();
        cantidadPersonas = BigDecimal.ZERO;
        cantidadXReceta = BigDecimal.ZERO;
        fechaFactura = new Date();
    }

    public void limpiar() {
        raciones = BigDecimal.ZERO;
        unidadMedidaSeleccionado = null;
        detallesReceta = new ArrayList<DetalleReceta>();
        cantidadPersonas = BigDecimal.ZERO;
        cantidadXReceta = BigDecimal.ZERO;
        fechaFactura = new Date();
        nombreReceta = null;
        descripcionPreparacion = null;
    }

    public void guardar() {
        System.out.println("TEST 0");
        if (!validar()) {
            return;
        }
        System.out.println("TEST 1");
        try {
            Receta recetaNueva = new Receta();
            recetaNueva.setNombreReceta(nombreReceta);
            recetaNueva.setDescripcionPreparacion(descripcionPreparacion);
            recetaEJB.insertar(recetaNueva);
            System.out.println("TEST 2");
            for (DetalleReceta item : detallesReceta) {
                item.setRecetaid(recetaNueva);
                detalleRecetaEJB.insertar(item);
            }
            
            System.out.println("TEST 3");
            limpiar();
            JsfUtil.agregarMensajeExito("La receta ha sido registrada con éxito.");
            System.out.println("TEST 4");
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorCampo("Receta", "No se pudo guardar el registro de compra.");
            logger.error(ex.getMessage());
        }
    }

    public void calcular() {

    }

    public void agregarItem() {

        if (productoSeleccionado == null || productoSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Stock", "Debe seleccionar un producto de la compra.");
            return;
        }
        /*
        if (this.cantidadPersonas == null) {
            JsfUtil.agregarMensajeErrorCampo("Cantidad", "Debe indicar la cantidad comprada.");
            return;
        }
         */
        DetalleReceta item = new DetalleReceta();
        item.setProductoid(productoSeleccionado);
        item.setCantidadPersonas(cantidadPersonas);
        item.setRaciones(raciones);
        item.setUnidadDeMedidaid(unidadMedidaSeleccionado);
        item.setCantidadPorReceta(cantidadXReceta);
        BigDecimal var= item.getCantidadPersonas().multiply(item.getCantidadPorReceta());
        BigDecimal result= var.divide(item.getRaciones(), RoundingMode.HALF_UP);
        item.setCantidadPorXpersona(result);
        detallesReceta.add(item);
        //   productoSeleccionado = null;
        //  cantidadPersonas = null;

    }

    private boolean validar() {

        if (unidadMedidaSeleccionado == null || unidadMedidaSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("UnidadDeMedida", "Debe seleccionar el unidadMedida de la compra.");
            return false;
        }

        if (detallesReceta == null || detallesReceta.isEmpty()) {
            JsfUtil.agregarMensajeErrorCampo("Detalles", "Debe agregar por lo menos un ítem comprado.");
            return false;
        }
        return true;
    }

    public void eliminarDetalle(DetalleReceta detalle) {
        int index = 0;
        for (DetalleReceta dc : detallesReceta) {
            if (dc.getProductoid().getId().equals(detalle.getProductoid().getId())) {
                detallesReceta.remove(index);
                break;
            }
            index++;
        }
        System.out.println("LENGTH: " + detallesReceta.size());
        List<DetalleReceta> detallesAux = detallesReceta;
        detallesReceta = new ArrayList<DetalleReceta>();
        for (DetalleReceta dc : detallesAux) {
            detallesReceta.add(dc);
        }
        System.out.println("SIZE: " + detallesReceta.size());
    }

    public List<Producto> getListaProducto() {
        try {
            listaProducto = productoEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Stock:" + ex.getMessage());
            return null;
        }
        return listaProducto;
    }

    public List<UnidadDeMedida> getUnidadDeMedidaes() {
        try {
            listaUnidadDeMedida = unidadMedidaesEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de UnidadDeMedida:" + ex.getMessage());
            return null;
        }
        return listaUnidadDeMedida;
    }

    public List<DetalleReceta> getDetallesReceta() {
        return detallesReceta;
    }

    public DetalleReceta getDetalleRecetaSeleccionado() {
        return detalleRecetaSeleccionado;
    }

    public void setDetalleRecetaSeleccionado(DetalleReceta detalleRecetaSeleccionado) {
        if (detalleRecetaSeleccionado != null) {
            this.detalleRecetaSeleccionado = detalleRecetaSeleccionado;
        }
    }

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        if (productoSeleccionado != null) {
            this.productoSeleccionado = productoSeleccionado;
        }
    }

    public BigDecimal getRaciones() {
        return raciones;
    }

    public void setRaciones(BigDecimal raciones) {
        if (raciones != null) {
            this.raciones = raciones;
        }
    }

    public BigDecimal getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(BigDecimal cantidadPersonas) {
        if (cantidadPersonas != null) {
            this.cantidadPersonas = cantidadPersonas;
        }
    }

    public BigDecimal getCantidadXReceta() {
        return cantidadXReceta;
    }

    public void setCantidadXReceta(BigDecimal cantidadXReceta) {
        if (cantidadXReceta != null) {
            this.cantidadXReceta = cantidadXReceta;
        }
    }

    public UnidadDeMedida getUnidadDeMedidaSeleccionado() {
        return unidadMedidaSeleccionado;
    }

    public void setUnidadDeMedidaSeleccionado(UnidadDeMedida unidadMedidaSeleccionado) {
        if (unidadMedidaSeleccionado != null) {
            this.unidadMedidaSeleccionado = unidadMedidaSeleccionado;
        }
    }

    public String getNombreReceta() {
        return nombreReceta;
    }

    public void setNombreReceta(String nombreReceta) {
        if (nombreReceta != null) {
            this.nombreReceta = nombreReceta;
        }
    }

    public String getDescripcionPreparacion() {
        return descripcionPreparacion;
    }

    public void setDescripcionPreparacion(String descripcionPreparacion) {
        if (descripcionPreparacion != null) {
            this.descripcionPreparacion = descripcionPreparacion;
        }
    }

    public Date getFechaFactura() {
        if (fechaFactura == null) {
            fechaFactura = new Date();
        }
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        if (fechaFactura != null) {
            this.fechaFactura = fechaFactura;
        }
    }

    public void actualizarFechaFactura(DateSelectEvent event) {
        fechaFactura = event.getDate();
    }

    public String getFechaHoy() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = sdf.format(new Date());
        return fecha;
    }
    
    
}
