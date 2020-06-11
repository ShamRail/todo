package start.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import start.todo.model.domain.Category;
import start.todo.model.domain.Project;
import start.todo.repo.CategoryRepository;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryDB;

    @Autowired
    private GroupService groupService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentService commentService;

    @Override
    public List<Category> categories(Project project) {
        return categoryDB.findByProject(project);
    }

    @Override
    public int deleteByProject(Project project) {
        return categoryDB.deleteByProject(project);
    }

    @Override
    public Category save(Category entity) {
        return categoryDB.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        commentService.deleteByCategory(Category.idStub(id));
        taskService.deleteByCategory(Category.idStub(id));
        groupService.deleteByCategory(Category.idStub(id));
        return categoryDB.delete(id) > 0;
    }

    @Override
    public Category findById(Long id) {
        return categoryDB.findById(id).orElse(null);
    }

    @Override
    public boolean update(Category entity) {
        return categoryDB.update(entity) > 0;
    }
}
