package org.jotad.apiservlet.weapp.header.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jotad.apiservlet.weapp.header.models.entities.Usuario;
import org.jotad.apiservlet.weapp.header.services.UsuarioService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/usuarios/eliminar")
public class UsuarioEleminarServlet extends HttpServlet {

    @Inject
    private UsuarioService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id;
        try {
            id = Long.valueOf(req.getParameter("id"));
        }catch (NumberFormatException e){
            id = 0L;
        }

        if (id > 0){
            Optional<Usuario> o = service.porId(id);
            if (o.isPresent()){
                service.eliminar(id);
                resp.sendRedirect(req.getContextPath() + "/usuarios");
            }else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND , "No exite el usuario en la base de datos");
            }
        }else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "El id no puede ser null, tiene que ser enviado en el parametro del request");
        }
    }
}
