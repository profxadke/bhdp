import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//public class DatabaseConnection {
//    private static final String URL = "jdbc:mariadb://localhost:3306/notes_app";
//    private static final String USER = "notes_user"; // Replace with your actual user
//    private static final String PASSWORD = "your_password"; // Replace with your actual password
//
//    public static Connection getConnection() {
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("Connected to the database.");
//        } catch (SQLException e) {
//            System.err.println("Connection failed: " + e.getMessage());
//        }
//        return connection;
//    }
//}
import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/notes_app";
    private static final String USER = "notes_user";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
