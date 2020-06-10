package start.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import start.todo.model.domain.Comment;
import start.todo.model.domain.Task;
import start.todo.repo.CommentRepository;

import java.util.List;

public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentDB;

    @Override
    public List<Comment> comments(Task task) {
        return commentDB.findByTask(task);
    }

    @Override
    public Comment save(Comment entity) {
        return commentDB.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        return commentDB.delete(id) > 0;
    }

    @Override
    public Comment findById(Long id) {
        return commentDB.findById(id).orElse(new Comment());
    }

    @Override
    public boolean update(Comment entity) {
        return commentDB.update(entity) > 0;
    }
}
