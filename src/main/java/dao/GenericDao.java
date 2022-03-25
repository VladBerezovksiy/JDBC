package dao;

import java.util.List;

public interface GenericDao<T> {

    public void create(T t);
    public T read(long id);
    public T update(T t);
    public void delete(long id);
    public List<T> readAll();

}
