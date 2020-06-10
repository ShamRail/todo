package start.todo.repo;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit4.SpringRunner;
import start.todo.model.domain.Project;
import start.todo.model.domain.User;
import start.todo.model.domain.UserProject;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userDB;

    @Test
    public void whenCreate() {
        User user = new User();
        user.setUsername("rail");
        userDB.save(user);
        User out = userDB.findById(user.getId()).orElse(new User());
        Assert.assertThat(out.getUsername(), Is.is(user.getUsername()));
    }

    @Test
    public void whenUpdate() {
        User user = new User();
        user.setUsername("rail");
        userDB.save(user);

        User another = new User();
        another.setId(user.getId());
        another.setUsername("name");
        userDB.update(another);

        User out = userDB.findById(user.getId()).orElse(new User());
        Assert.assertThat(out.getUsername(), Is.is(another.getUsername()));
    }

    @Test
    public void whenDelete() {
        User user = new User();
        user.setUsername("rail");
        userDB.save(user);
        userDB.delete(user.getId());
        Assert.assertThat(userDB.findById(user.getId()), Is.is(Optional.empty()));
    }

}