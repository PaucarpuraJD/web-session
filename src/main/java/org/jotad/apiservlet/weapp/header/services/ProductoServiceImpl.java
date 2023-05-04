package org.jotad.apiservlet.weapp.header.services;

import jakarta.enterprise.inject.Alternative;
import org.jotad.apiservlet.weapp.header.models.Categoria;
import org.jotad.apiservlet.weapp.header.models.Producto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//@Alternative
public class ProductoServiceImpl implements ProductoService{
    @Override
    public List<Producto> listar() {
        return Arrays.asList(new Producto(1L, "Laptop", "Computo", 3500),
                new Producto(2L, "Mesa escritorio", "Oficina", 500),
                new Producto(3L, "Teclado Mecanico", "Computo", 300));
    }

    @Override
    public Optional<Producto> porId(Long id) {
        return listar().stream().filter(p -> p.getId().equals(id)).findAny();
    }

    @Override
    public void guardar(Producto producto) {

    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public List<Categoria> listarCategoria() {
        return null;
    }

    @Override
    public Optional<Categoria> porIdCategoria(Long id) {
        return Optional.empty();
    }

}
