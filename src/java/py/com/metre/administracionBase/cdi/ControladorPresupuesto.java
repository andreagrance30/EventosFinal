package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import py.com.metre.administracionBase.ejb.ProductoEJB;
import py.com.metre.administracionBase.ejb.ClienteEJB;
import py.com.metre.administracionBase.ejb.SucursalEJB;
import py.com.metre.administracionBase.jpa.Presupuesto;
import py.com.metre.administracionBase.jpa.Producto;
import py.com.metre.administracionBase.jpa.Cliente;
import py.com.metre.administracionBase.jpa.DetallePresupuesto;
import py.com.metre.administracionBase.jpa.Sucursal;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author
 */
@Named
@SessionScoped
public class ControladorPresupuesto implements Serializable {

    Logger logger = Logger.getLogger(Presupuesto.class);
    @PersistenceContext
    protected EntityManager em;
    private @Inject
    LoginControlador loginControlador;

    private Date horaInicio;
    private Date horaFin;
    private Date fechaEvento;
    private Date fechaCotizacion;
    private Date fechaDevolucion;
    private BigDecimal cantidadPersonas;
    private String direccion;
    private String descripcion;
    private BigDecimal totalPagar;
    private BigDecimal subtotal;
    private String estado;
    private DetallePresupuesto detallePresupuestoSeleccionado;
    private Producto productoSeleccionado;
    private Cliente clienteSeleccionado;
    private Sucursal sucursalSeleccionado;
    private List<DetallePresupuesto> detallePresupuesto;
    private List<Producto> listaProducto;
    private List<Cliente> listaCliente;
    private List<Sucursal> listaSucursal;
    private BigDecimal cantidadItem;

    @EJB
    private PresupuestoEJB presupuestoEJB;
    @EJB
    private DetallePresupuestoEJB detallePresupuestoEJB;
    @EJB
    private ProductoEJB productosEJB;
    @EJB
    private ClienteEJB clienteEJB;
    @EJB
    private SucursalEJB sucursalEJB;

    public ControladorPresupuesto() {

        totalPagar = BigDecimal.ZERO;
        detallePresupuesto = new ArrayList<DetallePresupuesto>();
        listaCliente = new ArrayList<Cliente>();
        listaProducto = new ArrayList<Producto>();
        listaSucursal = new ArrayList<Sucursal>();
        cantidadItem = BigDecimal.ZERO;
        fechaEvento = new Date();
        fechaCotizacion = new Date();
        fechaDevolucion = new Date();
        horaInicio = new Date();
        horaFin = new Date();

    }

    public void limpiar() {
        
         horaInicio = new Date();
        horaFin = new Date();

        totalPagar = BigDecimal.ZERO;
        clienteSeleccionado = null;
        productoSeleccionado = null;
        detallePresupuesto = new ArrayList<DetallePresupuesto>();
        cantidadItem = BigDecimal.ZERO;
        fechaEvento = new Date();
        fechaCotizacion = new Date();
        fechaDevolucion = new Date();
        descripcion = null;
        direccion = null;
       

    }

    public void guardar() {
        System.out.println("TEST 0");
//        validarFechas();
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
            presuNuevo.setTotal(totalPagar);
            presuNuevo.setEstado(estado);
            presuNuevo.setClienteid(clienteSeleccionado);
            presuNuevo.setUsuarioid(loginControlador.getUsuario());
    
            presupuestoEJB.insertar(presuNuevo);
            System.out.println("TEST 2");
            for (DetallePresupuesto item : detallePresupuesto) {
                item.setPresupuestoid(presuNuevo);
                detallePresupuestoEJB.insertar(item);
                System.out.println("aver que pasa");
            }
            System.out.println("TEST 3");
////            limpiar();
            JsfUtil.agregarMensajeExito("El evento ha sido registrado con éxito.");
            System.out.println("TEST 4");
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorCampo("Evento", "No se pudo guardar el registro del evento.");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
    }

    public void agregarItem() {

        if (this.productoSeleccionado == null || this.productoSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Item", "Debe seleccionar un producto para agregar a la lista de evento.");
            return;
        }
        if (this.productoSeleccionado.getPrecio() == null) {
            JsfUtil.agregarMensajeErrorCampo("Costo", "Debe indicar el costo del ítem.");
            return;
        }
/*        if (this.productoSeleccionado.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            JsfUtil.agregarMensajeErrorCampo("Costo", "Ingrese un valor de costo válido.");
            return;
        } */
        if (this.cantidadItem == null) {
            JsfUtil.agregarMensajeErrorCampo("Cantidad", "Debe indicar la cantidad .");
            return;
        }
        if (this.cantidadItem.compareTo(BigDecimal.ZERO) <= 0) {
            JsfUtil.agregarMensajeErrorCampo("Cantidad", "Ingrese una cantidad válida.");
            return;
        }

        DetallePresupuesto item = new DetallePresupuesto();
        item.setProductoid(productoSeleccionado);
        item.setCantidad(cantidadItem);
//        item.setPrecioUnitario(productoSeleccionado.getPrecio());
        detallePresupuesto.add(item);
        subtotal = item.getPrecioUnitario().multiply(cantidadItem);
        totalPagar = totalPagar.add(subtotal);
        // productoSeleccionado = null;
        //  cantidadItem = null; 
        /*
        for(int i = 0; i < detallePresupuesto.size(); i++) {   
        System.out.print(detallePresupuesto.get(i));
} */

    }

    private boolean validar() {
        if (clienteSeleccionado == null || clienteSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Cliente", "Debe seleccionar el cliente.");
            return false;
        }

        if (detallePresupuesto == null || detallePresupuesto.isEmpty()) {
            JsfUtil.agregarMensajeErrorCampo("Detalles", "Debe agregar por lo menos un ítem.");
            return false;
        }
        return true;
    }

    /*
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
     */
    public void eliminarDetalle(DetallePresupuesto detalle) {
        int index = 0;
        for (DetallePresupuesto dc : detallePresupuesto) {
            if (dc.getProductoid().getId().equals(detalle.getProductoid().getId())) {
                detallePresupuesto.remove(index);
                totalPagar = totalPagar.subtract(subtotal);
                break;
            }
            index++;
        }
        /*   System.out.println("LENGTH: " + detallePresupuesto.size());
              List<DetallePresupuesto> detallesAux = detallePresupuesto;
        detallePresupuesto = new ArrayList<DetallePresupuesto>();
        for (DetallePresupuesto dc : detallesAux) {
            detallePresupuesto.add(dc);
        }
        System.out.println("SIZE: " + detallePresupuesto.size()); */
    }

    public List<Producto> getProducto() {
        try {
            listaProducto = productosEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Producto:" + ex.getMessage());
            return null;
        }
        return listaProducto;
    }

    public List<Cliente> getListaCliente() {
        try {
            listaCliente = clienteEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Cliente:" + ex.getMessage());
            return null;
        }
        return listaCliente;
    }

    public List<DetallePresupuesto> getDetallePresupuesto() {
        try {
            detallePresupuesto = detallePresupuestoEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado del detalle:" + ex.getMessage());
            return null;
        }
        return detallePresupuesto;
    }

    public DetallePresupuesto getDetallePresupuestoSeleccionado() {
        return detallePresupuestoSeleccionado;
    }

    public void setDetallePresupuestoSeleccionado(DetallePresupuesto detallePresupuestoSeleccionado) {
        if (detallePresupuestoSeleccionado != null) {
            this.detallePresupuestoSeleccionado = detallePresupuestoSeleccionado;
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

    public BigDecimal getTotalPagarEvento() {
        return totalPagar;
    }

    public void setTotalPagarEvento(BigDecimal totalPagar) {
        if (totalPagar != null) {
            this.totalPagar = totalPagar;
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

    public Sucursal getSucursalSeleccionado() {
        return sucursalSeleccionado;
    }

    public void setSucursalSeleccionado(Sucursal sucursalSeleccionado) {
        this.sucursalSeleccionado = sucursalSeleccionado;
    }

    public List<Sucursal> getListaSucursal() {
        try {
            listaSucursal = sucursalEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Sucursales:" + ex.getMessage());
            return null;
        }
        return listaSucursal;
    }

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
