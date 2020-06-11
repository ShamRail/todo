package start.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import start.todo.model.domain.Category;
import start.todo.model.domain.Group;
import start.todo.model.domain.Project;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Group g set g.title = :#{#group.title}, g.description = :#{#group.description}")
    @Transactional
    int update(@Param("group") Group group);

    @Modifying(clearAutomatically = true)
    @Query("delete from Group g where g.id = :id")
    @Transactional
    int delete(@Param("id") Long id);

    List<Group> findByProject(Project project);

    List<Group> findByCategory(Category category);


}
