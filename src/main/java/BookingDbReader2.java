import dao.BookingDao;
import model.Booking;
import org.apache.commons.lang3.ArrayUtils;

public class BookingDbReader2 {

    public static void main(String[] args) {
        BookingDao bookingDao = new BookingDao();
        bookingDao.create(new Booking());
        System.out.println(ArrayUtils.toString(bookingDao.readAll().toArray()).replaceAll("\\),", "\n"));

        System.out.printf("\n%s\n", bookingDao.read(2));
        System.out.printf("\n%s\n", bookingDao.update(16));
        bookingDao.delete(16);
        System.out.println(ArrayUtils.toString(bookingDao.readAll().toArray()).replaceAll("\\),", "\n"));
    }
}
