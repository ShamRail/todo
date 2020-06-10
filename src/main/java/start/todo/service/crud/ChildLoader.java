package start.todo.service.crud;

public interface ChildLoader<T, P> {
    T findByParent(P parent);
}
