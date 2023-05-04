package org.jotad.apiservlet.weapp.header.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jotad.apiservlet.weapp.header.models.Carro;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@WebServlet("/carro/actualizar")
public class ActualizarCarroServlet extends HttpServlet {

    @Inject
    private Carro carro;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       // HttpSession session = req.getSession();
       // if (session.getAttribute("carro") !=null){
       //     Carro carro = (Carro) session.getAttribute("carro");
            updateProductos(req, carro);
            updateCantidades(req, carro);
        //}
        resp.sendRedirect(req.getContextPath() + "/carro/ver");
    }

    public void updateProductos(HttpServletRequest request, Carro carro){
        String[] deleteIds = request.getParameterValues("deleteProductos");

        if (deleteIds !=null && deleteIds.length > 0){
            List<String> productoIds = Arrays.asList(deleteIds);
            carro.removeProductos(productoIds);
        }
    }

    public void updateCantidades(HttpServletRequest request, Carro carro){

        Enumeration<String> enumer = request.getParameterNames();

        while (enumer.hasMoreElements()){
            String paramName = enumer.nextElement();
            if (paramName.startsWith("cant_")){
                String id = paramName.substring(5);
                String cantidad = request.getParameter(paramName);
                if (cantidad !=null){
                    carro.updateCantidad(id, Integer.parseInt(cantidad));
                }
            }
        }
    }
}
