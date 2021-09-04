package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import py.com.metre.administracionBase.ejb.AperturaCierreCajaEJB;
import py.com.metre.administracionBase.ejb.DetalleAperturaCierreEJB;
import py.com.metre.administracionBase.ejb.UsuarioEJB;  /*
import py.com.metre.administracionBase.ejb.MedioPagoEJB;
import py.com.metre.administracionBase.jpa.AperturaCierreCabecera;
import py.com.metre.administracionBase.jpa.DetalleAperturaCierre;
import py.com.metre.administracionBase.jpa.Usuario;
import py.com.metre.administracionBase.jpa.MedioPago;
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author
 */
@Named
@SessionScoped
public class ControladorAperCierr implements Serializable {
/*
    Logger logger = Logger.getLogger(AperturaCierreCabecera.class);
    @PersistenceContext
    protected EntityManager em;
    private @Inject
    LoginControlador loginControlador;
    @EJB
    private AperturaCierreCabeceraEJB aperCierrCabEJB;
    private Date fecha;
    private DetalleAperturaCierre detalleAperCierrSeleccionado;
    private MedioPago medioPagoSeleccionado;
    private List<DetalleAperturaCierre> detalleAperCierr;
    private List<MedioPago> listaMedioPago;
    private BigDecimal montoApertura;
    private BigDecimal montoEntrada;
    private BigDecimal montoReal;
    private BigDecimal montoSalida;
    private BigDecimal montoFaltante;
    private BigDecimal montoCierre;
    private BigDecimal montoSistema;
    private Boolean cajaCerrada;
    AperturaCierreCabecera AperCierrNuevo = new AperturaCierreCabecera();
    @EJB
    private DetalleAperturaCierreEJB detalleAperCierrEJB;
    @EJB
    private MedioPagoEJB medioPagoEJB;

    public ControladorAperCierr() {
        detalleAperCierr = new ArrayList<DetalleAperturaCierre>();
        fecha = new Date();
        montoApertura=  BigDecimal.ZERO;
        montoEntrada=  BigDecimal.ZERO;
        montoReal=  BigDecimal.ZERO;
        montoSalida=  BigDecimal.ZERO;
         montoFaltante=  BigDecimal.ZERO;
        montoCierre=  BigDecimal.ZERO;
    }

    public void limpiar() {
 
        medioPagoSeleccionado = null;
        detalleAperCierr = new ArrayList<DetalleAperturaCierre>();
        fecha = new Date();
         montoApertura=  BigDecimal.ZERO;
        montoEntrada=  BigDecimal.ZERO;
        montoReal=  BigDecimal.ZERO;
        montoSalida=  BigDecimal.ZERO;
         montoFaltante=  BigDecimal.ZERO;
        montoCierre=  BigDecimal.ZERO;
    }

    public void guardar() {
        System.out.println("TEST 0");
        if (!validar()) {
            return;
        }
        System.out.println("TEST 1");
        try {
            AperCierrNuevo.setFecha(fecha);
           AperCierrNuevo.setCajaCerrada(cajaCerrada);
            AperCierrNuevo.setIdUsuario(loginControlador.getUsuario());
            aperCierrCabEJB.insertar(AperCierrNuevo);
            System.out.println("TEST 2");
            for (DetalleAperturaCierre item : detalleAperCierr) {
                item.setIdAperturaCierre(AperCierrNuevo);
                detalleAperCierrEJB.insertar(item);
            }
            System.out.println("TEST 3");
            limpiar();
            JsfUtil.agregarMensajeExito("La Apertura Cierre ha sido registrada con éxito.");
            System.out.println("TEST 4");
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorCampo("Cierre", "No se pudo guardar el registro del Cierre.");
            logger.error(ex.getMessage());
        }
    }
     public void calcular() {
            AperCierrNuevo.setMontoApertura(montoApertura);
            AperCierrNuevo.setMontoEntrada(montoEntrada);
            montoSistema= AperCierrNuevo.getMontoApertura().add(AperCierrNuevo.getMontoApertura()).subtract(AperCierrNuevo.getMontoSalida());
            AperCierrNuevo.setMontoReal(montoReal);
            AperCierrNuevo.setMontoSalida(montoSalida);         
            montoFaltante= AperCierrNuevo.getMontoApertura().add(AperCierrNuevo.getMontoEntrada()).subtract(AperCierrNuevo.getMontoSalida()).subtract(montoReal);
            AperCierrNuevo.setMontoFaltante(montoFaltante);
     }

    public void agregarItem() {
      
        if (this.montoCierre == null) {
            JsfUtil.agregarMensajeErrorCampo("Monto Cierre", "Debe indicar el monto de Cierre.");
            return;
        }
        if (this.montoCierre.compareTo(BigDecimal.ZERO) <= 0) {
            JsfUtil.agregarMensajeErrorCampo("Monto Cierre", "Ingrese un valor de cierre válido.");
            return;
        } 
      
       if (this.medioPagoSeleccionado == null || this.medioPagoSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Item", "Debe seleccionar un medio de pago para agregar a la lista de Cierre.");
            return;
        }
       
        DetalleAperturaCierre item = new DetalleAperturaCierre();
        item.setMontoCierre(montoCierre);
        item.setIdMedioPago(medioPagoSeleccionado);
        montoEntrada= montoEntrada.add(item.getMontoCierre());
        detalleAperCierr.add(item);
    }
        
    private boolean validar() {
        
        if (medioPagoSeleccionado == null || medioPagoSeleccionado.getId() == null) {
            JsfUtil.agregarMensajeErrorCampo("Medio de Pago", "Debe seleccionar el medio de Pago del cobro.");
            return false;
        }
        if (detalleAperCierr == null || detalleAperCierr.isEmpty()) {
            JsfUtil.agregarMensajeErrorCampo("Detalles", "Debe agregar por lo menos un ítem de cierre.");
            return false;
        }
        return true;
    }

    public void eliminarDetalle(DetalleAperturaCierre detalle) {
        int index = 0;
        for (DetalleAperturaCierre dc : detalleAperCierr) {
            if (dc.getId()!= null) {
                detalleAperCierr.remove(index);
                montoCierre = montoCierre.subtract(detalle.getMontoCierre());
                break;
            }
            index++; 
        }
        System.out.println("LENGTH: " + detalleAperCierr.size());
        List<DetalleAperturaCierre> detallesAux = detalleAperCierr;
        detalleAperCierr = new ArrayList<DetalleAperturaCierre>();
        for (DetalleAperturaCierre dc : detallesAux) {
            detalleAperCierr.add(dc);
        }
        System.out.println("SIZE: " + detalleAperCierr.size());
    }  
   
    public List<MedioPago> getListaMedioPago() {
        try {
            listaMedioPago = medioPagoEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Usuario:" + ex.getMessage());
            return null;
        }
        return listaMedioPago;
    }

    public List<DetalleAperturaCierre> getDetalleAperCierr() {
        return detalleAperCierr;
    }

    public DetalleAperturaCierre getDetalleAperCierrSeleccionado() {
        return detalleAperCierrSeleccionado;
    }

    public void setDetalleAperCierrSeleccionado(DetalleAperturaCierre detalleAperCierrSeleccionado) {
        if (detalleAperCierrSeleccionado != null) {
            this.detalleAperCierrSeleccionado = detalleAperCierrSeleccionado;
        }
    }
    
       public MedioPago getMedioPagoSeleccionado() {
        return medioPagoSeleccionado;
    }

    public void setMedioPagoSeleccionado(MedioPago medioPagoSeleccionado) {
        if (medioPagoSeleccionado != null) {
            this.medioPagoSeleccionado = medioPagoSeleccionado;
        }
    }
    
    public void actualizarFecha(DateSelectEvent event) {
        fecha = event.getDate();
    }
    
    public String getFechaHoy() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = sdf.format(new Date());
        return fecha;
    }
    
     public Date getFecha() {
        if (fecha == null) {
            fecha = new Date();
        }
        return fecha;
    }

    public void setFecha(Date fecha) {
        if (fecha != null) {
            this.fecha = fecha;
        }
    }
    
    public BigDecimal getMontoApertura() {
        return montoApertura;
    }

    public void setMontoApertura(BigDecimal montoApertura) {
             if (montoApertura != null) {
            this.montoApertura = montoApertura;
    }
   } 
    public Boolean getEstado() {
        return cajaCerrada;
    }

    public void setEstado(Boolean cajaCerrada) {
        this.cajaCerrada = cajaCerrada;
    }

    public BigDecimal getMontoEntrada() {
        return montoEntrada;
    }

    public void setMontoEntrada(BigDecimal montoEntrada) {
        this.montoEntrada = montoEntrada;
    }

    public BigDecimal getMontoReal() {
        return montoReal;
    }

    public void setMontoReal(BigDecimal montoReal) {
        this.montoReal = montoReal;
    }

    public BigDecimal getMontoSalida() {
        return montoSalida;
    }

    public void setMontoSalida(BigDecimal montoSalida) {
        this.montoSalida = montoSalida;
    }

    public BigDecimal getMontoFaltante() {
        return montoFaltante;
    }

    public void setMontoFaltante(BigDecimal montoFaltante) {
        this.montoFaltante = montoFaltante;
    }

    public BigDecimal getMontoCierre() {
        return montoCierre;
    }

    public void setMontoCierre(BigDecimal montoCierre) {
        this.montoCierre = montoCierre;
    }

    public BigDecimal getMontoSistema() {
        return montoSistema;
    }

    public void setMontoSistema(BigDecimal montoSistema) {
        this.montoSistema = montoSistema;
    }
  */  
}
