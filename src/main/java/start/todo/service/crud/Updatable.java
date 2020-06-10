package start.todo.service.crud;

public interface Updatable<T> {
    boolean update(T entity);
}
