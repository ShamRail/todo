package start.todo.service;

import start.todo.model.domain.Category;
import start.todo.model.domain.Project;
import start.todo.service.crud.BasicCRUD;

import java.util.List;

public interface CategoryService extends BasicCRUD<Category> {
    List<Category> categories(Project project);
    int deleteByProject(Project project);
}
