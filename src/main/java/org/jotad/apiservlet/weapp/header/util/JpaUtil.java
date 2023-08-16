package org.jotad.apiservlet.weapp.header.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("ejemploJpa");
    }
    public static EntityManager getEntityManager(){
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }
}
