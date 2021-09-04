package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * 
 */
@Named
@SessionScoped
public class AdministracionPrecios implements Serializable {
/*
    Logger logger = Logger.getLogger(Item.class);
    @PersistenceContext
    protected EntityManager em;
    @EJB
    private ItemsEJB itemsEJB;
    private Item itemSeleccionado;
    private Item itemNuevo;
    private List<Item> listaItems;
    private
    @Inject
    LoginControlador loginControlador;

    public Item getItemNuevo() {
        if (itemNuevo == null) {
            this.itemNuevo = new Item();
        }
        return this.itemNuevo;
    }

    public void setItemNuevo(Item insumo) {
        if (insumo != null) {
            this.itemNuevo = insumo;
        }
    }

    public Item getItemSeleccionado() {
        if (itemSeleccionado == null) {
            itemSeleccionado = new Item();
            return itemSeleccionado;
        }
        return itemSeleccionado;
    }

    public void setItemSeleccionado(Item insumo) {
        if (itemSeleccionado != null) {
            this.itemSeleccionado = insumo;
        }
    }

    public List<Item> getListaItems() {
        listaItems = itemsEJB.listarTodos();
        return listaItems;
    }

    public void setListaItems(List<Item> listaItems) {
        this.listaItems = listaItems;
    }

    public void update() {
        try {
            if (this.itemSeleccionado != null) {
                itemsEJB.actualizar(itemSeleccionado);
                JsfUtil.agregarMensajeExito("Se ha actualizado correctamente.");
                resetearCampos();
            }
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "No se pudo actualizar: " + ex);
            resetearCampos();
        }
    }

    public void add() {
        try {
            itemsEJB.insertar(itemNuevo);
            JsfUtil.agregarMensajeExito("Se ha guardado correctamente.");
            resetearCampos();
        } catch (Exception ex) {
            JsfUtil.agregarMensajeErrorGlobal(ex, "Error al intentar guardar: " + ex);
            resetearCampos();
        }
    }

    public void delete() {
        try {
            if (this.itemSeleccionado != null) {
                itemsEJB.eliminar(itemSeleccionado);
                JsfUtil.agregarMensajeExito("El registro fue eliminado correctamente.");
            }
        } catch (Exception e) {
            JsfUtil.agregarMensajeErrorGlobal(e, "Ocurrio un error al eliminar:" + e);
        }
    }

    private void resetearCampos() {
        itemSeleccionado = null;
        itemNuevo = null;
    }
*/
}
