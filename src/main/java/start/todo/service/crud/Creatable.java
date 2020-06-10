package start.todo.service.crud;

public interface Creatable<T> {
    T save(T entity);
}
