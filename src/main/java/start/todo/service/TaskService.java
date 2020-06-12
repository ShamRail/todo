package start.todo.service;

import start.todo.model.domain.*;
import start.todo.service.crud.BasicCRUD;

import java.util.List;

public interface TaskService extends BasicCRUD<Task> {
    List<Task> tasks(Group group);
    boolean markAs(Task task, TaskStatus status);
    Task loadWithContent(Long id);
    boolean updateContent(Task task, TaskContent content);
    boolean updateResponsible(Task task, User newResponsible);

    int deleteByProject(Project project);
    int deleteByCategory(Category category);
    int deleteByGroup(Group group);

    List<Task> findByProject(Project project);
    List<Task> loadWithStructure(Project project);
}
