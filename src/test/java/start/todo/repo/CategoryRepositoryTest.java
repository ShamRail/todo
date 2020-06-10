package start.todo.repo;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import start.todo.model.domain.Category;
import start.todo.model.domain.Project;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryDB;

    @Autowired
    private ProjectRepository projectDB;

    @Test
    public void whenCreate() {
        Category category = new Category();
        category.setTitle("rail");
        categoryDB.save(category);
        Category out = categoryDB.findById(category.getId()).orElse(new Category());
        Assert.assertThat(out.getTitle(), Is.is(category.getTitle()));
    }

    @Test
    public void whenUpdate() {
        Category category = new Category();
        category.setTitle("rail");
        categoryDB.save(category);

        Category another = new Category();
        another.setId(category.getId());
        another.setTitle("name");
        categoryDB.update(another);

        Category out = categoryDB.findById(category.getId()).orElse(new Category());
        Assert.assertThat(out.getTitle(), Is.is(another.getTitle()));
    }

    @Test
    public void whenDelete() {
        Category category = new Category();
        category.setTitle("rail");
        categoryDB.save(category);
        categoryDB.delete(category.getId());
        Assert.assertThat(categoryDB.findById(category.getId()), Is.is(Optional.empty()));
    }

    @Test
    public void whenSaveWithProject() {
        Project project = new Project("t1", "d1");
        projectDB.save(project);

        Category category1 = new Category("c", "d", project);
        Category category2 = new Category("c2", "d2", project);
        categoryDB.saveAll(List.of(category1, category2));

        List<Category> categories = categoryDB.findByProject(project);
        Assert.assertThat(categories.size(), Is.is(2));
        Assert.assertThat(categories.get(0).getTitle(), Is.is(category1.getTitle()));
        Assert.assertThat(categories.get(1).getTitle(), Is.is(category2.getTitle()));
    }

}