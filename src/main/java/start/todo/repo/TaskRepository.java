package start.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import start.todo.model.domain.*;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Task t set t.title = :#{#task.title}, t.description = :#{#task.description}, t.expiredDate = :#{#task.expiredDate}")
    @Transactional
    int update(@Param("task") Task task);

    @Modifying(clearAutomatically = true)
    @Query("update Task t set t.status = :sts where t.id = :id")
    @Transactional
    int updateStatus(@Param("id") Long id, @Param("sts") TaskStatus sts);

    @Modifying(clearAutomatically = true)
    @Query("delete from Task t where t.id = :id")
    @Transactional
    int delete(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("delete from Task t where t.project = :prj")
    @Transactional
    int deleteByProject(@Param("prj") Project prj);

    @Modifying(clearAutomatically = true)
    @Query("delete from Task t where t.category = :ctg")
    @Transactional
    int deleteByCategory(@Param("ctg") Category ctg);

    @Modifying(clearAutomatically = true)
    @Query("delete from Task t where t.group = :gr")
    @Transactional
    int deleteByGroup(@Param("gr") Group gr);

    @Query("select t from Task t join fetch t.content where t.id = :id")
    Task taskWithContent(@Param("id") Long id);

    List<Task> findByProject(Project project);

    List<Task> findByCategory(Category category);

    List<Task> findByGroup(Group group);

    @Query("select t from Task t " +
            "join fetch t.project p " +
            "join fetch t.category c " +
            "join fetch t.group where t.project = :prj")
    @Transactional
    List<Task> loadWithStructure(@Param("prj") Project prj);

}
