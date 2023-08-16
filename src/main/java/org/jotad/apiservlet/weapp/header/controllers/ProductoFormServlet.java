package org.jotad.apiservlet.weapp.header.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jotad.apiservlet.weapp.header.configs.ProductoServicePrincipal;
import org.jotad.apiservlet.weapp.header.models.entities.Categoria;
import org.jotad.apiservlet.weapp.header.models.entities.Producto;
import org.jotad.apiservlet.weapp.header.services.ProductoService;


import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/productos/form")
public class ProductoFormServlet extends HttpServlet {
    @Inject
    @ProductoServicePrincipal
    private ProductoService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id;
        try {
            id = Long.valueOf(req.getParameter("id"));
        }catch (NumberFormatException e){
            id = 0L;
        }
        Producto producto = new Producto();
        producto.setCategoria(new Categoria());
        if (id > 0){
            Optional<Producto> o = service.porId(id);
            if (o.isPresent()){
                producto = o.get();
            }
        }
        req.setAttribute("categorias", service.listarCategoria());
        req.setAttribute("producto", producto);
        req.setAttribute("title", req.getAttribute("title") + ": Formulario de productos");
        getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nombre = req.getParameter("nombre");
        Integer precio;
        try {
            precio = Integer.valueOf(req.getParameter("precio"));
        } catch (NumberFormatException e) {
            precio = 0;
        }
        String sku = req.getParameter("sku");

        String fechaStr = req.getParameter("fecha_registro");
        Long categoriaid;
        try {
            categoriaid = Long.valueOf(req.getParameter("categoria"));
        }catch (NumberFormatException e){
            categoriaid = 0L;
        }
        Map<String, String> errores = new HashMap<>();

        if (nombre == null || nombre.isBlank()){
            errores.put("nombre", "El nombre es requerido!");
        }
        if (sku == null || sku.isBlank()){
            errores.put("sku", "El sku es requerido!");
        } else if (sku.length() > 10) {
            errores.put("sku", "El sku debe tener como Max. 10 caracteres");
        }
        if (fechaStr == null || fechaStr.isBlank()){
            errores.put("fecha_registro", "la fecha es requerida");
        }
        if (precio.equals(0)){
            errores.put("precio", "El precio es requerido");
        }
        if (categoriaid.equals(0L)){
            errores.put("categoria", "La categoria es requerido");
        }

        LocalDate fecha;
        try {
            fecha= LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }catch (DateTimeException e){
            fecha = null;
        }
        Long id;
        try {
            id = Long.valueOf(req.getParameter("id"));
        }catch (NumberFormatException e){
            id = null;
        }
        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setSku(sku);
        producto.setFechaRegistro(fecha);
        Categoria categoria = new Categoria();
        categoria.setId(categoriaid);
        producto.setCategoria(categoria);

        if (errores.isEmpty()) {
            service.guardar(producto);
            resp.sendRedirect(req.getContextPath() + "/productos.html");
        }else {
            req.setAttribute("errores", errores);
            req.setAttribute("categorias", service.listarCategoria());
            req.setAttribute("producto", producto);
            req.setAttribute("title", req.getAttribute("title") + ": Formulario de productos");
            getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
        }
    }
}
