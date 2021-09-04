/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.metre.administracionBase.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;

public class SessionListener implements HttpSessionListener, ServletContextListener {

    Logger logger = Logger.getLogger(SessionListener.class);
    private static int totalActiveSessions;
    private static ServletContext ctx;
    private Map<String, HttpSession> mSession;
    BaseLecturaArchivos labels = new BaseLecturaArchivos();

    public static int getTotalActiveSession() {
        return totalActiveSessions;
    }

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        try {
            totalActiveSessions++;
            labels.setNombreArchivo(ApplicationConstant.ARCHIVO_PARAMETROS_GENERALES);
            HttpSession hSession = arg0.getSession();
            logger.debug("sessionCreated - Nueva session= " + totalActiveSessions);
            logger.debug("Session Time Out = " + labels.getPropiedad(ApplicationConstant.SESSIONTIMEOUT));
            hSession.setMaxInactiveInterval(Integer.parseInt(labels.getPropiedad(ApplicationConstant.SESSIONTIMEOUT)));
            logger.debug("Ingreso porque esta logueado=" + hSession.getId());
             mSession.put(hSession.getId(), hSession);
            logger.debug("Agregado a session= " + mSession);
            ctx.setAttribute(ApplicationConstant.CONTEXTMAPSESSION, mSession);
            logger.debug("Agregado a ServletContext");
        } catch (FileNotFoundException ex) {
            logger.error("Error en la lectura del archivo =", ex);
        } catch (IOException ex) {
            logger.error("Error en el acceso al archivo= ", ex);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        totalActiveSessions--;
        logger.debug("sessionDestroyed - Session borrada= " + totalActiveSessions);
        logger.debug("Servlet Context obtenido para la eliminacion= " + ctx);
        HttpSession hSession= mSession.get(arg0.getSession().getId());
        mSession.remove(hSession.getId());
        hSession.invalidate();
        ctx.setAttribute(ApplicationConstant.CONTEXTMAPSESSION, mSession);
        logger.debug("HttpSession obtenido para la eliminacion= " + mSession);
        logger.debug("Servlet Context que queda despues de la eliminacion= " + ctx.getAttribute(ApplicationConstant.CONTEXTMAPSESSION));
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ctx = sce.getServletContext();
        mSession = new HashMap<String, HttpSession>();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
