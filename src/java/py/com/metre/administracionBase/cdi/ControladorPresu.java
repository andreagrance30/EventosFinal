package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import java.math.BigDecimal;
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
import py.com.metre.administracionBase.ejb.PresupuestoEJB;
import py.com.metre.administracionBase.ejb.DetallePresupuestoEJB;
import py.com.metre.administracionBase.ejb.ClienteEJB;
import py.com.metre.administracionBase.ejb.SucursalEJB;
import py.com.metre.administracionBase.ejb.ProductoEJB;
import py.com.metre.administracionBase.jpa.Presupuesto;
import py.com.metre.administracionBase.jpa.DetallePresupuesto;
import py.com.metre.administracionBase.jpa.Cliente;
import py.com.metre.administracionBase.jpa.Sucursal;
import py.com.metre.administracionBase.jpa.Producto;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author
 */
@Named
@SessionScoped
public class ControladorPresu implements Serializable {

    Logger logger = Logger.getLogger(Presupuesto.class);
    @PersistenceContext
    protected EntityManager em;
    private @Inject
    LoginControlador loginControlador;
    @EJB
    private PresupuestoEJB presupuestoEJB;
    private DetallePresupuesto detallePresuSeleccionado;
    private Date horaInicio;
    private Date horaFin;
    private Date fechaEvento;
    private Date fechaCotizacion;
    private Date fechaDevolucion;
    private BigDecimal cantidadPersonas;
    private String direccion;
    private String descripcion;
    private BigDecimal montoTotalPresu;
    private String estado;
    private Cliente clienteSeleccionado;
    private List<Cliente> listaClientes;
    private Sucursal sucursalSeleccionado;
    private List<Sucursal> listaSucursal;
    //Variables del Detalle Presupuesto
    private Producto productoSeleccionado;
    private List<Producto> listaProducto;
    private List<DetallePresupuesto> detallesPresu;
    private BigDecimal cantidadItem;

    @EJB
    private DetallePresupuestoEJB detallePresupuestoEJB;
    @EJB
    private ClienteEJB clienteEJB;
    @EJB
    private SucursalEJB sucursalEJB;
    @EJB
    private ProductoEJB productoEJB;

    public ControladorPresu() {

        horaInicio = new Date();
        horaFin = new Date();
        fechaEvento = new Date();
        fechaCotizacion = new Date();
        fechaDevolucion = new Date();
        cantidadPersonas = BigDecimal.ZERO;
        montoTotalPresu = BigDecimal.ZERO;
        listaClientes = new ArrayList<Cliente>();
        listaSucursal = new ArrayList<Sucursal>();
        listaProducto = new ArrayList<Producto>();
        detallesPresu = new ArrayList<DetallePresupuesto>();
        cantidadItem = BigDecimal.ZERO;

    }

    public void limpiar() {

        horaInicio = new Date();
        horaFin = new Date();
        fechaEvento = new Date();
        fechaCotizacion = new Date();
        fechaDevolucion = new Date();
        cantidadPersonas = BigDecimal.ZERO;
        direccion = null;
        descripcion = null;
        montoTotalPresu = BigDecimal.ZERO;
        estado = null;
        clienteSeleccionado = null;
        sucursalSeleccionado = null;
        productoSeleccionado = null;
        detallesPresu = new ArrayList<DetallePresupuesto>();
        cantidadItem = BigDecimal.ZERO;

    }

    public void guardar() {
        System.out.println("TEST 0");
        validarFechas();
        if (!validar()) {
            return;
        }
        System.out.println("TEST 1");
        try {
            Presupuesto presuNuevo = new Presupuesto();
            
            presuNuevo.setHoraInicio(horaInicio);
            presuNuevo.setHoraFin(horaFin);
            presuNuevo.setFechaEvento(fechaEvento);
            presuNuevo.setFechaCotizacion(fechaCotizacion);
            presuNuevo.setFechaDevolucion(fechaDevolucion);
            presuNuevo.setCantidadPersonas(cantidadPersonas);
            presuNuevo.setDireccion(direccion);
            presuNuevo.setDescripcion(descripcion);
            presuNuevo.setTotal(montoTotalPresu );
            presuNuevo.setEstado(estado);
            presuNuevo.setClienteid(clienteSeleccionado);
            presuNuevo.setUsuarioid(loginControlador.getUsuario());
           
            presupuestoEJB.insertar(presuNuevo);
            System.out.println("TEST 2");
            for (DetallePresupuesto item : detallesPresu) {
                item.setPresupuestoid(presuNuevo);
                detallePresupuestoEJB.insertar(item);
                // Actualizar producto existente del item presu
          /*      Producto stock = item.getProductoid();
                BigDecimal producto = stock.getStock();
                stock.setStock(producto.add(item.getCantidad()));
                productoEJB.actualizar(stock);*/
            }
            System.out.println("TEST 3");
            limpiar();
            JsfUtil.agregarMensajeExito("El presupuesto ha sido registrado con éxito.");
            System.out.println("TEST 4");
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorCampo("Presu", "No se pudo guardar el registro de presu.");
            logger.error(ex.getMessage());
        }
    }

    public void agregarItem() {

        if (productoSeleccionado == null || productoSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Stock", "Debe seleccionar un producto de la presu.");
            return;
        }

        if (this.cantidadItem == null) {
            JsfUtil.agregarMensajeErrorCampo("Cantidad", "Debe indicar la cantidad presuda.");
            return;
        }

        DetallePresupuesto item = new DetallePresupuesto();
        item.setProductoid(productoSeleccionado);
        item.setCantidad(cantidadItem);
//        item.setPrecioUnitario(productoSeleccionado.getPrecio());
        item.setSubtotal(item.getPrecioUnitario().multiply(cantidadItem));
        detallesPresu.add(item);
        montoTotalPresu = montoTotalPresu.add(item.getSubtotal());
        //   productoSeleccionado = null;
        //  cantidadItem = null;

    }

    private boolean validar() {

        if (clienteSeleccionado == null || clienteSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Cliente", "Debe seleccionar el cliente de la presu.");
            return false;
        }

        if (sucursalSeleccionado == null || sucursalSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Medio de Pago", "Debe seleccionar el medio de Pago de la presu.");
            return false;
        }
        if (detallesPresu == null || detallesPresu.isEmpty()) {
            JsfUtil.agregarMensajeErrorCampo("Detalles", "Debe agregar por lo menos un ítem presudo.");
            return false;
        }
        return true;
    }
    
     public void validarFechas() {
        if (fechaCotizacion.compareTo(fechaEvento) > 0) {
            JsfUtil.agregarMensajeErrorCampo("Fecha", "La fecha de cotizacion debe ser anterior a la fecha del evento");

        } else if (fechaCotizacion.compareTo(fechaEvento) == 0) {
            JsfUtil.agregarMensajeErrorCampo("Fecha", "La fecha de cotizacion y del evento no pueden ser las mismas");
        }
        if (fechaEvento.compareTo(fechaDevolucion) > 0) {
            JsfUtil.agregarMensajeErrorCampo("Fecha", "La fecha del evento debe ser anterior a la fecha de devolucion");

        } else if (fechaEvento.compareTo(fechaDevolucion) == 0) {
            JsfUtil.agregarMensajeErrorCampo("Fecha", "La fecha del evento y de devolucion no pueden ser las mismas");
        }
    }

    public void eliminarDetalle(DetallePresupuesto detalle) {
        int index = 0;
        for (DetallePresupuesto dc : detallesPresu) {
            if (dc.getProductoid().getId().equals(detalle.getProductoid().getId())) {
                detallesPresu.remove(index);
                montoTotalPresu = montoTotalPresu.subtract(detalle.getSubtotal());
                break;
            }
            index++;
        }
        System.out.println("LENGTH: " + detallesPresu.size());
        List<DetallePresupuesto> detallesAux = detallesPresu;
        detallesPresu = new ArrayList<DetallePresupuesto>();
        for (DetallePresupuesto dc : detallesAux) {
            detallesPresu.add(dc);
        }
        System.out.println("SIZE: " + detallesPresu.size());
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

    public List<Cliente> getClientees() {
        try {
            listaClientes = clienteEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Cliente:" + ex.getMessage());
            return null;
        }
        return listaClientes;
    }

    public List<Sucursal> getSucursal() {
        try {
            listaSucursal = sucursalEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Medio de Pago:" + ex.getMessage());
            return null;
        }
        return listaSucursal;
    }

    public List<DetallePresupuesto> getDetallesPresu() {
        return detallesPresu;
    }

    public DetallePresupuesto getDetallePresuSeleccionado() {
        return detallePresuSeleccionado;
    }

    public void setDetallePresuSeleccionado(DetallePresupuesto detallePresuSeleccionado) {
        if (detallePresuSeleccionado != null) {
            this.detallePresuSeleccionado = detallePresuSeleccionado;
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

    public BigDecimal getMontoTotalPresu() {
        return montoTotalPresu;
    }

    public void setMontoTotalPresu(BigDecimal montoTotalPresu) {
        if (montoTotalPresu != null) {
            this.montoTotalPresu = montoTotalPresu;
        }
    }

    public BigDecimal getCantidadItem() {
        return cantidadItem;
    }

    public void setCantidadItem(BigDecimal cantidadItem) {
        if (cantidadItem != null) {
            this.cantidadItem = cantidadItem;
        }
    }

    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        if (clienteSeleccionado != null) {
            this.clienteSeleccionado = clienteSeleccionado;
        }
    }

    public Sucursal getSucursalSeleccionado() {
        return sucursalSeleccionado;
    }

    public void setSucursalSeleccionado(Sucursal sucursalSeleccionado) {
        if (sucursalSeleccionado != null) {
            this.sucursalSeleccionado = sucursalSeleccionado;
        }
    }

    public Date getFechaEvento() {
        if (fechaEvento == null) {
            fechaEvento = new Date();
        }
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        if (fechaEvento != null) {
            this.fechaEvento = fechaEvento;
        }
    }

    public void actualizarFecha(DateSelectEvent event) {
        fechaEvento = event.getDate();
    }

    public String getFechaHoy() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaEvento = sdf.format(new Date());
        return fechaEvento;
    }

    public Date getFechaCotizacion() {
        return fechaCotizacion;
    }

    public void setFechaCotizacion(Date fechaCotizacion) {
        this.fechaCotizacion = fechaCotizacion;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public BigDecimal getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(BigDecimal cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
