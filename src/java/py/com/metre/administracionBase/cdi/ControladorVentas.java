package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import org.apache.log4j.Logger;
import org.primefaces.event.DateSelectEvent;
import py.com.metre.administracionBase.cdi.LoginControlador;
import py.com.metre.administracionBase.ejb.AjustesEJB;
import py.com.metre.administracionBase.ejb.ClienteEJB;
import py.com.metre.administracionBase.ejb.CobrosEJB;
import py.com.metre.administracionBase.ejb.DetalleCobrosEJB;
import py.com.metre.administracionBase.ejb.DetalleVentasEJB;
import py.com.metre.administracionBase.ejb.ProductoEJB;
import py.com.metre.administracionBase.ejb.VentasEJB;
import py.com.metre.administracionBase.ejb.FormaCobroEJB;
import py.com.metre.administracionBase.jpa.Ajustes;
import py.com.metre.administracionBase.jpa.Cliente;
import py.com.metre.administracionBase.jpa.Cobros;
import py.com.metre.administracionBase.jpa.FormaCobro;
import py.com.metre.administracionBase.jpa.DetalleCobros;
import py.com.metre.administracionBase.jpa.DetalleVentas;
import py.com.metre.administracionBase.jpa.Producto;
import py.com.metre.administracionBase.jpa.Ventas;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author Andy
 */
@Named
@SessionScoped
public class ControladorVentas implements Serializable {

    Logger logger = Logger.getLogger(Ventas.class);
    @PersistenceContext
    protected EntityManager em;
    private @Inject
    LoginControlador loginControlador;
    private Date fechaFactura;               //Ventas Cabecera
    private String nroFactura;
    private String condicionVenta;
    private BigDecimal subtotal;
    private BigDecimal totalPagar;
    private Cliente clienteSeleccionado;
    private List<Cliente> listaCliente;
    private FormaCobro formaCobroSeleccionado;
    private List<FormaCobro> listaFormaCobro;
    private Cobros cobrosSeleccionado;
    private List<Cobros> listaCobros;
    private DetalleVentas detalleSeleccionado;   //Detalle de Ventas
    private List<DetalleVentas> listaDetalle;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private Producto productoSeleccionado;
    private List<Producto> listaProducto;
    private BigDecimal nroCuotas;
    private Boolean esContado;
    private String tipoIvaItem; // Exento (0), 5% (5), 10% (10)
    private BigDecimal montoIvaItem; // Monto deducido del costo y el tipo de IVA
    private List<String> listaTiposIva;
    @EJB
    private VentasEJB ventasEJB;
    @EJB
    private DetalleVentasEJB detalleVentasEJB;
    @EJB
    private ClienteEJB clienteEJB;
    @EJB
    private CobrosEJB cobrosEJB;
    @EJB
    private DetalleCobrosEJB detCobEJB;
    @EJB
    private ProductoEJB productoEJB;
    @EJB
    private AjustesEJB ajustesEJB;
    @EJB
    private FormaCobroEJB formaCobroEJB;

    public ControladorVentas() {

        fechaFactura = new Date();
        nroFactura = "";
        condicionVenta = "efectivo";
        subtotal = BigDecimal.ZERO;
        montoIvaItem = BigDecimal.ZERO;
        totalPagar = BigDecimal.ZERO;
        cantidad = BigDecimal.ZERO;
        precioUnitario = BigDecimal.ZERO;
        tipoIvaItem = "Exento";
        listaDetalle = new ArrayList<DetalleVentas>();
    }

    public void limpiar() {

        fechaFactura = new Date();
        nroFactura = "";
        condicionVenta = "efectivo";
        subtotal = BigDecimal.ZERO;
        montoIvaItem = BigDecimal.ZERO;
        totalPagar = BigDecimal.ZERO;
        cantidad = BigDecimal.ZERO;
        precioUnitario = BigDecimal.ZERO;
        nroCuotas= BigDecimal.ZERO;
        clienteSeleccionado = null;
        cobrosSeleccionado = null;
        detalleSeleccionado = null;  // Detalle de Ventas
        productoSeleccionado = null;

    }

    public void guardar() {
        System.out.println("TEST 0");
        if (!validar()) {
            return;
        }

        System.out.println("TEST 1");
        try {
            Ventas ventaNueva = new Ventas();
            ventaNueva.setFechaFact(fechaFactura);
            ventaNueva.setNroFact(nroFactura);
            ventaNueva.setCondicionVenta(condicionVenta);
            ventaNueva.setTotalIva(montoIvaItem);
            ventaNueva.setTotalPagar(totalPagar);
            ventaNueva.setClienteid(clienteSeleccionado);
            ventaNueva.setFormaCobroid(formaCobroSeleccionado);
            ventaNueva.setUsuarioid(loginControlador.getUsuario());
            ventasEJB.insertar(ventaNueva);
            System.out.println("TEST 2");
            for (DetalleVentas item : listaDetalle) {
                item.setVentasid(ventaNueva);
                detalleVentasEJB.insertar(item);
                Producto stock = item.getProductoid();
                BigDecimal producto = stock.getCantidad();
                stock.setCantidad(producto.subtract(item.getCantidad()));
                productoEJB.actualizar(stock);

                //Actualizar stock existente del item evento
                Ajustes objAjustes = new Ajustes();
                objAjustes.setCantidad(item.getCantidad());
                objAjustes.setDescripcion("");
                objAjustes.setFechaAjuste(fechaFactura);
                objAjustes.setVentasid(item.getVentasid());
                objAjustes.setProductoid(item.getProductoid());
                objAjustes.setTipoajuste("Salida");
                ajustesEJB.insertar(objAjustes);
            }

            if (condicionVenta.equals("contado")) {
                pagoContado(ventaNueva);
            }
            if (condicionVenta.equals("credito")) {
                pagoCredito(ventaNueva);
            }

            //      MovimientoArticulos movArti
            System.out.println("TEST 3");
            ////      limpiar();
            JsfUtil.agregarMensajeExito("La venta ha sido registrada con éxito.");
            System.out.println("TEST 4");
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorCampo("Evento", "No se pudo guardar el registro de la venta.");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
    }

    private void pagoContado(Ventas venta) {
        Cobros cobro = new Cobros();
        cobro.setFechaPres(fechaFactura);
        cobro.setObservacion("");
        cobro.setVentasid(venta);
        cobro.setTotalCobro(totalPagar);
        cobro.setUsuarioid(loginControlador.getUsuario());
        cobrosEJB.insertar(cobro);
        //    cobro.
        /// detalle cobro
        DetalleCobros detCob = new DetalleCobros();
        detCob.setEstado("pagado");
        detCob.setFechaCobro(fechaFactura);
        detCob.setFechaVencimiento(fechaFactura);
        detCob.setMontoEntregado(totalPagar);
        detCob.setCobrosid(cobro);
        detCob.setFormaCobroid(formaCobroSeleccionado);
        detCobEJB.insertar(detCob);
    }

    private void pagoCredito(Ventas venta) {
        Cobros cobro = new Cobros();
        cobro.setFechaPres(fechaFactura);
        cobro.setTotalCobro(totalPagar);
        cobro.setObservacion("");
        cobro.setVentasid(venta);
        cobro.setUsuarioid(loginControlador.getUsuario());
        cobrosEJB.insertar(cobro);
        BigDecimal montoEntre = totalPagar.divide(nroCuotas);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaFactura);

        for (int i = 0; i < this.nroCuotas.intValue(); i++) {
            DetalleCobros detCob = new DetalleCobros();
            detCob.setEstado("pendiente");
            //    detCob.setFechaCobro(fechaFactura);
            detCob.setFechaVencimiento(calendar.getTime());
            detCob.setMontoEntregado(montoEntre);
            detCob.setFormaCobroid(formaCobroSeleccionado);
            detCob.setCobrosid(cobro);

            calendar.add(Calendar.DAY_OF_YEAR, 30);

            detCobEJB.insertar(detCob);

        }
    }

    private boolean validar() {
        if (clienteSeleccionado == null || clienteSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Cliente", "Debe seleccionar el cliente.");
            return false;
        }

        if (listaDetalle == null || listaDetalle.isEmpty()) {
            JsfUtil.agregarMensajeErrorCampo("Detalles", "Debe agregar por lo menos un ítem.");
            return false;
        }
        if (productoSeleccionado == null || productoSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Producto", "Debe seleccionar por lo menos un producto.");
            return false;
        }

        return true;
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
        if (this.productoSeleccionado.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            JsfUtil.agregarMensajeErrorCampo("Costo", "Ingrese un valor de costo válido.");
            return;
        }
        if (this.cantidad == null) {
            JsfUtil.agregarMensajeErrorCampo("Cantidad", "Debe indicar la cantidad .");
            return;
        }
        if (this.cantidad.compareTo(BigDecimal.ZERO) <= 0) {
            JsfUtil.agregarMensajeErrorCampo("Cantidad", "Ingrese una cantidad válida.");
            return;
        }

        DetalleVentas item = new DetalleVentas();
        item.setCantidad(cantidad);
        item.setDescripcion(productoSeleccionado.getDescripcion());
        item.setPrecioUnitario(productoSeleccionado.getPrecio());
        item.setProductoid(productoSeleccionado);
        item.setSubtotal(cantidad.multiply(item.getPrecioUnitario()));
          
     
        if (this.tipoIvaItem.equals("Exento")) {
            item.setTipoiva(BigDecimal.ZERO);
        } else if (this.tipoIvaItem.equals("5%")) {
            item.setTipoiva(BigDecimal.valueOf(5));
        } else if (this.tipoIvaItem.equals("10%")) {
            item.setTipoiva(BigDecimal.valueOf(10));
        }

        BigDecimal ivaIncluido = item.getTipoiva().add(BigDecimal.valueOf(100));
        BigDecimal IVA = item.getSubtotal().multiply(item.getTipoiva()); /* multiplicar por el subtotal no por el precio unitario*/
        System.out.println(IVA);
        IVA = IVA.divide(ivaIncluido, 2, RoundingMode.HALF_EVEN);
        listaDetalle.add(item);
        totalPagar = totalPagar.add(item.getSubtotal());
        montoIvaItem = montoIvaItem.add(IVA);

    }

    public void eliminarDetalle(DetalleVentas detalle) {
        int index = 0;
        for (DetalleVentas dc : listaDetalle) {
            if (dc.getProductoid().getId().equals(detalle.getProductoid().getId())) {
                listaDetalle.remove(index);
                totalPagar = totalPagar.subtract(detalle.getSubtotal());
                break;
            }
            index++;
        }
        System.out.println("LENGTH: " + listaDetalle.size());

    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
    }

    public String getCondicionVenta() {
        return condicionVenta;
    }

    public void setCondicionVenta(String condicionVenta) {
        if (condicionVenta != null) {
            this.condicionVenta = condicionVenta;
            if (condicionVenta.equals("contado")) {
                esContado = true;
                nroCuotas =  BigDecimal.ZERO;
            }
            if (condicionVenta.equals("credito")) {
                esContado = false;
                nroCuotas = new BigDecimal(2);
                
            }
        }
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
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

    public Cobros getCobrosSeleccionado() {
        return cobrosSeleccionado;
    }

    public void setCobrosSeleccionado(Cobros cobrosSeleccionado) {
        this.cobrosSeleccionado = cobrosSeleccionado;
    }

    public List<Cobros> getListaCobros() {
        return listaCobros;
    }

    public void setListaCobros(List<Cobros> listaCobros) {
        this.listaCobros = listaCobros;
    }

    public DetalleVentas getDetalleSeleccionado() {
        return detalleSeleccionado;
    }

    public void setDetalleSeleccionado(DetalleVentas detalleSeleccionado) {
        this.detalleSeleccionado = detalleSeleccionado;
    }

    public List<DetalleVentas> getListaDetalle() {
        return listaDetalle;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public List<Producto> getListaProducto() {
        try {
            listaProducto = productoEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Producto:" + ex.getMessage());
            return null;
        }
        return listaProducto;
    }

    public BigDecimal getNroCuotas() {
        return nroCuotas;
    }

    public void setNroCuotas(BigDecimal nroCuotas) {
        this.nroCuotas = nroCuotas;
    }

    public Boolean getEsContado() {
        return esContado;
    }

    public void setEsContado(Boolean esContado) {
        this.esContado = esContado;
    }

    public BigDecimal getMontoIvaItem() {
        return montoIvaItem;
    }

    public void setMontoIvaItem(BigDecimal montoIvaItem) {
        this.montoIvaItem = montoIvaItem;
    }

    public String getTipoIvaItem() {
        if (tipoIvaItem == null || tipoIvaItem.isEmpty()) {
            tipoIvaItem = "Exento";
        }
        return tipoIvaItem;
    }

    public void setTipoIvaItem(String tipoIvaItem) {
        if (tipoIvaItem != null) {
            this.tipoIvaItem = tipoIvaItem;
            /*
            if (precioUnitario != null && !precioUnitario.equals(BigDecimal.ZERO)) {
                BigDecimal iva = BigDecimal.ZERO;
                BigDecimal ivaIncluido = BigDecimal.ZERO; //10% = 110, 5% = 105
                if (tipoIvaItem.equals("Exento")) {
                    iva = BigDecimal.ZERO;
                } else if (this.tipoIvaItem.equals("5%")) {
                    iva = BigDecimal.valueOf(5);
                } else if (this.tipoIvaItem.equals("10%")) {
                    iva = BigDecimal.valueOf(10);
                }

                ivaIncluido = iva.add(BigDecimal.valueOf(100));
                iva = precioUnitario.multiply(iva);
                iva = iva.divide(ivaIncluido, 2, RoundingMode.HALF_EVEN);
                this.montoIvaItem = iva;
            } */
        }
    }

    public List<String> getListaTiposIva() {
        if (listaTiposIva == null || listaTiposIva.isEmpty()) {
            listaTiposIva = new ArrayList<String>();
            listaTiposIva.add("Exento");
            listaTiposIva.add("5%");
            listaTiposIva.add("10%");
        }
        return listaTiposIva;
    }

    public List<FormaCobro> getFormaCobro() {
        try {
            listaFormaCobro = formaCobroEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Medio de Pago:" + ex.getMessage());
            return null;
        }
        return listaFormaCobro;
    }

    public FormaCobro getFormaCobroSeleccionado() {
        return formaCobroSeleccionado;
    }

    public void setFormaCobroSeleccionado(FormaCobro formaCobroSeleccionado) {
        if (formaCobroSeleccionado != null) {
            this.formaCobroSeleccionado = formaCobroSeleccionado;
        }
    }
}
