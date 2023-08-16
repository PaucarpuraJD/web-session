package org.jotad.apiservlet.weapp.header.Listener;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class AplicacionListener implements ServletContextListener,
        ServletRequestListener, HttpSessionListener {

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().log("inicializando la app");
        servletContext = sce.getServletContext();
        servletContext.setAttribute("mensaje", "Algun valor global de la aplicacion");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        servletContext.log("destruyendo la app");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        servletContext.log("inicalizando el request");
        sre.getServletRequest().setAttribute("mensaje", "Guardando algun valor para el request");
        sre.getServletRequest().setAttribute("title", "Catalogo Servlet");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        servletContext.log("destruyendo el request");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        servletContext.log("inicializando la session");
        //Carro carro = new Carro();
        //HttpSession session = se.getSession();
        //session.setAttribute("carro", carro);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        servletContext.log("destruyendo la session");
    }
}
