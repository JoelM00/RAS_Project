import java.sql.Connection;
import java.sql.DriverManager;

public class ConfigDAO {
    static final String USERNAME = "root";
    static final String PASSWORD = "root";
    private static final String DATABASE = "BetDatabase";
    private static final String DRIVER = "jdbc:mysql";
    static final String URL = DRIVER+"://localhost/BetDatabase?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=WET";

    static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(ConfigDAO.URL, ConfigDAO.USERNAME, ConfigDAO.PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    static void close(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}