package start.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import start.todo.model.domain.Category;
import start.todo.model.domain.Project;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Category c set c.title = :#{#category.title}, c.description = :#{#category.description}")
    @Transactional
    int update(@Param("category") Category category);

    @Modifying(clearAutomatically = true)
    @Query("delete from Category c where c.id = :id")
    @Transactional
    int delete(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("delete from Category c where c.project = :prj")
    @Transactional
    int deleteByProject(@Param("prj") Project prj);

    List<Category> findByProject(Project project);

}
