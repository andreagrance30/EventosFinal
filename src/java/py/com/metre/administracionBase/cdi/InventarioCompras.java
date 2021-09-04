package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author 
 */
@Named
@SessionScoped
public class InventarioCompras implements Serializable {
/*
    Logger logger = Logger.getLogger(InventarioCompras.class);
    @PersistenceContext
    protected EntityManager em;
    private
    @Inject
    LoginControlador loginControlador;
    private Date fechaFactura;
    private DetalleCompra detalleCompraSeleccionado;
    private Insumos insumoSeleccionado;
    private Proveedores proveedorSeleccionado;
    private List<DetalleCompra> detallesCompra;
    private List<Insumos> listaInsumos;
    private List<Proveedores> listaProveedores;
    private BigDecimal cantidadItem;
    private BigInteger costoItem;
    private String tipoIvaItem; // Exento (0), 5% (5), 10% (10)
    private BigInteger montoIvaItem; // Monto deducido del costo y el tipo de IVA
    private BigInteger montoTotalCompra;
    private String numeroFactura;
    private List<String> listaTiposIva;
    @EJB
    private ComprasEJB comprasEJB;
    @EJB
    private DetalleComprasEJB detalleComprasEJB;
    @EJB
    private InsumosEJB insumosEJB;
    @EJB
    private ProveedoresEJB proveedoresEJB;

    public InventarioCompras() {
        montoTotalCompra = BigInteger.ZERO;
        detallesCompra = new ArrayList<DetalleCompra>();
        cantidadItem = BigDecimal.ZERO;
        costoItem = BigInteger.ZERO;
        tipoIvaItem = "Exento";
        montoIvaItem = BigInteger.ZERO;
        fechaFactura = new Date();
    }

    public void limpiar() {
        montoTotalCompra = BigInteger.ZERO;
        proveedorSeleccionado = null;
        insumoSeleccionado = null;
        detallesCompra = new ArrayList<DetalleCompra>();
        cantidadItem = BigDecimal.ZERO;
        costoItem = BigInteger.ZERO;
        tipoIvaItem = "Exento";
        montoIvaItem = BigInteger.ZERO;
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
            compraNueva.setProveedores(proveedorSeleccionado);
            compraNueva.setNroFactura(numeroFactura);
            comprasEJB.insertar(compraNueva);
            System.out.println("TEST 2");
            for (DetalleCompra item : detallesCompra) {
                item.setCompras(compraNueva);
                detalleComprasEJB.insertar(item);
                // Actualizar stock existente del item comprado
                Insumos insumo = item.getInsumos();
                BigDecimal stock = insumo.getStock();
                insumo.setStock(stock.add(item.getCantidad()));
                insumosEJB.actualizar(insumo);
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
        if (this.insumoSeleccionado == null || this.insumoSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Item", "Debe seleccionar un insumo para agregar a la lista de compra.");
            return;
        }
        if (this.costoItem == null) {
            JsfUtil.agregarMensajeErrorCampo("Costo", "Debe indicar el costo del ítem.");
            return;
        }
        if (this.costoItem.compareTo(BigInteger.ZERO) <= 0) {
            JsfUtil.agregarMensajeErrorCampo("Costo", "Ingrese un valor de costo válido.");
            return;
        }
        if (this.cantidadItem == null) {
            JsfUtil.agregarMensajeErrorCampo("Cantidad", "Debe indicar la cantidad comprada.");
            return;
        }
        if (this.cantidadItem.compareTo(BigDecimal.ZERO) <= 0) {
            JsfUtil.agregarMensajeErrorCampo("Cantidad", "Ingrese una cantidad válida.");
            return;
        }
        DetalleCompra item = new DetalleCompra();
        item.setInsumos(insumoSeleccionado);
        item.setCantidad(cantidadItem);
        item.setPrecio(costoItem);
        if (this.tipoIvaItem.equals("Exento")) {
            item.setTipoIva(BigInteger.ZERO);
        } else if (this.tipoIvaItem.equals("5%")) {
            item.setTipoIva(BigInteger.valueOf(5));
        } else if (this.tipoIvaItem.equals("10%")) {
            item.setTipoIva(BigInteger.valueOf(10));
        }
        BigInteger IVA = item.getPrecio().multiply(item.getTipoIva());
        IVA = IVA.divide(BigInteger.valueOf(100).add(item.getTipoIva()));
        item.setMontoIva(IVA);
        detallesCompra.add(item);
        montoTotalCompra = montoTotalCompra.add(item.getPrecio());
        insumoSeleccionado = null;
        cantidadItem = null;
        costoItem = null;
        tipoIvaItem = "Exento";
    }

    private boolean validar() {
        if (proveedorSeleccionado == null || proveedorSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Proveedor", "Debe seleccionar el proveedor de la compra.");
            return false;
        }
        if (detallesCompra == null || detallesCompra.isEmpty()) {
            JsfUtil.agregarMensajeErrorCampo("Detalles", "Debe agregar por lo menos un ítem comprado.");
            return false;
        }
        return true;
    }

    public void eliminarDetalle(DetalleCompra detalle) {
        int index = 0;
        for (DetalleCompra dc : detallesCompra) {
            if (dc.getInsumos().getId().equals(detalle.getInsumos().getId())) {
                detallesCompra.remove(index);
                montoTotalCompra = montoTotalCompra.subtract(detalle.getPrecio());
                break;
            }
            index++;
        }
        System.out.println("LENGTH: " + detallesCompra.size());
        List<DetalleCompra> detallesAux = detallesCompra;
        detallesCompra = new ArrayList<DetalleCompra>();
        for (DetalleCompra dc : detallesAux) {
            detallesCompra.add(dc);
        }
        System.out.println("SIZE: " + detallesCompra.size());
    }

    public List<Insumos> getInsumos() {
        try {
            listaInsumos = insumosEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Insumos:" + ex.getMessage());
            return null;
        }
        return listaInsumos;
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

    public List<DetalleCompra> getDetallesCompra() {
        return detallesCompra;
    }

    public DetalleCompra getDetalleCompraSeleccionado() {
        return detalleCompraSeleccionado;
    }

    public void setDetalleCompraSeleccionado(DetalleCompra detalleCompraSeleccionado) {
        if (detalleCompraSeleccionado != null) {
            this.detalleCompraSeleccionado = detalleCompraSeleccionado;
        }
    }

    public Insumos getInsumoSeleccionado() {
        return insumoSeleccionado;
    }

    public void setInsumoSeleccionado(Insumos insumoSeleccionado) {
        if (insumoSeleccionado != null) {
            this.insumoSeleccionado = insumoSeleccionado;
        }
    }

    public BigInteger getMontoTotalCompra() {
        return montoTotalCompra;
    }

    public void setMontoTotalCompra(BigInteger montoTotalCompra) {
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

    public BigInteger getCostoItem() {
        return costoItem;
    }

    public void setCostoItem(BigInteger costoItem) {
        if (costoItem != null) {
            this.costoItem = costoItem;
        }
    }

    public Proveedores getProveedorSeleccionado() {
        return proveedorSeleccionado;
    }

    public void setProveedorSeleccionado(Proveedores proveedorSeleccionado) {
        if (proveedorSeleccionado != null) {
            this.proveedorSeleccionado = proveedorSeleccionado;
        }
    }

    public BigInteger getMontoIvaItem() {
        return montoIvaItem;
    }

    public void setMontoIvaItem(BigInteger montoIvaItem) {
        this.montoIvaItem = montoIvaItem;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        if (numeroFactura != null) {
            this.numeroFactura = numeroFactura;
        }
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
            if (costoItem != null && !costoItem.equals(BigInteger.ZERO)) {
                BigInteger iva = BigInteger.ZERO;
                BigInteger ivaIncluido = BigInteger.ZERO; //10% = 110, 5% = 105
                if (tipoIvaItem.equals("Exento")) {
                    iva = BigInteger.ZERO;
                } else if (this.tipoIvaItem.equals("5%")) {
                    iva = BigInteger.valueOf(5);
                } else if (this.tipoIvaItem.equals("10%")) {
                    iva = BigInteger.valueOf(10);
                }
                ivaIncluido = iva.add(BigInteger.valueOf(100));
                iva = costoItem.multiply(iva);
                iva = iva.divide(ivaIncluido);
                this.montoIvaItem = iva;
            }
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

    public Date getFechaFactura() {
    if(fechaFactura == null){
            fechaFactura = new Date();
        }
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        if(fechaFactura != null){
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
*/
}
