package start.todo.service;

import start.todo.model.domain.Project;
import start.todo.model.domain.User;
import start.todo.service.crud.BasicCRUD;

import java.util.List;

public interface ProjectService extends BasicCRUD<Project> {
    List<Project> userProjects(User user);
    List<User> participants(Project project);

    boolean createProject(User user, Project project);
    boolean dropProject(User user, Project project);

    boolean addParticipant(Project project, User user);
    boolean dropParticipant(Project project, User user);

}
