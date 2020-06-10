package start.todo.repo;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import start.todo.model.domain.Comment;
import start.todo.model.domain.Task;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {


    @Autowired
    private CommentRepository commentDB;

    @Autowired
    private TaskRepository taskDB;

    @Test
    public void whenCreate() {
        Comment comment = new Comment();
        comment.setText("rail");
        commentDB.save(comment);
        Comment out = commentDB.findById(comment.getId()).orElse(new Comment());
        Assert.assertThat(out.getText(), Is.is(comment.getText()));
    }

    @Test
    public void whenUpdate() {
        Comment comment = new Comment();
        comment.setText("rail");
        commentDB.save(comment);

        Comment another = new Comment();
        another.setId(comment.getId());
        another.setText("name");
        commentDB.update(another);

        Comment out = commentDB.findById(comment.getId()).orElse(new Comment());
        Assert.assertThat(out.getText(), Is.is(another.getText()));
    }

    @Test
    public void whenDelete() {
        Comment comment = new Comment();
        comment.setText("rail");
        commentDB.save(comment);
        commentDB.delete(comment.getId());
        Assert.assertThat(commentDB.findById(comment.getId()), Is.is(Optional.empty()));
    }

    @Test
    public void whenLoadByTask() {
        Task task = new Task();
        taskDB.save(task);
        Comment comment1 = new Comment("t1", task);
        Comment comment2 = new Comment("t2", task);
        Comment comment3 = new Comment("t3", task);
        commentDB.saveAll(List.of(comment1, comment2, comment3));

        List<Comment> comments = commentDB.findByTask(task);
        Assert.assertThat(comments.size(), Is.is(3));
        Assert.assertThat(comments.get(0).getText(), Is.is(comment1.getText()));
        Assert.assertThat(comments.get(1).getText(), Is.is(comment2.getText()));
        Assert.assertThat(comments.get(2).getText(), Is.is(comment3.getText()));
    }

}