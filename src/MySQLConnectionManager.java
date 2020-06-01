import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnectionManager implements IConnectionManager {

    @Override
    public synchronized Connection getConnection(String host, String db, String userName, String password)
            throws SQLException {
        System.out.println("Creating connection ...");

        String strConnect = "jdbc:mysql://" + host + "/" + db;
        Properties pro = new Properties();
        pro.put("user", userName);
        pro.put("password", password);
        pro.put("useUnicode", "true");
        pro.put("characterEncoding", "UTF-8");
        pro.put("autoReconnect", "true");
//	pro.put("useSSL", "false");

        Connection connection = DriverManager.getConnection(strConnect, pro);
        System.out.println("Connected!.");
        return connection;
    }
}
