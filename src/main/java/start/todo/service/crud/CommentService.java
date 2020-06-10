package start.todo.service.crud;

import start.todo.model.domain.Comment;
import start.todo.model.domain.Task;

import java.util.List;

public interface CommentService extends BasicCRUD<Comment> {
    List<Comment> comments(Task task);
}
