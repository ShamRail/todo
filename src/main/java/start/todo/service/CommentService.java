package start.todo.service;

import start.todo.model.domain.*;
import start.todo.service.crud.BasicCRUD;

import java.util.List;

public interface CommentService extends BasicCRUD<Comment> {
    List<Comment> comments(Task task);

    int deleteByProject(Project project);
    int deleteByCategory(Category category);
    int deleteByGroup(Group group);
    int deleteByTask(Task task);
}
