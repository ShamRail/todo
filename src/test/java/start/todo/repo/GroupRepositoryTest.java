package start.todo.repo;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import start.todo.model.domain.*;
import start.todo.service.GroupService;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupDB;

    @Autowired
    private CategoryRepository categoryDB;

    @Autowired
    private ProjectRepository projectDB;

    @Autowired
    private TaskRepository taskDB;

    @Autowired
    private CommentRepository commentDB;

    @Test
    public void whenCreate() {
        Group group = new Group();
        group.setTitle("rail");
        groupDB.save(group);
        Group out = groupDB.findById(group.getId()).orElse(new Group());
        Assert.assertThat(out.getTitle(), Is.is(group.getTitle()));
    }

    @Test
    public void whenUpdate() {
        Group group = new Group();
        group.setTitle("rail");
        groupDB.save(group);

        Group another = new Group();
        another.setId(group.getId());
        another.setTitle("name");
        groupDB.update(another);

        Group out = groupDB.findById(group.getId()).orElse(new Group());
        Assert.assertThat(out.getTitle(), Is.is(another.getTitle()));
    }

    @Test
    public void whenDelete() {
        Group group = new Group();
        group.setTitle("rail");
        groupDB.save(group);
        groupDB.delete(group.getId());
        Assert.assertThat(groupDB.findById(group.getId()), Is.is(Optional.empty()));
    }

    @Test
    public void whenLoadByCategory() {
        Project project = new Project();
        projectDB.save(project);

        Category category = new Category();
        categoryDB.save(category);

        Group group1 = new Group("t1", "d1", category, project);
        Group group2 = new Group("t2", "d2", category, project);
        groupDB.saveAll(List.of(group1, group2));

        List<Group> groups = groupDB.findByCategory(category);
        Assert.assertThat(groups.size(), Is.is(2));
        Assert.assertThat(groups.get(0).getTitle(), Is.is(group1.getTitle()));
        Assert.assertThat(groups.get(1).getTitle(), Is.is(group2.getTitle()));
    }

    @Test
    public void whenLoadByProject() {
        Project project = new Project();
        projectDB.save(project);

        Category category = new Category();
        categoryDB.save(category);

        Group group1 = new Group("t1", "d1", category, project);
        Group group2 = new Group("t2", "d2", category, project);
        groupDB.saveAll(List.of(group1, group2));

        List<Group> groups = groupDB.findByProject(project);
        Assert.assertThat(groups.size(), Is.is(2));
        Assert.assertThat(groups.get(0).getTitle(), Is.is(group1.getTitle()));
        Assert.assertThat(groups.get(1).getTitle(), Is.is(group2.getTitle()));
    }

    @Test
    public void whenDeleteGroupTaskAlsoDeleted() {
        Group group = new Group();
        groupDB.save(group);
        Task task = new Task("t1", group);
        taskDB.save(task);
        Comment comment = new Comment("c1", task);
        commentDB.save(comment);

        commentDB.deleteByTask(task);
        taskDB.deleteByGroup(group);
        groupDB.delete(group.getId());

        Assert.assertThat(groupDB.findAll().size(), Is.is(0));
        Assert.assertThat(taskDB.findAll().size(), Is.is(0));
        Assert.assertThat(commentDB.findAll().size(), Is.is(0));

    }

}