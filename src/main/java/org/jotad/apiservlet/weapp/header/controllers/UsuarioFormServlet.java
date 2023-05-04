package org.jotad.apiservlet.weapp.header.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jotad.apiservlet.weapp.header.models.Usuario;
import org.jotad.apiservlet.weapp.header.services.UsuarioService;
import org.jotad.apiservlet.weapp.header.services.UsuarioServiceImpl;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/usuarios/form")
public class UsuarioFormServlet extends HttpServlet {

    @Inject
    private UsuarioService service;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id;
        try {
            id = Long.valueOf(req.getParameter("id"));
        }catch (NumberFormatException e){
            id=0L;
        }
        Usuario usuario = new Usuario();
        if (id > 0){
            Optional<Usuario> o = service.porId(id);
            if (o.isPresent()){
                usuario = o.get();
            }
        }
        req.setAttribute("usuario", usuario);
        req.setAttribute("title", req.getAttribute("title") + ": Registro de usuarios");
        getServletContext().getRequestDispatcher("/formUsuario.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        Map<String, String> errores = new HashMap<>();
        if (username == null || username.isBlank()){
            errores.put("username", "El username es requerido");
        }
        if (password == null || password.isBlank()) {
            errores.put("password", "El password es requerido");
        }
        if (email == null || email.isBlank()){
            errores.put("email", "El email es requerido");
        }

        Long id;
        try {
            id = Long.valueOf(req.getParameter("id"));
        }catch (NumberFormatException e){
            id = 0L;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setCorreo(email);
        if (errores.isEmpty()){
            service.guardar(usuario);
            resp.sendRedirect(req.getContextPath() + "/usuarios");
        }else {
            req.setAttribute("errores", errores);
            req.setAttribute("usuario", usuario);
            req.setAttribute("title", req.getAttribute("title") + ": Registro de usuarios");
            getServletContext().getRequestDispatcher("/formUsuario.jsp").forward(req,resp);
        }
    }
}
