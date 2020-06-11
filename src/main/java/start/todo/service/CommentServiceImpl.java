package start.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import start.todo.model.domain.*;
import start.todo.repo.CommentRepository;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentDB;

    @Override
    public List<Comment> comments(Task task) {
        return commentDB.findByTask(task);
    }

    @Override
    public int deleteByProject(Project project) {
        return commentDB.deleteByProject(project);
    }

    @Override
    public int deleteByCategory(Category category) {
        return commentDB.deleteByCategory(category);
    }

    @Override
    public int deleteByGroup(Group group) {
        return commentDB.deleteByGroup(group);
    }

    @Override
    public int deleteByTask(Task task) {
        return commentDB.deleteByTask(task);
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
        return commentDB.findById(id).orElse(null);
    }

    @Override
    public boolean update(Comment entity) {
        return commentDB.update(entity) > 0;
    }
}
