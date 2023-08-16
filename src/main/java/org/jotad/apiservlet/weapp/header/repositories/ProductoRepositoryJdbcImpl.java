package org.jotad.apiservlet.weapp.header.repositories;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import org.jotad.apiservlet.weapp.header.configs.MysqlConn;
import org.jotad.apiservlet.weapp.header.configs.Repository;
import org.jotad.apiservlet.weapp.header.models.entities.Categoria;
import org.jotad.apiservlet.weapp.header.models.entities.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
@RepositoryJdbc
public class ProductoRepositoryJdbcImpl implements CrudRepository<Producto> {

    @Inject
    private Logger log;

    @Inject
    @MysqlConn
    private Connection conn;

    @PostConstruct
    public void inicializar(){
        log.info("Inicializando el beans " + this.getClass().getName());
    }

    @PreDestroy
    public void destruir(){
        log.info("Destruyebdi el beans " + this.getClass().getName());
    }

    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT p.*, c.nombre as categoria FROM productos as p" +
                     " inner join categorias as c ON (p.categoria_id = c.id) ORDER BY p.id ASC")) {
            while (rs.next()) {
                Producto p = getProducto(rs);
                productos.add(p);
            }
        }
        return productos;
    }

    @Override
    public Producto porId(Long id) throws SQLException {
        Producto producto = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.*, c.nombre as categoria FROM productos as p " +
                "inner join categorias as c ON (p.categoria_id = c.id) WHERE p.id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = getProducto(rs);
                }
            }
        }
        return producto;
    }

    @Override
    public void guardar(Producto producto) throws SQLException {
        String sql;
        if (producto.getId() != null && producto.getId() > 0) {
            sql = "UPDATE productos SET nombre=?, precio=?, sku=?, categoria_id=? WHERE id=?";
        } else {
            sql = "INSERT INTO productos (nombre, precio, sku, categoria_id, fecha_registro) VALUES(?,?,?,?,?)";
        }

        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, producto.getNombre());
            psmt.setInt(2, producto.getPrecio());
            psmt.setString(3, producto.getSku());
            psmt.setLong(4, producto.getCategoria().getId());
            if (producto.getId() != null && producto.getId() > 0) {
                psmt.setLong(5, producto.getId());
            } else {
                psmt.setDate(5, Date.valueOf(producto.getFechaRegistro()));
            }
            psmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id=?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)){
            psmt.setLong(1,id);
            psmt.executeUpdate();
        }
    }

    private static Producto getProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong(1));
        p.setNombre(rs.getString(2));
        p.setPrecio(rs.getInt(3));
        p.setSku(rs.getString("sku"));
        p.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
        Categoria c = new Categoria();
        c.setId(rs.getLong("categoria_id"));
        c.setNombre(rs.getString("categoria"));
        p.setCategoria(c);
        return p;
    }
}
