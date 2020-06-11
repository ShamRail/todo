package start.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import start.todo.model.domain.Project;
import start.todo.model.domain.Role;
import start.todo.model.domain.User;
import start.todo.model.domain.UserProject;
import start.todo.repo.ProjectRepository;
import start.todo.repo.UserProjectRepository;
import start.todo.repo.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectDB;

    @Autowired
    private UserProjectRepository userProjectDB;

    @Autowired
    private UserRepository userDB;

    @Override
    public List<Project> userProjects(User user) {
        return userProjectDB.userProjects(user);
    }

    @Override
    public List<User> participants(Project project) {
        return userProjectDB.projectUsers(project);
    }

    @Override
    public boolean createProject(User user, Project project) {
        if (user.getId() == null) {
            return false;
        }
        projectDB.save(project);
        UserProject up = new UserProject(user, project);
        up.setRole(Role.CREATOR);
        up.setSince(LocalDateTime.now());
        userProjectDB.save(up);
        return true;
    }

    @Override
    public boolean dropProject(User user, Project project) {
        UserProject up = userProjectDB.findByUserAndProject(user, project);
        if (up == null || up.getRole() != Role.CREATOR) {
            return false;
        }
        userProjectDB.deleteByUserAndProject(user, project);
        return delete(project.getId());
    }

    @Override
    public boolean addParticipant(Project project, User user) {
        if (project.getId() == null || user.getId() == null) {
            return false;
        }
        if (projectDB.findById(project.getId()).isEmpty() || userDB.findById(user.getId()).isEmpty()) {
            return false;
        }
        if (userProjectDB.findByUserAndProject(user, project) != null) {
            return false;
        }
        UserProject up = new UserProject(user, project);
        up.setRole(Role.PARTICIPANT);
        up.setSince(LocalDateTime.now());
        userProjectDB.save(up);
        return true;
    }

    @Override
    public boolean dropParticipant(Project project, User user) {
        return userProjectDB.deleteByUserAndProject(user, project) > 0;
    }

    @Override
    public Project save(Project entity) {
        return projectDB.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        return projectDB.delete(id) > 0;
    }

    @Override
    public Project findById(Long id) {
        return projectDB.findById(id).orElse(null);
    }

    @Override
    public boolean update(Project entity) {
        return projectDB.update(entity) > 0;
    }
}
