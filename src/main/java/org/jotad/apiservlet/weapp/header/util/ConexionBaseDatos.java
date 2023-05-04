package org.jotad.apiservlet.weapp.header.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {

    private static String url = "jdbc:mysql://localhost:3306/java_curso?serverTimeZone=America/lima";
    private static String username = "root";
    private static String password = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username, password);
    }
}
