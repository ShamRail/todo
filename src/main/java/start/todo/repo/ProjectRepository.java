package start.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import start.todo.model.domain.Project;
import start.todo.model.domain.User;

import javax.transaction.Transactional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Project p set p.title = :#{#project.title}, p.description = :#{#project.description}")
    @Transactional
    int update(@Param("project") Project project);

    @Modifying(clearAutomatically = true)
    @Query("delete from Project p where p.id = :id")
    @Transactional
    int delete(@Param("id") Long id);

}
