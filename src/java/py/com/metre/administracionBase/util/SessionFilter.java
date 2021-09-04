/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.metre.administracionBase.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author adolfo
 */
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session= req.getSession();
        String usuario= session.getAttribute(ApplicationConstant.USERNAME) != null ? session.getAttribute(ApplicationConstant.USERNAME).toString() : null;
        String sPage= req.getRequestURL().toString();
        System.out.println("--------------Pagina llamada------------- = " + sPage);
        String sPageURL= "/"+ sPage.substring(sPage.indexOf(ApplicationConstant.PAGINA), sPage.indexOf(ApplicationConstant.PAGINA)+ApplicationConstant.PAGINA.length());
//        System.out.println("--------------Pagina URL ------------- = " + sPageURL);
//        System.out.println("--------------Pagina Cantidad URL ------------- = " + sPageURL.length());
        int nPageSize= sPage.substring(sPage.indexOf(ApplicationConstant.PAGINA), sPage.length()-1).length();
//        System.out.println("--------------Pagina Cantidad------------- = " + nPageSize);
        if(usuario != null && (sPage.contains("login.xhtml") || nPageSize == ApplicationConstant.PAGINA.length()))
            res.sendRedirect(sPageURL + ApplicationConstant.PAGINIT);
        else if (usuario == null && !sPage.contains("login.xhtml"))
            res.sendRedirect(sPageURL + ApplicationConstant.PAGLOGIN) ;
        else
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
