package org.jotad.apiservlet.weapp.header.controllers;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jotad.apiservlet.weapp.header.configs.ProductoServicePrincipal;
import org.jotad.apiservlet.weapp.header.models.Producto;
import org.jotad.apiservlet.weapp.header.repository.ProductoRepositoryJdbcImpl;
import org.jotad.apiservlet.weapp.header.services.ProductoService;
import org.jotad.apiservlet.weapp.header.services.ProductoServiceJdbcImpl;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/productos/eliminar")
public class ProductoEliminarServlet extends HttpServlet {

    @Inject
    @ProductoServicePrincipal
    private ProductoService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long id;
        try{
            id = Long.parseLong(req.getParameter("id"));
        }catch (NumberFormatException e){
            id = 0L;
        }
        if (id > 0){
            Optional<Producto> o = service.porId(id);
            if (o.isPresent()) {
                service.eliminar(id);
                resp.sendRedirect(req.getContextPath() + "/productos.html");
            }else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No existe el producto en la base de datos");
            }
        }else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,"El id es null, tiene que enviar como parametro en la url");
        }
    }
}
