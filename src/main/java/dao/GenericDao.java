package dao;

import model.Booking;
import model.BookingDates;

import java.util.List;

public interface GenericDao<T> {

    public void create(BookingDates bookingDates);
    public void create(Booking booking);
    public T read(long id);
    public T update(long id);
    public void delete(long id);
    public List<T> readAll();

}
