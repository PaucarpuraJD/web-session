package org.jotad.apiservlet.weapp.header.repository;

import org.jotad.apiservlet.weapp.header.models.Usuario;

import java.sql.SQLException;

public interface UsuarioRepository extends CrudRepository<Usuario> {
    Usuario porUsername(String username) throws SQLException;
}
