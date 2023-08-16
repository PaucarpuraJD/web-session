package org.jotad.apiservlet.weapp.header.services;

import jakarta.inject.Inject;
import org.jotad.apiservlet.weapp.header.Interceptor.TransactionalJpa;
import org.jotad.apiservlet.weapp.header.configs.Service;
import org.jotad.apiservlet.weapp.header.models.entities.Usuario;
import org.jotad.apiservlet.weapp.header.repositories.RepositoryJpa;
import org.jotad.apiservlet.weapp.header.repositories.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
@TransactionalJpa
public class UsuarioServiceImpl implements UsuarioService{

    private UsuarioRepository usuarioRepository;

    @Inject
    public UsuarioServiceImpl(@RepositoryJpa UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> listar() {
        try {
            return usuarioRepository.listar();
        } catch (Exception e) {
            throw new Servicexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Usuario> porId(Long id) {
        try {
            return Optional.ofNullable(usuarioRepository.porId(id));
        } catch (Exception e) {
            throw new Servicexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Usuario usuario) {
        try {
            usuarioRepository.guardar(usuario);
        } catch (Exception e) {
            throw new Servicexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            usuarioRepository.eliminar(id);
        } catch (Exception e) {
            throw new Servicexception(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Usuario> login(String username, String password) {
        try {
            return Optional.ofNullable(usuarioRepository.porUsername(username)).filter(u -> u.getPassword().equals(password));
        } catch (Exception e) {
            throw new Servicexception(e.getMessage(), e.getCause());
        }
    }
}
