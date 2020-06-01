import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionManager {

    Connection getConnection(String host, String db, String userName, String password) throws SQLException;
}
