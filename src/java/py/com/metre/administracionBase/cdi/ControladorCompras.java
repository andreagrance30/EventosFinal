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
import py.com.metre.administracionBase.ejb.ComprasEJB;
import py.com.metre.administracionBase.ejb.DetalleComprasEJB;
import py.com.metre.administracionBase.ejb.ProveedorEJB;
import py.com.metre.administracionBase.ejb.FormaPagoEJB;
import py.com.metre.administracionBase.ejb.ProductoEJB;
import py.com.metre.administracionBase.ejb.CuentasPagarEJB;
import py.com.metre.administracionBase.jpa.Compras;
import py.com.metre.administracionBase.jpa.DetalleCompras;
import py.com.metre.administracionBase.jpa.Proveedor;
import py.com.metre.administracionBase.jpa.FormaPago;
import py.com.metre.administracionBase.jpa.Producto;
import py.com.metre.administracionBase.jpa.CuentasPagar;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author
 */
@Named
@SessionScoped
public class ControladorCompras implements Serializable {

    Logger logger = Logger.getLogger(Compras.class);
    @PersistenceContext
    protected EntityManager em;
    private @Inject
    LoginControlador loginControlador;
    @EJB
    private ComprasEJB comprasEJB;
    private Date fechaFactura;
    private DetalleCompras detalleCompraSeleccionado;
    private Proveedor proveedorSeleccionado;
    private FormaPago formaPagoSeleccionado;
    private Producto productoSeleccionado;
    private CuentasPagar cuentaSeleccionado;
    private List<DetalleCompras> detallesCompra;
    private List<Proveedor> listaProveedores;
    private List<FormaPago> listaFormaPago;
    private List<Producto> listaProducto;
    private List<CuentasPagar> listaCuentas;
    private BigDecimal cantidadItem;
    private BigDecimal montoTotalCompra;
    private String numeroFactura;

    @EJB
    private DetalleComprasEJB detalleComprasEJB;
    @EJB
    private ProveedorEJB proveedoresEJB;
    @EJB
    private FormaPagoEJB formaPagoEJB;
    @EJB
    private ProductoEJB productoEJB;
    @EJB
    private CuentasPagarEJB cuentasEJB;
    
    public ControladorCompras() {
        montoTotalCompra = BigDecimal.ZERO;
        detallesCompra = new ArrayList<DetalleCompras>();
        cantidadItem = BigDecimal.ZERO;
        fechaFactura = new Date();
    }

    public void limpiar() {
        montoTotalCompra = BigDecimal.ZERO;
        proveedorSeleccionado = null;
        formaPagoSeleccionado = null;
        detallesCompra = new ArrayList<DetalleCompras>();
        cantidadItem = BigDecimal.ZERO;
        fechaFactura = new Date();
        numeroFactura = null;
    }

    public void guardar() {
        System.out.println("TEST 0");
        if (!validar()) {
            return;
        }
        System.out.println("TEST 1");
        try {
            Compras compraNueva = new Compras();
            compraNueva.setFecha(fechaFactura);
            compraNueva.setProveedorid(proveedorSeleccionado);
            compraNueva.setUsuarioid(loginControlador.getUsuario());
            compraNueva.setFormaPagoid(formaPagoSeleccionado);
            compraNueva.setNroFactura(numeroFactura);
            compraNueva.setMontoTotal(montoTotalCompra);
            
            comprasEJB.insertar(compraNueva);
            
            CuentasPagar cuentas = new CuentasPagar();
            cuentas.setComprasid(compraNueva);
            cuentas.setDescripcion("");
            cuentas.setEstado("Pagado");
            cuentas.setFechaEmision(fechaFactura);
            cuentas.setNroFactura(numeroFactura);
            cuentas.setFechaVencimiento(fechaFactura);
            cuentas.setMonto(montoTotalCompra);
            cuentasEJB.insertar(cuentas);
            
            System.out.println("TEST 2");
            for (DetalleCompras item : detallesCompra) {
                item.setComprasid(compraNueva);
                detalleComprasEJB.insertar(item);
                // Actualizar producto existente del item comprado
                Producto stock = item.getProductoid();
                BigDecimal producto = stock.getCantidad();
                stock.setCantidad(producto.add(item.getCantidad())); 
                productoEJB.actualizar(stock);
            }
            
           
            
            System.out.println("TEST 3");
            limpiar();
            JsfUtil.agregarMensajeExito("La compra ha sido registrada con éxito.");
            System.out.println("TEST 4");
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorCampo("Compra", "No se pudo guardar el registro de compra.");
            logger.error(ex.getMessage());
        }
    }
     
    public void agregarItem() {

        if (productoSeleccionado == null || productoSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Stock", "Debe seleccionar un producto de la compra.");
            return;
        }

        if (this.cantidadItem == null) {
            JsfUtil.agregarMensajeErrorCampo("Cantidad", "Debe indicar la cantidad comprada.");
            return;
        }

        DetalleCompras item = new DetalleCompras();
        item.setProductoid(productoSeleccionado);
        item.setCantidad(cantidadItem);
        item.setCostoUnitario(productoSeleccionado.getPrecio());
        item.setCostoTotal(item.getCostoUnitario().multiply(cantidadItem));
        detallesCompra.add(item);
        montoTotalCompra = montoTotalCompra.add(item.getCostoTotal()); 
          productoSeleccionado = null;
         cantidadItem = null;
    }

    private boolean validar() {

        if (proveedorSeleccionado == null || proveedorSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Proveedor", "Debe seleccionar el proveedor de la compra.");
            return false;
        }

        if (formaPagoSeleccionado == null || formaPagoSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Medio de Pago", "Debe seleccionar el medio de Pago de la compra.");
            return false;
        }
        if (detallesCompra == null || detallesCompra.isEmpty()) {
            JsfUtil.agregarMensajeErrorCampo("Detalles", "Debe agregar por lo menos un ítem comprado.");
            return false;
        }
        return true;
    }

    public void eliminarDetalle(DetalleCompras detalle) {
        int index = 0;
        for (DetalleCompras dc : detallesCompra) {
            if (dc.getProductoid().getId().equals(detalle.getProductoid().getId())) {
                detallesCompra.remove(index);
//                montoTotalCompra = montoTotalCompra.subtract(detalle.getCostoTotal());
                break;
            }
            index++;
        }
        System.out.println("LENGTH: " + detallesCompra.size());
        List<DetalleCompras> detallesAux = detallesCompra;
        detallesCompra = new ArrayList<DetalleCompras>();
        for (DetalleCompras dc : detallesAux) {
            detallesCompra.add(dc);
        }
        System.out.println("SIZE: " + detallesCompra.size());
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
    
     public List<CuentasPagar> getListaCuentas() {
        try {
            listaCuentas = cuentasEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Cuentas:" + ex.getMessage());
            return null;
        }
        return listaCuentas;
    }

    public List<Proveedor> getProveedores() {
        try {
            listaProveedores = proveedoresEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Proveedor:" + ex.getMessage());
            return null;
        }
        return listaProveedores;
    }

    public List<FormaPago> getFormaPago() {
        try {
            listaFormaPago = formaPagoEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Medio de Pago:" + ex.getMessage());
            return null;
        }
        return listaFormaPago;
    }

    public List<DetalleCompras> getDetallesCompra() {
        return detallesCompra;
    }

    public DetalleCompras getDetalleCompraSeleccionado() {
        return detalleCompraSeleccionado;
    }

    public void setDetalleCompraSeleccionado(DetalleCompras detalleCompraSeleccionado) {
        if (detalleCompraSeleccionado != null) {
            this.detalleCompraSeleccionado = detalleCompraSeleccionado;
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

     public CuentasPagar getCuentaSeleccionado() {
        return cuentaSeleccionado;
    }

    public void setCuentaSeleccionado( CuentasPagar cuentaSeleccionado) {
        if (cuentaSeleccionado != null) {
            this.cuentaSeleccionado = cuentaSeleccionado;
        }
    }
    
    public BigDecimal getMontoTotalCompra() {
        return montoTotalCompra;
    }

    public void setMontoTotalCompra(BigDecimal montoTotalCompra) {
        if (montoTotalCompra != null) {
            this.montoTotalCompra = montoTotalCompra;
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

    public Proveedor getProveedorSeleccionado() {
        return proveedorSeleccionado;
    }

    public void setProveedorSeleccionado(Proveedor proveedorSeleccionado) {
        if (proveedorSeleccionado != null) {
            this.proveedorSeleccionado = proveedorSeleccionado;
        }
    }

    public FormaPago getFormaPagoSeleccionado() {
        return formaPagoSeleccionado;
    }

    public void setFormaPagoSeleccionado(FormaPago formaPagoSeleccionado) {
        if (formaPagoSeleccionado != null) {
            this.formaPagoSeleccionado = formaPagoSeleccionado;
        }
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        if (numeroFactura != null) {
            this.numeroFactura = numeroFactura;
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
