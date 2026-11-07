package pe.edu.pucp.iweb.lab8.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDao {

    private static final String URL = "jdbc:mysql://localhost:3306/melodiape";
    private static final String USER = "root";
    private static final String PASS = "12345678";

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
