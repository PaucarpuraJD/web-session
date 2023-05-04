package org.jotad.apiservlet.weapp.header.repository;

import jakarta.inject.Inject;
import org.jotad.apiservlet.weapp.header.configs.MysqlConn;
import org.jotad.apiservlet.weapp.header.configs.Repository;
import org.jotad.apiservlet.weapp.header.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {
    @Inject
    @MysqlConn
    private Connection conn;

    @Override
    public List<Usuario> listar() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios")) {
            while (rs.next()) {
                usuarios.add(getUsuario(rs));
            }
        }
        return usuarios;
    }

    @Override
    public Usuario porId(Long id) throws SQLException {
        Usuario usuario = null;
        try (PreparedStatement psmt = conn.prepareStatement("SELECT * FROM usuarios WHERE id=?")) {
            psmt.setLong(1, id);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    usuario = getUsuario(rs);
                }
            }
        }
        return usuario;
    }

    @Override
    public void guardar(Usuario usuario) throws SQLException {
        String sql;
        if (usuario.getId() != null && usuario.getId() > 0) {
            sql = "UPDATE usuarios set username=?,password=?,email=? WHERE id=?";
        } else {
            sql = "INSERT INTO usuarios (username,password,email) VALUES (?,?,?)";
        }
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, usuario.getUsername());
            psmt.setString(2, usuario.getPassword());
            psmt.setString(3, usuario.getCorreo());
            if (usuario.getId() != null && usuario.getId() > 0) {
                psmt.setLong(4, usuario.getId());
            }
            psmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setLong(1, id);
            psmt.executeUpdate();
        }
    }

    @Override
    public Usuario porUsername(String username) throws SQLException {
        Usuario usuario = null;
        try (PreparedStatement psmt = conn.prepareStatement("SELECT * FROM usuarios WHERE username=?")) {
            psmt.setString(1, username);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    usuario = getUsuario(rs);
                }
            }
        }
        return usuario;
    }

    private static Usuario getUsuario(ResultSet rs) throws SQLException {
        Usuario usuario;
        usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        usuario.setCorreo(rs.getString("email"));
        return usuario;
    }
}
