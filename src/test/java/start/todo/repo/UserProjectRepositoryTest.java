package start.todo.repo;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import start.todo.model.domain.Project;
import start.todo.model.domain.Role;
import start.todo.model.domain.User;
import start.todo.model.domain.UserProject;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserProjectRepositoryTest {

    @Autowired
    private UserRepository userDB;

    @Autowired
    private ProjectRepository projectDB;

    @Autowired
    private UserProjectRepository userProjectDB;

    @Test
    public void whenFindUserProjects() {
        User user = new User();
        Project project = new Project("t1", "d1");
        Project projectTwo = new Project("t2", "d2");
        userDB.save(user);
        projectDB.saveAll(List.of(project, projectTwo));
        UserProject up1 = new UserProject(user, project);
        UserProject up2 = new UserProject(user, projectTwo);
        userProjectDB.saveAll(List.of(up1, up2));

        List<Project> projects = userProjectDB.userProjects(new User(user.getId()));
        Assert.assertThat(projects.get(0).getTitle(), Is.is("t1"));
        Assert.assertThat(projects.get(0).getDescription(), Is.is("d1"));
        Assert.assertThat(projects.get(1).getTitle(), Is.is("t2"));
        Assert.assertThat(projects.get(1).getDescription(), Is.is("d2"));
    }

    @Test
    public void whenFindProjectUsers() {
        User user1 = new User("u1");
        User user2 = new User("u2");
        Project project = new Project("t1", "d1");
        userDB.saveAll(List.of(user1, user2));
        projectDB.save(project);
        userProjectDB.saveAll(List.of(
                new UserProject(user1, project),
                new UserProject(user2, project)
        ));
        List<User> users = userProjectDB.projectUsers(project);
        Assert.assertThat(users.get(0).getUsername(), Is.is(user1.getUsername()));
        Assert.assertThat(users.get(1).getUsername(), Is.is(user2.getUsername()));
    }

    @Test
    public void whenDeleteByProjectAndUser() {
        User user = new User();
        Project project = new Project();
        userDB.save(user);
        projectDB.save(project);
        UserProject up = new UserProject(user, project);
        userProjectDB.save(up);
        userProjectDB.deleteByUserAndProject(user, project);
        Assert.assertThat(userProjectDB.findAll().size(), Is.is(0));
    }

    @Test
    public void whenFindByUserAndProject() {
        User user = new User();
        Project project = new Project();
        userDB.save(user);
        projectDB.save(project);
        UserProject up = new UserProject(user, project);
        up.setRole(Role.PARTICIPANT);
        userProjectDB.save(up);
        Assert.assertThat(userProjectDB.findByUserAndProject(user, project).getRole(), Is.is(up.getRole()));
    }

}