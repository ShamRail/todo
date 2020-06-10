package start.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import start.todo.model.domain.Project;
import start.todo.model.domain.User;
import start.todo.model.domain.UserProject;

import java.util.List;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {

    int deleteByUserAndProject(User user, Project project);

    UserProject findByUserAndProject(User user, Project project);

    @Query("select up.project from UserProject up where up.user = :user")
    List<Project> userProjects(@Param("user") User user);

    @Query("select up.user from UserProject up where up.project = :project")
    List<User> projectUsers(@Param("project") Project project);

}
