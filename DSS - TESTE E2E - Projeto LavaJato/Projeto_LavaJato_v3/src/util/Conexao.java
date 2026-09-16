package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL =
        "jdbc:mysql://localhost:3306/lavajato";

    private static final String USER = "root";
    private static final String PASSWORD = "1234567";

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD);
    }
}