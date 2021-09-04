
package py.com.metre.administracionBase.util;

import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;

public class ImprimirReporte extends Reporte implements Serializable {

    Logger logger = Logger.getLogger(ImprimirReporte.class);
    private String nombreReporte;
    private String nombreReporteSinJasper;
    private Map<String, Object> parametros;
    private FacesContext faces;
    private HttpServletResponse response;
    BaseLecturaArchivos archivoConfig;
    SimpleDateFormat sdf;
//    private String imagenPath;
    private String nombreImagen;
    Connection conn;

    public ImprimirReporte() {
        nombreReporte = "PonerNombreReporte";
        parametros = new HashMap<String, Object>();
        sdf = new SimpleDateFormat("dd/MM/yyyy-hh:mm");

        try {
            archivoConfig = new BaseLecturaArchivos();
            archivoConfig.setNombreArchivo(ApplicationConstant.ARCHIVO_PARAMETROS_GENERALES);
            nombreImagen = archivoConfig.getPropiedad(ApplicationConstant.NOMBRELOGO).toString();
        } catch (FileNotFoundException ex) {
            logger.error("Error al intentar leer el archivo", ex);
        } catch (IOException ex) {
            logger.error("Error de accesso al archivo", ex);
        }
    }

    @Override
    public void imprimir() {
        String path = "";
        String imagenPath = "";
        String separator = File.separator;
        try {
            if (separator.equals(ApplicationConstant.SEPARADOR_WINDOWS)) {
                path = ApplicationConstant.CARPETA_REPORTES_WINDOWS;
                imagenPath = ApplicationConstant.CARPETA_WINDOWS;
            } else {
                path = ApplicationConstant.CARPETA_REPORTES_LINUX;
                imagenPath = ApplicationConstant.CARPETA_LINUX;
            }
            nombreReporteSinJasper = nombreReporte; // para q al imprimir el nombre del reporte no aparezca con la extension .jasper
            nombreReporte = nombreReporte + ".jasper";
            path += separator + nombreReporte; //path de los reportes
            parametros.put("imagenPath", imagenPath + separator);
            parametros.put("nombreImagen", nombreImagen);
            parametros.put("DiaHora", BaseCalendar.getAhora());
            //JasperReport reporte = (JasperReport) JRLoader.loadObject(path);
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            getConnectionDS();
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, conn);
            generarPdf(jasperPrint);

        } catch (Exception e) {
            logger.error("Error al intentar generar el reporte.", e);
        }finally{
             try {
                if(conn != null)
                    conn.close();
            } catch (SQLException ex) {
                logger.error("Error al intentar cerrar la conexion.", ex);
            }
        }
    }


     public void imprimirDirect(String nombreImpresora,float margenIzq,float margenDer,float margenPie, float margenArriba,boolean horizontal) {
        String path = "";
        String imagenPath = "";
        String separator = File.separator;
        try{
            if(separator.equals(ApplicationConstant.SEPARADOR_WINDOWS)) {
                path = ApplicationConstant.CARPETA_REPORTES_WINDOWS;
                imagenPath = ApplicationConstant.CARPETA_WINDOWS;
            }else {
                path = ApplicationConstant.CARPETA_REPORTES_LINUX;
                imagenPath = ApplicationConstant.CARPETA_LINUX;
            }
            nombreReporteSinJasper = nombreReporte; // para q al imprimir el nombre del reporte no aparezca con la extension .jasper
            nombreReporte = nombreReporte + ".jasper";
            path += separator + nombreReporte; //path de los reportes
            parametros.put("imagenPath", imagenPath + separator);
            parametros.put("nombreImagen", nombreImagen);
            parametros.put("DiaHora", BaseCalendar.getAhora());
            //JasperReport reporte = (JasperReport) JRLoader.loadObject(path);
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            getConnectionDS();
            //JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, conn);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, conn);
            //generarPdf(jasperPrint);
            PrinterJob job = PrinterJob.getPrinterJob();
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            int selectedService = 0;
            for(int i = 0; i < services.length;i++){
                if(services[i].getName().toUpperCase().contains(nombreImpresora.toUpperCase())){
                    selectedService = i;
                }
            }
            //Insets insets=new Insets(5, 10, 10, 5);
            float leftMargin =margenIzq;// insets.top;
            float rightMargin =margenDer;
            float topMargin = margenArriba;
            float bottomMargin =margenPie;
            job.setPrintService(services[selectedService]);
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            //MediaSizeName mediaSizeName = MediaSize.findMedia(4, 4, MediaPrintableArea.INCH);
            printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
            MediaSize mediaSize = MediaSize.NA.LETTER;
            float mediaWidth = mediaSize.getX(Size2DSyntax.MM);
            float mediaHeight = mediaSize.getY(Size2DSyntax.MM);
            //printRequestAttributeSet.add(mediaSizeName.NA_LETTER);
            printRequestAttributeSet.add(new Copies(1));
            if(horizontal)
                printRequestAttributeSet.add(OrientationRequested.LANDSCAPE);
            else
                printRequestAttributeSet.add(OrientationRequested.PORTRAIT);
            printRequestAttributeSet.add(new MediaPrintableArea(
                    leftMargin, topMargin,
                    (mediaWidth - leftMargin - rightMargin),
                    (mediaHeight - topMargin - bottomMargin), Size2DSyntax.MM));
            JRPrintServiceExporter exporter;
            exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            exporter.exportReport();


        } catch (Exception e) {
            logger.error("Error al intentar generar el reporte.", e);
        }finally{
             try {
                if(conn != null)
                    conn.close();
            } catch (SQLException ex) {
                logger.error("Error al intentar cerrar la conexion.", ex);
            }
        }
    }




    public void imprimir(List registros) {
        String path = "";
        String imagenPath = "";
        String separator = File.separator;
        try {
            if (separator.equals(ApplicationConstant.SEPARADOR_WINDOWS)) {
                path = ApplicationConstant.CARPETA_REPORTES_WINDOWS;
                imagenPath = ApplicationConstant.CARPETA_WINDOWS;
            } else {
                path = ApplicationConstant.CARPETA_REPORTES_LINUX;
                imagenPath = ApplicationConstant.CARPETA_LINUX;
            }
            nombreReporteSinJasper = nombreReporte; // para q al imprimir el nombre del reporte no aparezca con la extension .jasper
            nombreReporte = nombreReporte + ".jasper";
            path += separator + nombreReporte; //path de los reportes
            parametros.put("imagenPath", imagenPath + separator);
            parametros.put("nombreImagen", nombreImagen);
            parametros.put("DiaHora", BaseCalendar.getAhora());

            //JasperReport reporte = (JasperReport) JRLoader.loadObject(path);
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(path);

            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, new JRBeanCollectionDataSource(registros));

            generarPdf(jasperPrint);

        } catch (Exception ex) {
            logger.error("Error al intentar generar el reporte.", ex);
        }

    }

    public void imprimirMatricial() {
        String path = "";
        String imagenPath = "";
        String separator = File.separator;
        try {
            if (separator.equals(ApplicationConstant.SEPARADOR_WINDOWS)) {
                path = ApplicationConstant.CARPETA_REPORTES_WINDOWS;
                imagenPath = ApplicationConstant.CARPETA_WINDOWS;
            } else {
                path = ApplicationConstant.CARPETA_REPORTES_LINUX;
                imagenPath = ApplicationConstant.CARPETA_LINUX;
            }

            nombreReporte = nombreReporte + ".jasper";

            path += separator + nombreReporte; //path de los reportes
            parametros.put("imagenPath", imagenPath + separator);
            parametros.put("nombreImagen", nombreImagen);
            parametros.put("DiaHora", BaseCalendar.getAhora());
            //JasperReport reporte = (JasperReport) JRLoader.loadObject(path);
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            getConnectionDS();
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, conn);
            JasperPrintManager.printReport(jasperPrint, false);


        } catch (Exception e) {
            logger.error("Error al intentar generar el reporte.", e);
        }finally{
             try {
                if(conn != null)
                    conn.close();
            } catch (SQLException ex) {
                logger.error("Error al intentar cerrar la conexion.", ex);
            }
        }
    }

    public void imprimirMatricial(List registros) {
        String path = "";
        String imagenPath = "";
        String separator = File.separator;
        try {
            if (separator.equals(ApplicationConstant.SEPARADOR_WINDOWS)) {
                path = ApplicationConstant.CARPETA_REPORTES_WINDOWS;
                imagenPath = ApplicationConstant.CARPETA_WINDOWS;
            } else {
                path = ApplicationConstant.CARPETA_REPORTES_LINUX;
                imagenPath = ApplicationConstant.CARPETA_LINUX;
            }

            nombreReporte = nombreReporte + ".jasper";

            path += separator + nombreReporte; //path de los reportes
            parametros.put("imagenPath", imagenPath + separator);
            parametros.put("nombreImagen", nombreImagen);
            parametros.put("DiaHora", BaseCalendar.getAhora());
            //JasperReport reporte = (JasperReport) JRLoader.loadObject(path);
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, new JRBeanCollectionDataSource(registros));
            JasperPrintManager.printReport(jasperPrint, false);


        } catch (Exception e) {
            logger.error("Error al intentar generar el reporte.", e);
        }
    }

    public void imprimirXLS() {
        String path = "";
        String imagenPath = "";
        String separator = File.separator;
        ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
        try {
            if (separator.equals(ApplicationConstant.SEPARADOR_WINDOWS)) {
                path = ApplicationConstant.CARPETA_REPORTES_WINDOWS;
                imagenPath = ApplicationConstant.CARPETA_WINDOWS;
            } else {
                path = ApplicationConstant.CARPETA_REPORTES_LINUX;
                imagenPath = ApplicationConstant.CARPETA_LINUX;
            }

            parametros.put("imagenPath", imagenPath + separator);
            parametros.put("nombreImagen", nombreImagen);
            parametros.put("DiaHora", BaseCalendar.getAhora());

            //JasperReport jasperReport = (JasperReport) JRLoader.loadObject(path + separator + nombreReporte + ".jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(path + separator + nombreReporte + ".jasper");
            getConnectionDS();
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametros, conn);

            generarExcel(jp, xlsReport);

        } catch (JRException jre) {
            logger.error("Error al intentar generar el reporte.", jre);
        } catch (Exception e) {
            logger.error("Error al intentar generar el reporte.", e);
        }finally{
            try {
                if(conn != null)
                    conn.close();
            } catch (SQLException ex) {
                logger.error("Error al intentar cerrar la conexion.", ex);
            }
        }
    }

    public void imprimirXLS(List registros) {
        String path = "";
        String imagenPath = "";
        String separator = File.separator;
        ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
        try {
            if (separator.equals(ApplicationConstant.SEPARADOR_WINDOWS)) {
                path = ApplicationConstant.CARPETA_REPORTES_WINDOWS;
                imagenPath = ApplicationConstant.CARPETA_WINDOWS;
            } else {
                path = ApplicationConstant.CARPETA_REPORTES_LINUX;
                imagenPath = ApplicationConstant.CARPETA_LINUX;
            }


            parametros.put("imagenPath", imagenPath + separator);
            parametros.put("nombreImagen", nombreImagen);
            parametros.put("DiaHora", BaseCalendar.getAhora());

            //JasperReport jasperReport = (JasperReport) JRLoader.loadObject(path + separator + nombreReporte + ".jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(path + separator + nombreReporte + ".jasper");

            JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(registros));

            generarExcel(jp, xlsReport);

        } catch (JRException jre) {
            logger.error("Error al intentar generar el reporte.", jre);
        } catch (Exception e) {
            logger.error("Error al intentar generar el reporte.", e);
        }
    }



    public void beforeReport(String vparametros, String maquina, Long idEmpresa, Long idUsuario, String codUsuario, String nombreReporte) {

       try {
            InitialContext initCtx = new InitialContext();
            DataSource ds = (DataSource) initCtx.lookup(ApplicationConstant.DATASOURCENAME);
            Connection conn = ds.getConnection();
            // carga los parametros de entrada IN
            CallableStatement sentencia = conn.prepareCall("{call Actualiza_auditoria_repor_w(?,?,?,?,?,?)}");
            sentencia.setLong(1, idEmpresa);
            sentencia.setLong(2, idUsuario);
            sentencia.setString(3, nombreReporte);
            sentencia.setString(4,vparametros);
            sentencia.setString(5,maquina);
            sentencia.setString(6,codUsuario);
            sentencia.executeQuery();

       } catch (Exception e) {
            logger.error("Error al llamar al procedimiento beforeReport", e);
        }


    }


    @Override
    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
        parametros.put("nombreReporte", nombreReporte);
    }

    @Override
    public void setParametros(Map<String, Object> parametros) {
        this.parametros = new HashMap<String, Object>();
        this.parametros = parametros;
        if (nombreReporte != null && !nombreReporte.equals("")) {
            this.parametros.put("nombreReporte", nombreReporte);
        }
    }

    private void generarPdf(JasperPrint jp) throws Exception {
        faces = FacesContext.getCurrentInstance();
        byte[] report = JasperExportManager.exportReportToPdf(jp);
        response = (HttpServletResponse) faces.getExternalContext().getResponse();
        response.setHeader("Content-disposition", "attachment; filename=\"" + nombreReporteSinJasper + "\"" + sdf.format(System.currentTimeMillis()) + ".pdf\"");
        response.setContentType("application/pdf");
        response.setContentLength(report.length);
        ServletOutputStream out = response.getOutputStream();
        out.write(report);
        faces.responseComplete();
        out.flush();
        out.close();
    }

    private void generarExcel(JasperPrint jp, ByteArrayOutputStream byteReporte) throws JRException, Exception {
        faces = FacesContext.getCurrentInstance();
        JRXlsExporter xlsExporter = new JRXlsExporter();
        response = (HttpServletResponse) faces.getExternalContext().getResponse();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=\"" + nombreReporte + "_" + System.currentTimeMillis() + ".xls\"");
        xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        xlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, byteReporte);
        xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        xlsExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        xlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);

        xlsExporter.exportReport();

        byte[] report = byteReporte.toByteArray();
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(report);
        faces.responseComplete();
        outputStream.flush();
        outputStream.close();
    }

    public void getConnectionDS() throws Exception {
        // Obtain the initial Java Naming and Directory Interface
        // (JNDI) context.
        InitialContext initCtx = new InitialContext();
        // Perform JNDI lookup to obtain the resource.
        DataSource ds = (DataSource) initCtx.lookup(ApplicationConstant.DATASOURCENAME);
        conn = ds.getConnection();
    }
}
