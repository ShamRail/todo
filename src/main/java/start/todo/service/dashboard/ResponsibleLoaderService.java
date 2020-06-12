package start.todo.service.dashboard;

import start.todo.model.domain.Task;
import start.todo.model.domain.User;

import java.util.List;

public interface ResponsibleLoaderService {

    List<Task> loadByResponsible(User responsible);

}
