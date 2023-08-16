package org.jotad.apiservlet.weapp.header.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jotad.apiservlet.weapp.header.models.entities.Usuario;
import org.jotad.apiservlet.weapp.header.services.LoginService;
import org.jotad.apiservlet.weapp.header.services.LoginServiceSessionImpl;
import org.jotad.apiservlet.weapp.header.services.UsuarioService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    @Inject
    private UsuarioService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Usuario> usuarios = service.listar();

        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = auth.getUsername(req);

        req.setAttribute("usuarios", usuarios);
        req.setAttribute("username", usernameOptional);
        req.setAttribute("title", req.getAttribute("title") + ": Listado de usuarios");

        getServletContext().getRequestDispatcher("/listarUsuario.jsp").forward(req, resp);

    }
}
