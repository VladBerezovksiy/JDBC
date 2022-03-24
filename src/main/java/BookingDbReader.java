import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;

@Slf4j
public class BookingDbReader {

    public static final int MY_SQL_PORT = 3306;
    public static final String MY_SQL_IP = "127.0.0.1";
    public static final String MY_SQL_DATABASE = "restfulbooker";
    public static final String MY_SQL_HOST =
            StringUtils.join("jdbc:mysql://", MY_SQL_IP, ":", MY_SQL_PORT, "/");
    public static final String MY_SQL_URL = StringUtils.join(MY_SQL_HOST, MY_SQL_DATABASE);
    public static final String MY_SQL_USERNAME = "root";
    public static final String MY_SQL_PASSWORD = "root";

    public static void main(String[] args) {
        try {
            Connection dbConnection =
                    DriverManager.getConnection(MY_SQL_URL, MY_SQL_USERNAME, MY_SQL_PASSWORD);
            Statement statement = dbConnection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM restfulbooker.booking;");
            while (result.next()){
                long id = result.getLong(1);
                String name = result.getString("firstname");
                System.out.printf("ID: %s\n", id);
                System.out.printf("Firstname: %s\n", name);
            }
            dbConnection.close();
        } catch (SQLException e) {
            log.error("ERROR sql connection");
            e.printStackTrace();
        }
    }

}
