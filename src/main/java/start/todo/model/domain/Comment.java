package start.todo.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import start.todo.model.view.ModelView;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ModelView.BasicFields.class)
    private Long id;

    @JsonView(ModelView.BasicFields.class)
    private String text;

    @JsonView(ModelView.BasicFields.class)
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    @JsonView(ModelView.BasicFields.class)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    public Comment() {}

    public Comment(String text, Task task) {
        this.text = text;
        this.task = task;
    }

    public Comment(String text, LocalDateTime date, User author, Task task) {
        this.text = text;
        this.date = date;
        this.author = author;
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
