import dao.BookingDao;
import dao.BookingDatesDao;
import model.Booking;
import model.BookingDates;
import org.apache.commons.lang3.ArrayUtils;

public class BookingDbReader2 {

    public static void main(String[] args) {

        // Operation with 'booking' table in SQL

        BookingDao bookingDao = new BookingDao();
        bookingDao.create(new Booking());
        System.out.println(ArrayUtils.toString(bookingDao.readAll().toArray()).replaceAll("\\),", "\n"));

        System.out.printf("\n%s\n", bookingDao.read(2));
        System.out.printf("\n%s\n", bookingDao.update(16));
        bookingDao.delete(16);

        System.out.println(ArrayUtils.toString(bookingDao.readAll().toArray()).replaceAll("\\),", "\n"));

        // Operation with 'bookingdates' table in SQL

        BookingDatesDao bookingDatesDao = new BookingDatesDao();
        bookingDatesDao.create(new BookingDates());
        System.out.println(ArrayUtils.toString(bookingDatesDao.readAll().toArray()).replaceAll("\\),", "\n"));

        System.out.printf("\n%s\n", bookingDatesDao.read(2));
        System.out.printf("\n%s\n", bookingDatesDao.update(2));
        bookingDatesDao.delete(4);

        System.out.println(ArrayUtils.toString(bookingDatesDao.readAll().toArray()).replaceAll("\\),", "\n"));
    }
}
