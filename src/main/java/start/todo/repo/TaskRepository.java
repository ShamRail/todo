package start.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import start.todo.model.domain.*;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Task t set t.title = :#{#task.title}, t.description = :#{#task.description}, t.expiredDate = :#{#task.expiredDate}")
    int update(@Param("task") Task task);

    @Modifying(clearAutomatically = true)
    @Query("update Task t set t.status = :sts where t.id = :id")
    int updateStatus(@Param("id") Long id, @Param("sts") TaskStatus sts);

    @Modifying(clearAutomatically = true)
    @Query("delete from Task t where t.id = :id")
    int delete(@Param("id") Long id);

    List<Task> findByProject(Project project);

    List<Task> findByCategory(Category category);

    List<Task> findByGroup(Group group);

}
