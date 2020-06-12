package start.todo.service.dashboard;

import start.todo.model.domain.*;

import java.util.List;
import java.util.Map;

public interface StructuralLoaderService {

    List<Task> allTasks(User user);
    List<Task> tasksByProject(Project project);
    List<Category> categoriesByProject(Project project);
    List<Group> groupsByProject(Project project);

}
