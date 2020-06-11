package start.todo.model.domain;

import com.fasterxml.jackson.annotation.JsonView;
import start.todo.model.view.ModelView;
import javax.persistence.*;
import java.util.Objects;

@Entity
public class TaskContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    @JsonView(ModelView.FieldsGroupContent.class)
    private String text;

    @OneToOne(mappedBy = "content")
    private Task task;

    public TaskContent() {}

    public TaskContent(String text) {
        this.text = text;
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
        TaskContent that = (TaskContent) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
