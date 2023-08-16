package org.jotad.apiservlet.weapp.header.repositories;

import org.jotad.apiservlet.weapp.header.models.entities.Usuario;


public interface UsuarioRepository extends CrudRepository<Usuario> {
    Usuario porUsername(String username) throws Exception;
}
