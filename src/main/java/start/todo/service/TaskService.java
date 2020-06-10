package start.todo.service;

import start.todo.model.domain.Group;
import start.todo.model.domain.Task;
import start.todo.model.domain.TaskStatus;
import start.todo.service.crud.BasicCRUD;

import java.util.List;

public interface TaskService extends BasicCRUD<Task> {
    List<Task> tasks(Group group);
    boolean markAs(Task task, TaskStatus status);
}
