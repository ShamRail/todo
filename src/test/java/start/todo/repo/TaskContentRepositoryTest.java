package start.todo.repo;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import start.todo.model.domain.Task;
import start.todo.model.domain.TaskContent;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskContentRepositoryTest {

    @Autowired
    private TaskRepository taskDB;

    @Autowired
    private TaskContentRepository taskContentRepository;

    @Test
    public void whenUpdateContent() {
        Task task = new Task();
        TaskContent taskContent = new TaskContent("content");
        task.setContent(taskContent);
        taskDB.save(task);

        //Long contentID = taskDB.findById(task.getId()).get().getId();

        taskContentRepository.update(taskContent.getId(), "new content");

        Task out = taskDB.taskWithContent(task.getId());
        Assert.assertThat(out.getContent().getText(), Is.is("new content"));
    }

}