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
public class InventarioAjustes implements Serializable {
/*
    Logger logger = Logger.getLogger(AjustesInsumos.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private InsumosEJB insumosEJB;
    @EJB
    private AjustesInsumosEJB ajustesEJB;
    private Insumos insumoSeleccionado;
    private AjustesInsumos nuevoAjuste;
    private String tipoAjuste;
    private List<String> listaTiposAjustes;
    private List<AjustesInsumos> listaAjustes;
    private List<Insumos> listaInsumos;
    private
    @Inject
    LoginControlador loginControlador;

    public AjustesInsumos getNuevoAjuste() {
        if (nuevoAjuste == null) {
            this.nuevoAjuste = new AjustesInsumos();
            tipoAjuste = "Pérdida";
        }
        nuevoAjuste.setFecha(new Date());
        return this.nuevoAjuste;
    }

    public void setNuevoAjuste(AjustesInsumos ajuste) {
        if (ajuste != null) {
            this.nuevoAjuste = ajuste;
        }
    }

    public Insumos getInsumoSeleccionado() {
        if (insumoSeleccionado == null) {
            insumoSeleccionado = new Insumos();
            return insumoSeleccionado;
        }
        return insumoSeleccionado;
    }

    public void setInsumoSeleccionado(Insumos insumo) {
        if (insumo != null) {
            this.insumoSeleccionado = insumo;
        }
    }

    public List<Insumos> getListaInsumos() {
        listaInsumos = insumosEJB.listarTodos();
        return listaInsumos;
    }

    public List<AjustesInsumos> getListaAjustes() {
        listaAjustes = ajustesEJB.listarTodos();
        return listaAjustes;
    }

    public List<String> getListaTiposAjustes() {
        if(listaTiposAjustes == null || listaTiposAjustes.isEmpty()){
            listaTiposAjustes = new ArrayList<String>();
            listaTiposAjustes.add("Aumento");
            listaTiposAjustes.add("Pérdida");
        }
        return listaTiposAjustes;
    }

    public String getTipoAjuste() {
        return tipoAjuste;
    }

    public void setTipoAjuste(String tipoAjuste) {
        if(tipoAjuste != null && !tipoAjuste.isEmpty()){
            this.tipoAjuste = tipoAjuste;
        }
    }

    public void add() {
        try {
            if(!validar(nuevoAjuste)) { return; }
            nuevoAjuste.setInsumos(insumoSeleccionado);
            if(tipoAjuste.equals("Aumento")){
                nuevoAjuste.setTipoAjuste("+");
            } else if (tipoAjuste.equals("Pérdida")){
                nuevoAjuste.setTipoAjuste("-");
            }
            ajustesEJB.insertar(nuevoAjuste);
            actualizarStockInsumo();
            JsfUtil.agregarMensajeExito("Se ha actualizado el stock correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }

    private void actualizarStockInsumo() {
        BigDecimal stock = insumoSeleccionado.getStock();
        if (tipoAjuste.equals("Aumento")) {
            stock = stock.add(nuevoAjuste.getAjuste());
        } else if (tipoAjuste.equals("Pérdida")) {
            stock = stock.subtract(nuevoAjuste.getAjuste());
        }
        insumoSeleccionado.setStock(stock);
        insumosEJB.actualizar(insumoSeleccionado);
    }

    private boolean validar(AjustesInsumos ajuste){
        if (ajuste == null){
            JsfUtil.agregarMensajeErrorCampo("Ajuste", "No se pudo guardar.");
            return false;
        }
        if (insumoSeleccionado == null || insumoSeleccionado.getId() == null){
            JsfUtil.agregarMensajeErrorCampo("Insumo", "Debe seleccionar un insumo sobre el cual se efectúa el ajuste.");
            return false;
        }
        if(ajuste.getMotivo() == null || ajuste.getMotivo().isEmpty()){
            JsfUtil.agregarMensajeErrorCampo("Ajuste", "Debe indicar un motivo por el cual se está efectuando el ajuste.");
            return false;
        }
        if(ajuste.getAjuste() == null || ajuste.getAjuste().compareTo(BigDecimal.ZERO) <= 0){
            JsfUtil.agregarMensajeErrorCampo("Ajuste", "Ingrese una cantidad válida para el ajuste.");
            return false;
        }
        return true;
    }

    private void resetearCampos() {
        insumoSeleccionado = null;
        nuevoAjuste = null;
        tipoAjuste = "Pérdida";
    }
*/
}
