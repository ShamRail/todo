package start.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import start.todo.model.domain.Category;
import start.todo.model.domain.Project;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Modifying(clearAutomatically = true)
    @Query("update Category c set c.title = :#{#category.title}, c.description = :#{#category.description}")
    int update(@Param("category") Category category);

    @Modifying(clearAutomatically = true)
    @Query("delete from Category c where c.id = :id")
    int delete(@Param("id") Long id);

    List<Category> findByProject(Project project);

}
