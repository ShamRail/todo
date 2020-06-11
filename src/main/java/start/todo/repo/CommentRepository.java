package start.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import start.todo.model.domain.Comment;
import start.todo.model.domain.Task;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Comment c set c.text = :#{#comment.text}")
    @Transactional
    int update(@Param("comment") Comment comment);

    @Modifying(clearAutomatically = true)
    @Query("delete from Comment c where c.id = :id")
    @Transactional
    int delete(@Param("id") Long id);

    List<Comment> findByTask(Task task);

}
