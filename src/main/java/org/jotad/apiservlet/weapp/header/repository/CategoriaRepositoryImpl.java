package org.jotad.apiservlet.weapp.header.repository;

import jakarta.inject.Inject;
import org.jotad.apiservlet.weapp.header.configs.MysqlConn;
import org.jotad.apiservlet.weapp.header.configs.Repository;
import org.jotad.apiservlet.weapp.header.models.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoriaRepositoryImpl implements CrudRepository<Categoria> {

    private Connection conn;
    @Inject
    public CategoriaRepositoryImpl(@MysqlConn Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Categoria> listar() throws SQLException {

        List<Categoria> categorias = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM categorias")) {
            while (rs.next()) {
                Categoria c = getCategoria(rs);
                categorias.add(c);
            }
        }
        return categorias;
    }

    @Override
    public Categoria porId(Long id) throws SQLException {
        Categoria categoria = null;
        try (PreparedStatement psmt = conn.prepareStatement("SELECT * FROM categorias as c WHERE c.id=?")) {
            psmt.setLong(1, id);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    categoria = getCategoria(rs);
                }
            }

        }
        return categoria;
    }

    @Override
    public void guardar(Categoria categoria) throws SQLException {
        String sql;
        if (categoria.getId() != null && categoria.getId() > 0) {
            sql = "UPDATE categorias SET nombre=? WHERE id=?";
        } else {
            sql = "INSERT INTO categorias (nombre) VALUES (?)";
        }
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, categoria.getNombre());
            if (categoria.getId() != null && categoria.getId() > 0) {
                psmt.setLong(2, categoria.getId());
            }
            psmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM categorias WHERE id=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setLong(1, id);
            psmt.executeUpdate();
        }
    }

    private static Categoria getCategoria(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setId(rs.getLong("id"));
        c.setNombre(rs.getString("nombre"));
        return c;
    }
}
