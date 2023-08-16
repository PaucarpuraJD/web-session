package org.jotad.apiservlet.weapp.header.services;

import jakarta.inject.Inject;
import org.jotad.apiservlet.weapp.header.Interceptor.TransactionalJpa;
import org.jotad.apiservlet.weapp.header.configs.ProductoServicePrincipal;
import org.jotad.apiservlet.weapp.header.configs.Service;
import org.jotad.apiservlet.weapp.header.models.entities.Categoria;
import org.jotad.apiservlet.weapp.header.models.entities.Producto;
import org.jotad.apiservlet.weapp.header.repositories.CrudRepository;
import org.jotad.apiservlet.weapp.header.repositories.RepositoryJpa;

import java.util.List;
import java.util.Optional;

@Service
@ProductoServicePrincipal
@TransactionalJpa
public class ProductoServiceImpl implements ProductoService{

    @Inject
    @RepositoryJpa
    private CrudRepository<Producto> productoRepository;
    @Inject
    @RepositoryJpa
    private CrudRepository<Categoria> categoriaRepository;


    @Override
    public List<Producto> listar() {
        try {
            return productoRepository.listar();
        } catch (Exception e) {
            throw new Servicexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Producto> porId(Long id) {
        try {
            return Optional.ofNullable(productoRepository.porId(id));
        } catch (Exception e) {
            throw new Servicexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Producto producto) {
        try {
            productoRepository.guardar(producto);
        } catch (Exception e) {
            throw new Servicexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            productoRepository.eliminar(id);
        } catch (Exception e) {
            throw new Servicexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Categoria> listarCategoria() {
        try {
            return categoriaRepository.listar();
        } catch (Exception e) {
            throw new Servicexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Categoria> porIdCategoria(Long id) {
        try {
            return Optional.ofNullable(categoriaRepository.porId(id));
        } catch (Exception e) {
            throw new Servicexception(e.getMessage(), e.getCause());
        }
    }
}
