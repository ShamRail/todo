package start.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import start.todo.model.domain.Project;
import start.todo.model.domain.User;
import start.todo.model.domain.UserProject;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {

    @Transactional
    int deleteByUserAndProject(User user, Project project);

    @Transactional
    int deleteByProject(Project project);

    @Transactional
    int deleteByUser(User user);

    UserProject findByUserAndProject(User user, Project project);

    @Query("select up.project from UserProject up where up.user = :user")
    List<Project> userProjects(@Param("user") User user);

    @Query("select up from UserProject up join fetch up.user where up.project = :project")
    List<UserProject> projectUsers(@Param("project") Project project);

}
