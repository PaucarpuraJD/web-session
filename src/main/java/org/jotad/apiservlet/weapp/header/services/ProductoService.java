package org.jotad.apiservlet.weapp.header.services;

import org.jotad.apiservlet.weapp.header.models.entities.Categoria;
import org.jotad.apiservlet.weapp.header.models.entities.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> listar();

    Optional<Producto> porId(Long id);

    void guardar(Producto producto);

    void eliminar(Long id);

    List<Categoria> listarCategoria();

    Optional<Categoria> porIdCategoria(Long id);
}
