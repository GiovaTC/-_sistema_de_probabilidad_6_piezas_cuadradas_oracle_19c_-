package conexion;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class ConexionOracle {

    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private static final String USER = "system";
    private static final String PASSWORD = "Tapiero123";

    public static Connection conectar() {

        try {
            Class.forName(
                    "oracle.jdbc.driver.OracleDriver");

            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}
