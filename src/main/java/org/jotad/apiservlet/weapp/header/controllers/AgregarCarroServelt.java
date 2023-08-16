package org.jotad.apiservlet.weapp.header.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jotad.apiservlet.weapp.header.configs.ProductoServicePrincipal;
import org.jotad.apiservlet.weapp.header.models.Carro;
import org.jotad.apiservlet.weapp.header.models.ItemCarro;
import org.jotad.apiservlet.weapp.header.models.entities.Producto;
import org.jotad.apiservlet.weapp.header.services.ProductoService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/carro/agregar")
public class AgregarCarroServelt extends HttpServlet {

    @Inject
    private Carro carro;

    @Inject
    @ProductoServicePrincipal
    private ProductoService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Optional<Producto> producto = service.porId(id);
        if (producto.isPresent()){
            ItemCarro item = new ItemCarro(1, producto.get());
            //HttpSession session = req.getSession();
            //carro = (Carro) session.getAttribute("carro");
            carro.addItemCarro(item);
        }
        resp.sendRedirect(req.getContextPath() + "/carro/ver");
    }
}
