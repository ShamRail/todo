package start.todo.repo;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import start.todo.model.domain.*;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskRepositoryTest {


    @Autowired
    private TaskRepository taskDB;

    @Autowired
    private GroupRepository groupDB;

    @Autowired
    private CategoryRepository categoryDB;

    @Autowired
    private ProjectRepository projectDB;

    @Test
    public void whenCreate() {
        Task task = new Task();
        task.setTitle("rail");
        taskDB.save(task);
        Task out = taskDB.findById(task.getId()).orElse(new Task());
        Assert.assertThat(out.getTitle(), Is.is(task.getTitle()));
    }

    @Test
    public void whenUpdate() {
        Task task = new Task();
        task.setTitle("rail");
        taskDB.save(task);

        Task another = new Task();
        another.setId(task.getId());
        another.setTitle("name");
        taskDB.update(another);

        Task out = taskDB.findById(task.getId()).orElse(new Task());
        Assert.assertThat(out.getTitle(), Is.is(another.getTitle()));
    }

    @Test
    public void whenDelete() {
        Task task = new Task();
        task.setTitle("rail");
        taskDB.save(task);
        taskDB.delete(task.getId());
        Assert.assertThat(taskDB.findById(task.getId()), Is.is(Optional.empty()));
    }

    @Test
    public void whenLoadByGroup() {
        Group group = new Group();
        groupDB.save(group);

        Category category = new Category();
        categoryDB.save(category);

        Project project = new Project();
        projectDB.save(project);

        Task task1 = new Task("t1").path(project, category, group);
        Task task2 = new Task("t2").path(project, category, group);
        Task task3 = new Task("t3").path(project, category, group);
        taskDB.saveAll(List.of(task1, task2, task3));

        List<Task> tasks = taskDB.findByGroup(group);
        Assert.assertThat(tasks.size(), Is.is(3));
        Assert.assertThat(tasks.get(0).getTitle(), Is.is(task1.getTitle()));
        Assert.assertThat(tasks.get(1).getTitle(), Is.is(task2.getTitle()));
        Assert.assertThat(tasks.get(2).getTitle(), Is.is(task3.getTitle()));
    }

    @Test
    public void whenLoadByCategory() {
        Group group = new Group();
        groupDB.save(group);

        Category category = new Category();
        categoryDB.save(category);

        Project project = new Project();
        projectDB.save(project);

        Task task1 = new Task("t1").path(project, category, group);
        Task task2 = new Task("t2").path(project, category, group);
        Task task3 = new Task("t3").path(project, category, group);
        taskDB.saveAll(List.of(task1, task2, task3));

        List<Task> tasks = taskDB.findByCategory(category);
        Assert.assertThat(tasks.size(), Is.is(3));
        Assert.assertThat(tasks.get(0).getTitle(), Is.is(task1.getTitle()));
        Assert.assertThat(tasks.get(1).getTitle(), Is.is(task2.getTitle()));
        Assert.assertThat(tasks.get(2).getTitle(), Is.is(task3.getTitle()));
    }

    @Test
    public void whenLoadByProject() {
        Group group = new Group();
        groupDB.save(group);

        Category category = new Category();
        categoryDB.save(category);

        Project project = new Project();
        projectDB.save(project);

        Task task1 = new Task("t1").path(project, category, group);
        Task task2 = new Task("t2").path(project, category, group);
        Task task3 = new Task("t3").path(project, category, group);
        taskDB.saveAll(List.of(task1, task2, task3));

        List<Task> tasks = taskDB.findByProject(project);
        Assert.assertThat(tasks.size(), Is.is(3));
        Assert.assertThat(tasks.get(0).getTitle(), Is.is(task1.getTitle()));
        Assert.assertThat(tasks.get(1).getTitle(), Is.is(task2.getTitle()));
        Assert.assertThat(tasks.get(2).getTitle(), Is.is(task3.getTitle()));
    }


    @Test
    public void whenSaveLoadWithContent() {
        Task task = new Task().content(
                new TaskContent("content")
        );
        taskDB.save(task);
        Assert.assertThat(
                taskDB.taskWithContent(task.getId()).getContent().getText(),
                Is.is("content")
        );
    }

}