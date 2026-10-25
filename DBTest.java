import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/HospitalDB";
        String user = "root";
        String password = "Suji3101_@.ravi";

        try {
            try (Connection con = DriverManager.getConnection(url, user, password)) {
                if (con.isValid(2)) {
                    System.out.println("MySQL Connected Successfully!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }
    }
}