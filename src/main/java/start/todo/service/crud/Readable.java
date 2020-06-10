package start.todo.service.crud;

public interface Readable<T> {
    T findById(Long id);
}
