package start.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import start.todo.model.domain.Task;
import start.todo.model.domain.TaskContent;

import javax.transaction.Transactional;

@Repository
public interface TaskContentRepository extends JpaRepository<TaskContent, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update TaskContent tc set tc.text = :text where tc.id = :id")
    @Transactional
    int update(@Param("id") Long id, String text);

}
