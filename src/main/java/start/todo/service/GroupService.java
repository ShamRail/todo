package start.todo.service;

import start.todo.model.domain.Category;
import start.todo.model.domain.Group;
import start.todo.service.crud.BasicCRUD;

import java.util.List;

public interface GroupService extends BasicCRUD<Group> {
    List<Group> groups(Category category);
}
