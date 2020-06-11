package start.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import start.todo.model.domain.Category;
import start.todo.model.domain.Group;
import start.todo.model.domain.Project;
import start.todo.repo.GroupRepository;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupDB;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    @Override
    public List<Group> groups(Category category) {
        return groupDB.findByCategory(category);
    }

    @Override
    public int deleteByProject(Project project) {
        return groupDB.deleteByProject(project);
    }

    @Override
    public int deleteByCategory(Category category) {
        return groupDB.deleteByCategory(category);
    }

    @Override
    public Group save(Group entity) {
        return groupDB.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        commentService.deleteByGroup(Group.idStub(id));
        taskService.deleteByGroup(Group.idStub(id));
        return groupDB.delete(id) > 0;
    }

    @Override
    public Group findById(Long id) {
        return groupDB.findById(id).orElse(null);
    }

    @Override
    public boolean update(Group entity) {
        return groupDB.update(entity) > 0;
    }
}
