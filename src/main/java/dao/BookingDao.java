package dao;

import lombok.extern.slf4j.Slf4j;
import model.Booking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.join;

@Slf4j
public class BookingDao extends JdbcDao implements GenericDao<Booking> {

    public static final String BOOKING_TABLE = "booking";
    public static final String INSERT_SQL_PATTERN = String.format("""
            INSERT INTO %s (firstname, lastname, totalprice, depositpaid, additionalneeds, bookingdates_id)
            VALUES %s;
            """, BOOKING_TABLE, "(\"%s\", \"%s\", %s, %s, \"%s\", %s)");

    public static final String INSERT_SQL_STATEMENT = String.format("""
            INSERT INTO %s (firstname, lastname, totalprice, depositpaid, additionalneeds, bookingdates_id)
            VALUES (?, ?, ?, ?, ?, ?);
            """, BOOKING_TABLE);

    public static final String SELECT_SQL_PATTERN =
            String.format("SELECT * FROM %s WHERE id = %s;", BOOKING_TABLE, "%s");

    public static final String UPDATE_SQL_PATTERN = String.format("""
            UPDATE %s
            SET firstname=?, lastname=?
            WHERE ID = %s;
            """, BOOKING_TABLE, "%s");

    public static final String DELETE_SQL_PATTERN = String.format("""
            DELETE FROM %s
            WHERE ID = %s
            """, BOOKING_TABLE, "%s");

    public BookingDao() {
        super(BOOKING_TABLE);
    }

    @Override
    public void create(Booking booking) {
        String query = "";
        try (PreparedStatement ps = connection.prepareStatement(INSERT_SQL_STATEMENT)) {
            ps.setString(1, booking.getFirstname());
            ps.setString(2, booking.getLastname());
            ps.setDouble(3, booking.getTotalPrice());
            ps.setBoolean(4, booking.isDepositPaid());
            ps.setString(5, booking.getAdditionalNeeds());
            ps.setLong(6, booking.getBookingDatesId());
            query = ps.toString();
            ps.executeUpdate();
            log.info(query);
        } catch (SQLException e) {
            log.error(join("Cannot create booking with: ", query));
            e.printStackTrace();
        }
    }

    public void create2(Booking booking) {
        String query = String.format(
                INSERT_SQL_PATTERN,
                booking.getFirstname(),
                booking.getLastname(),
                booking.getTotalPrice(),
                booking.isDepositPaid(),
                booking.getAdditionalNeeds(),
                booking.getBookingDatesId()
        );
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            log.error(join("Cannot create booking with ", query));
            e.printStackTrace();
        }
    }

    @Override
    public Booking read(long id) {
        Statement statement;
        ResultSet resultSet;
        Booking booking = null;
        String query;
        try {
            statement = connection.createStatement();
            query = String.format(SELECT_SQL_PATTERN, id);
            resultSet = statement.executeQuery(String.format(SELECT_SQL_PATTERN, id));
            log.info(query);
            while (resultSet.next()) {
                booking = Booking.builder()
                        .ID(resultSet.getLong("ID"))
                        .firstname(resultSet.getString("firstname"))
                        .lastname(resultSet.getString("lastname"))
                        .totalPrice(resultSet.getDouble("totalPrice"))
                        .depositPaid(resultSet.getBoolean("depositPaid"))
                        .additionalNeeds(resultSet.getString("additionalNeeds"))
                        .bookingDatesId(resultSet.getLong("bookingdates_id"))
                        .build();
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error(join("Cannot execute statement", selectQuery));
            e.printStackTrace();
        }
        return booking;
    }

    @Override
    public Booking update(long id) {
        String query = "";
        Booking booking = Booking.builder()
                .firstname("Vlad")
                .lastname("Lol")
                .build();
        try (PreparedStatement ps = connection.prepareStatement(String.format(UPDATE_SQL_PATTERN, id))) {
            ps.setString(1, booking.getFirstname());
            ps.setString(2, booking.getLastname());
            query = ps.toString();
            ps.executeUpdate();
            log.info(query);
        } catch (SQLException e) {
            log.error(join("Cannot execute statement", query));
            e.printStackTrace();
        }
        return booking;
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
    public List<Booking> readAll() {
        List<Booking> bookingList = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                bookingList.add(
                        Booking.builder()
                                .ID(resultSet.getLong("ID"))
                                .firstname(resultSet.getString("firstname"))
                                .lastname(resultSet.getString("lastname"))
                                .totalPrice(resultSet.getDouble("totalPrice"))
                                .depositPaid(resultSet.getBoolean("depositPaid"))
                                .additionalNeeds(resultSet.getString("additionalNeeds"))
                                .bookingDatesId(resultSet.getLong("bookingdates_id"))
                                .build()
                );
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error(join("Cannot execute statement", selectQuery));
            e.printStackTrace();
        }
        return bookingList;
    }
}
