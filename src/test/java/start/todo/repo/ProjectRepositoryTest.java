package start.todo.repo;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import start.todo.model.domain.Project;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectDB;

    @Test
    public void whenCreate() {
        Project user = new Project();
        user.setTitle("rail");
        projectDB.save(user);
        Project out = projectDB.findById(user.getId()).orElse(new Project());
        Assert.assertThat(out.getTitle(), Is.is(user.getTitle()));
    }

    @Test
    public void whenUpdate() {
        Project user = new Project();
        user.setTitle("rail");
        projectDB.save(user);

        Project another = new Project();
        another.setId(user.getId());
        another.setTitle("name");
        projectDB.update(another);

        Project out = projectDB.findById(user.getId()).orElse(new Project());
        Assert.assertThat(out.getTitle(), Is.is(another.getTitle()));
    }

    @Test
    public void whenDelete() {
        Project user = new Project();
        user.setTitle("rail");
        projectDB.save(user);
        projectDB.delete(user.getId());
        Assert.assertThat(projectDB.findById(user.getId()), Is.is(Optional.empty()));
    }

}