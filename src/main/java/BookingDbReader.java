import db.MySqlConnection;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class BookingDbReader {

    public static void main(String[] args) {
        Connection dbConnection;
        Statement statement;
        try {
            dbConnection = new MySqlConnection().get();
            statement = dbConnection.createStatement();
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
