package start.todo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import start.todo.model.domain.User;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update User u set " +
            "u.username = :#{#user.username}, " +
            "u.email = :#{#user.email}, " +
            "u.password = :#{#user.username} " +
            "where u.id = :#{#user.id}")
    @Transactional
    int update(@Param("user") User user);

    @Modifying(clearAutomatically = true)
    @Query("delete from User u where u.id = :id")
    @Transactional
    int delete(@Param("id") Long id);

    User findByEmail(String email);

}
