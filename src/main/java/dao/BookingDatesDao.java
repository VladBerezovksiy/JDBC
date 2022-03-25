package dao;

import lombok.extern.slf4j.Slf4j;
import model.Booking;
import model.BookingDates;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.join;

@Slf4j
public class BookingDatesDao extends JdbcDao implements GenericDao {

    public static final String BOOKING_TABLE = "bookingdates";
    public static final String INSERT_SQL_STATEMENT = String.format("""
            INSERT INTO %s (checkin, checkout)
            VALUES (?, ?);
            """, BOOKING_TABLE);

    public static final String SELECT_SQL_PATTERN =
            String.format("SELECT * FROM %s WHERE id = %s;", BOOKING_TABLE, "%s");

    public static final String UPDATE_SQL_PATTERN = String.format("""
            UPDATE %s
            SET checkout=?
            WHERE ID = %s;
            """, BOOKING_TABLE, "%s");

    public static final String DELETE_SQL_PATTERN = String.format("""
            DELETE FROM %s
            WHERE ID = %s
            """, BOOKING_TABLE, "%s");


    public BookingDatesDao() {
        super(BOOKING_TABLE);
    }

    @Override
    public void create(BookingDates bookingDates) {
        String query = "";
        try (PreparedStatement ps = connection.prepareStatement(INSERT_SQL_STATEMENT)) {
            ps.setString(1, bookingDates.getCheckin());
            ps.setString(2, bookingDates.getCheckout());
            query = ps.toString();
            ps.executeUpdate();
            log.info(query);
        } catch (SQLException e) {
            log.error(join("Cannot create bookingDates with: ", query));
            e.printStackTrace();
        }
    }

    @Override
    public Object read(long id) {
        Statement statement;
        ResultSet resultSet;
        BookingDates bookingDates = null;
        String query;
        try {
            statement = connection.createStatement();
            query = String.format(SELECT_SQL_PATTERN, id);
            resultSet = statement.executeQuery(String.format(SELECT_SQL_PATTERN, id));
            log.info(query);
            while (resultSet.next()) {
                bookingDates = BookingDates.builder()
                        .checkin(resultSet.getString("checkin"))
                        .checkout(resultSet.getString("checkout"))
                        .build();
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error(join("Cannot execute statement", selectQuery));
            e.printStackTrace();
        }
        return bookingDates;
    }

    @Override
    public Object update(long id) {
        String query = "";
        BookingDates bookingDates = BookingDates.builder()
                .checkout("2014-02-10")
                .build();
        try (PreparedStatement ps = connection.prepareStatement(String.format(UPDATE_SQL_PATTERN, id))) {
            ps.setString(1, bookingDates.getCheckout());
            query = ps.toString();
            ps.executeUpdate();
            log.info(query);
        } catch (SQLException e) {
            log.error(join("Cannot execute statement", query));
            e.printStackTrace();
        }
        return bookingDates;
    }

    @Override
    public void delete(long id) {
        String query = "";
        try (PreparedStatement ps = connection.prepareStatement(String.format(DELETE_SQL_PATTERN, id))) {
            query = ps.toString();
            ps.executeUpdate();
            log.info(query);
        } catch (SQLException e) {
            log.error(join("Cannot execute statement", query));
            e.printStackTrace();
        }
    }

    @Override
    public List readAll() {
        List<BookingDates> bookingDatesList = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                bookingDatesList.add(
                        BookingDates.builder()
                                .checkin(resultSet.getString("checkin"))
                                .checkout(resultSet.getString("checkout"))
                                .build()
                );
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error(join("Cannot execute statement", selectQuery));
            e.printStackTrace();
        }
        return bookingDatesList;
    }

    @Override
    public void create(Booking booking) {

    }
}
