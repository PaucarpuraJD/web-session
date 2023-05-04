package org.jotad.apiservlet.weapp.header.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jotad.apiservlet.weapp.header.configs.Service;
import org.jotad.apiservlet.weapp.header.models.Usuario;
import org.jotad.apiservlet.weapp.header.repository.UsuarioRepository;
import org.jotad.apiservlet.weapp.header.repository.UsuarioRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    private UsuarioRepository usuarioRepository;

    @Inject
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> listar() {
        try {
            return usuarioRepository.listar();
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Usuario> porId(Long id) {
        try {
            return Optional.ofNullable(usuarioRepository.porId(id));
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Usuario usuario) {
        try {
            usuarioRepository.guardar(usuario);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            usuarioRepository.eliminar(id);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Usuario> login(String username, String password) {
        try {
            return Optional.ofNullable(usuarioRepository.porUsername(username)).filter(u -> u.getPassword().equals(password));
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }
}
