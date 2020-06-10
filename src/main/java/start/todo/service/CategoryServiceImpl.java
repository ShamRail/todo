package start.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import start.todo.model.domain.Category;
import start.todo.model.domain.Project;
import start.todo.repo.CategoryRepository;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryDB;

    @Override
    public List<Category> categories(Project project) {
        return categoryDB.findByProject(project);
    }

    @Override
    public Category save(Category entity) {
        return categoryDB.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        return categoryDB.delete(id) > 0;
    }

    @Override
    public Category findById(Long id) {
        return categoryDB.findById(id).orElse(new Category());
    }

    @Override
    public boolean update(Category entity) {
        return categoryDB.update(entity) > 0;
    }
}
