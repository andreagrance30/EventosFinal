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
import javax.persistence.Query;
import org.apache.log4j.Logger;
import py.com.metre.administracionBase.ejb.ClienteEJB;
import py.com.metre.administracionBase.ejb.VentasEJB;
import py.com.metre.administracionBase.ejb.DetalleCobrosEJB; 
import py.com.metre.administracionBase.ejb.FormaCobroEJB;
import py.com.metre.administracionBase.jpa.Cliente;
import py.com.metre.administracionBase.jpa.Cobros;
import py.com.metre.administracionBase.jpa.Ventas;
import py.com.metre.administracionBase.jpa.DetalleCobros;
import py.com.metre.administracionBase.jpa.FormaCobro;  
import py.com.metre.administracionBase.jsf.JsfUtil;

/**
 *
 * @author
 */
@Named
@SessionScoped
public class ControladorCobros implements Serializable {

    Logger logger = Logger.getLogger(Cobros.class);
    @PersistenceContext
    protected EntityManager em;
    protected EntityManager am;
    private @Inject
            
    LoginControlador loginControlador;
   
    private Cliente clienteSeleccionado;
    private List<Cliente> listaCliente;
    private Ventas ventaSeleccionado;
    private List<Ventas> listaVentas;
    private List<DetalleCobros> listaCobros;
    private List<Cobros> listaCabecera;
    private DetalleCobros cobroSeleccionado;
    private FormaCobro formaCobroSeleccionado;
    private List<FormaCobro> listaFormaCobro;
    private Boolean estaPagado;
    
    @EJB
    private DetalleCobrosEJB detalleCobroEJB;
    @EJB
    private FormaCobroEJB formaCobroEJB;
    @EJB
    private ClienteEJB clienteEJB;
     @EJB
    private VentasEJB ventasEJB;
   
    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) { /*Lo que traemos aca es el objeto cliente, ydentro de el tenemos todos atributos como id nombre ci */
        if(clienteSeleccionado != null ){
        this.clienteSeleccionado = clienteSeleccionado; 
       /*
         Query query = em.createNativeQuery(consultarCobros(),DetalleCobros.class);
          listaCobros = new ArrayList<DetalleCobros>();
         listaCobros = (List<DetalleCobros>) query.getResultList();*/
         
          Query query = em.createNativeQuery(consultarVentas(),Ventas.class);
          listaVentas = new ArrayList<Ventas>();
          listaVentas = (List<Ventas>) query.getResultList();
         
                  
          }
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
 
 
  public Ventas getVentasSeleccionado() {
        return ventaSeleccionado;
    }

    public void setVentasSeleccionado(Ventas ventaSeleccionado) { /*Lo que traemos aca es el objeto cliente, ydentro de el tenemos todos atributos como id nombre ci */
        if(ventaSeleccionado != null ){
        this.ventaSeleccionado = ventaSeleccionado; 
        
          }
    }
/*
 public List<Ventas> getListaVentas() {
        try {
            listaVentas = ventasEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Ventas:" + ex.getMessage());
            return null;
        }
        return listaVentas;
    }   */
      public void limpiar(){
      
      clienteSeleccionado= null;
      cobroSeleccionado= null;
      formaCobroSeleccionado= null;
      }
      
      
      private String consultarCobros() {
        String query = "select cd.*, cc.*"
              +" from ventas v, cobros cc, detalle_cobros cd"
               +" where cd.cobrosid = cc.id"
               +" and cc.ventasid = v.id"
               + " and v.clienteid = " + this.clienteSeleccionado.getId() ;  /*le especificamos uno de sus atributos que seria el id */
     return query;        
    }  

      
       private String consultarVentas() {
        String query = "select v.*"
              +" from ventas v, cliente cli"
               +" where cli.id= v.clienteid"
               + " and cli.id= " + this.clienteSeleccionado.getId() ;  /*le especificamos uno de sus atributos que seria el id */
     return query;        
    }  
       
    public void setListaCobros(List<DetalleCobros> listaCobros) {
        if(listaCobros!= null){
        this.listaCobros = listaCobros;
    }
    }  
    
    public List<DetalleCobros> getListaCobros() {
        return listaCobros;
    }
    
    public List<Cobros> getListaCabecera() {
        return listaCabecera;
    }

    public void setListaCabecera(List<Cobros> listaCabecera) {
        if(listaCabecera!= null){
        this.listaCabecera = listaCabecera;
    }
    }  

    public DetalleCobros getCobroSeleccionado() {
        return cobroSeleccionado;
    }

    public void setCobroSeleccionado(DetalleCobros cobroSeleccionado) {
        if(cobroSeleccionado != null){
        this.cobroSeleccionado = cobroSeleccionado;
        if (cobroSeleccionado.getEstado().equals("pagado")){
            estaPagado= true;
            }else if (cobroSeleccionado.getEstado().equals("pendiente")){
                estaPagado= false;
            }
        }
    }
    
     public List<FormaCobro> getListaFormaCobro() {
        try {
            listaFormaCobro = formaCobroEJB.listarTodos();
        } catch (Exception ex) {
            System.out.println("Error en carga de Listado de Forma de Cobro:" + ex.getMessage());
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
            if (cobroSeleccionado!= null){
            this.cobroSeleccionado.setFormaCobroid(formaCobroSeleccionado);
            
            
            }
        }
    }
     
      public void update() {
        try {
            if (this.cobroSeleccionado != null) {
                detalleCobroEJB.actualizar(cobroSeleccionado);
                JsfUtil.agregarMensajeExito("Se ha actualizado correctamente.");
            }
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "No se pudo actualizar: " + ex);
        }
    } 
      
    public Boolean getEstaPagado() {
        return estaPagado;
    }

    public void setEstaPagado(Boolean estaPagado) {
        this.estaPagado = estaPagado;
    }

    public List<Ventas> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(List<Ventas> listaVentas) {
        this.listaVentas = listaVentas;
    }
    
    
    
} 