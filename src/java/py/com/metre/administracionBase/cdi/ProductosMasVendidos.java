package py.com.metre.administracionBase.cdi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.primefaces.event.DateSelectEvent;

/**
 *
 * @author Adolfo
 */
@Named
@SessionScoped
public class ProductosMasVendidos implements Serializable {

    Logger logger = Logger.getLogger(ProductosMasVendidos.class);
    @PersistenceContext
    protected EntityManager em;
    private
    @Inject
    LoginControlador loginControlador;

    public class Producto {

        private String nombre;
        private String cantidad;

        public String getCantidad() {
            return cantidad;
        }

        public void setCantidad(String cantidad) {
            this.cantidad = cantidad;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    };
    
    private List<Producto> rankingProductos;
    private List<String> categorias;
    private Date fechaInicial;
    private Date fechaFinal;
    private String catSeleccionada;

    public ProductosMasVendidos() {
        catSeleccionada = "Todas";
        categorias = new ArrayList<String>();
        categorias.add("Todas");
        categorias.add("Bases");
        categorias.add("Tipos de Panes");
        categorias.add("Tipos de Carnes");
        categorias.add("Toppings");
        categorias.add("Salsas");
        categorias.add("Otros adicionales steaks");
        categorias.add("Otros adicionales hamburguesas");
        categorias.add("Adicionales papas fritas");
        categorias.add("Combos");
        categorias.add("Empanadas");
        categorias.add("Bebidas");
    }

    public void actualizarResultado() {
    }

    public void setPeriodoSemana() {
        Calendar hoy = Calendar.getInstance();
        Calendar fechaIni = Calendar.getInstance();
        // 604800000 = Cantidad de milisegundos en una semana
        fechaIni.setTimeInMillis(hoy.getTimeInMillis() - 604800000);
        setFechaInicial(fechaIni.getTime());
        setFechaFinal(hoy.getTime());
    }

    public void setPeriodoMes() {
        Calendar fechaIni = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        fechaIni.set(Calendar.DAY_OF_MONTH, fechaIni.getActualMinimum(Calendar.DAY_OF_MONTH));
        fechaFin.set(Calendar.DAY_OF_MONTH, fechaIni.getActualMaximum(Calendar.DAY_OF_MONTH));
        setFechaInicial(fechaIni.getTime());
        setFechaFinal(fechaFin.getTime());
    }

    public void setPeriodoAnho() {
        Calendar fechaIni = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        fechaIni.set(Calendar.MONTH, fechaIni.getActualMinimum(Calendar.MONTH));
        fechaIni.set(Calendar.DAY_OF_MONTH, fechaIni.getActualMinimum(Calendar.DAY_OF_MONTH));
        fechaFin.set(Calendar.MONTH, fechaIni.getActualMaximum(Calendar.MONTH));
        fechaFin.set(Calendar.DAY_OF_MONTH, fechaIni.getActualMaximum(Calendar.DAY_OF_MONTH));
        setFechaInicial(fechaIni.getTime());
        setFechaFinal(fechaFin.getTime());
    }

    public List<Producto> getRankingProductos() {
        rankingProductos = new ArrayList<Producto>();
        try {
            Query q = em.createNativeQuery(consultaProductos());
            List<Object[]> resultado = q.getResultList();
            for (Object[] registro : resultado) {
                Producto item = new Producto();
                item.setNombre(registro[0].toString());
                item.setCantidad(registro[1].toString());
                rankingProductos.add(item);
            }
        } catch (Exception ex) {
            System.out.println("Ranking Productos:" + ex.getMessage());
        }
        return rankingProductos;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    private String consultaProductos() {
        String idPedidoInicial = getIdPedidoInicial();
        String idPedidoFinal = getIdPedidoFinal();
        String query = "SELECT i.nombre as NOMBRE, count (i.id_item) as CANTIDAD "
                + "FROM item i, detalle_pedido dp, pedido p, categoria c "
                + "WHERE dp.id_item = i.id_item "
                + "AND dp.id_pedido = p.id_pedido "
                + "AND dp.cancelado = false "
                + "AND i.id_categoria = c.id_categoria ";
                if(!catSeleccionada.equals("Todas")){
                    query += "AND c.nombre IN " + categoriasAgrupadas() + " ";
                }
                if(!idPedidoInicial.isEmpty()){
                    query += " AND dp.id_pedido >= " + idPedidoInicial;
                }
                if(!idPedidoFinal.isEmpty()){
                    query += " AND dp.id_pedido <= " + idPedidoFinal;
                }
                query += "GROUP by i.nombre "
                + "ORDER BY CANTIDAD desc";
        System.out.println("Query: " + query);
        return query;

    }

    /*
     * Por cuestiones de comodidad se agruparon las categorías de la BD en algunas categorías visibles para el usuario.
     * Este método retorna una lista de nombres por grupo para hacer una condicion IN en la consulta SQL
     */
    private String categoriasAgrupadas(){
        String result = "";
        if(catSeleccionada.equals("Bases")){
            result += "('Bases')";
        }
        if(catSeleccionada.equals("Tipos de Panes")){
            result += "('TipoPan')";
        }
        if(catSeleccionada.equals("Tipos de Carnes")){
            result += "('Carnes')";
        }
        if(catSeleccionada.equals("Toppings")){
            result += "('Toppings')";
        }
        if(catSeleccionada.equals("Salsas")){
            result += "('Salsas')";
        }
        if(catSeleccionada.equals("Otros adicionales steaks")){
            result += "('AdicionalesSteak')";
        }
        if(catSeleccionada.equals("Otros adicionales hamburguesas")){
            result += "('AdicionalesHamburguesa')";
        }
        if(catSeleccionada.equals("Adicionales papas fritas")){
            result += "('AdicionalesPapasFritas')";
        }
        if(catSeleccionada.equals("Combos")){
            result += "('Combos')";
        }
        if(catSeleccionada.equals("Empanadas")){
            result += "('Empanadas')";
        }
        if(catSeleccionada.equals("Bebidas")){
            result += "('Bebidas')";
        }
        return result;
    }

    /*
     * Tomamos el id del primer pedido de la fecha de inicio
     */
    private String getIdPedidoInicial(){
        String result = "";
        BigInteger primerIdPM, primerIdAM;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 86400000 milisegundos = 1 dia
        long tiempoDia = getFechaInicial().getTime() + 86400000;
        Date fechaSiguiente = new Date();
        fechaSiguiente.setTime(tiempoDia);
	try {
            Query queryPM = em.createNativeQuery("SELECT MIN(id_pedido) FROM pedido WHERE fecha >= '" + sdf.format(getFechaInicial()) + "' AND hora > '10:00'");
            Object resultadoPM = queryPM.getSingleResult();
            em.clear();
            Query queryAM = em.createNativeQuery("SELECT MIN(id_pedido) FROM pedido WHERE fecha >= '" + sdf.format(fechaSiguiente) + "' ");
            Object resultadoAM = queryAM.getSingleResult();
            em.clear();
            if(resultadoAM != null && resultadoPM != null){
                primerIdAM = new BigInteger(resultadoAM.toString());
                primerIdPM = new BigInteger(resultadoPM.toString());
                if(primerIdAM.compareTo(primerIdPM) < 0){
                    result = primerIdAM.toString();
                } else {
                    result = primerIdPM.toString();
                }
            }
            if(resultadoAM == null && resultadoPM != null){
                result = resultadoPM.toString();
            }
            if(resultadoPM == null && resultadoAM != null){
                result = resultadoAM.toString();
            }
            if(resultadoAM == null && resultadoPM == null){
                Query max = em.createNativeQuery("SELECT MAX(id_pedido) FROM pedido");
                BigDecimal id = (BigDecimal) max.getSingleResult();
                id = id.add(BigDecimal.ONE);
                result = id.toString();
            }
        } catch (Exception ex) {
            System.out.println("Calculo Fecha Inicio: " + ex.getMessage());
        }
        return result;
    }

    /*
     * Tomamos el id del ultimo pedido de la fecha de fin
     */
    private String getIdPedidoFinal(){
        String result = "";
        BigInteger ultimoIdPM, ultimoIdAM;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 86400000 milisegundos en un dia
        long tiempoDia = getFechaFinal().getTime() + 86400000;
        Date fechaSiguiente = new Date();
        fechaSiguiente.setTime(tiempoDia);

        try {
            Query queryAM = em.createNativeQuery("SELECT MAX(id_pedido) FROM pedido WHERE fecha <= '" + sdf.format(fechaSiguiente) + "' AND hora < '10:00'");
            Object resultadoAM = queryAM.getSingleResult();
            em.clear();
            Query queryPM = em.createNativeQuery("SELECT MAX(id_pedido) FROM pedido WHERE fecha <= '" + sdf.format(getFechaFinal()) + "' ");
            Object resultadoPM = queryPM.getSingleResult();
            em.clear();
            if(resultadoAM != null && resultadoPM != null){
                ultimoIdAM = new BigInteger(resultadoAM.toString());
                ultimoIdPM = new BigInteger(resultadoPM.toString());
                if(ultimoIdAM.compareTo(ultimoIdPM) > 0){
                    result = ultimoIdAM.toString();
                } else {
                    result = ultimoIdPM.toString();
                }
            }
            if(resultadoAM == null && resultadoPM != null){
                result = resultadoPM.toString();
            }
            if(resultadoPM == null && resultadoAM != null){
                result = resultadoAM.toString();
            }
            if(resultadoAM == null && resultadoPM == null){
                result = "0";
            }
        } catch (Exception ex) {
            System.out.println("Calculo Fecha Fin: " + ex.getMessage());
        } finally {
            return result;
        }
    }

    public Date getFechaFinal() {
        if(fechaFinal == null){
            Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), 11, 31);
            fechaFinal = cal.getTime();
        }
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        if(fechaFinal != null){
            this.fechaFinal = fechaFinal;
        }
    }

    public Date getFechaInicial() {
        if(fechaInicial == null){
            Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), 0, 1);
            fechaInicial = cal.getTime();
        }
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        if(fechaInicial != null){
            this.fechaInicial = fechaInicial;
        }
    }

    public void actualizarFechaInicio(DateSelectEvent event) {
        fechaInicial = event.getDate();
    }

    public void actualizarFechaFin(DateSelectEvent event) {
        fechaFinal = event.getDate();
    }

    public String getCatSeleccionada() {
        return catSeleccionada;
    }

    public void setCatSeleccionada(String catSeleccionada) {
        this.catSeleccionada = catSeleccionada;
    }
   
}
