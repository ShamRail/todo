package start.todo.service;

import start.todo.model.domain.Comment;
import start.todo.model.domain.Task;
import start.todo.service.crud.BasicCRUD;

import java.util.List;

public interface CommentService extends BasicCRUD<Comment> {
    List<Comment> comments(Task task);
}
