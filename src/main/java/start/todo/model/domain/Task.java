package start.todo.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import start.todo.model.view.ModelView;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ModelView.BasicFields.class)
    private Long id;

    @JsonView(ModelView.BasicFields.class)
    private String title;

    @JsonView(ModelView.BasicFields.class)
    private String description;

    @JsonView(ModelView.BasicFields.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate expiredDate;

    @Enumerated(value = EnumType.STRING)
    @JsonView(ModelView.BasicFields.class)
    private TaskStatus status = TaskStatus.IN_PROGRESS;

    @JsonView(ModelView.BasicFields.class)
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime createDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TaskContent content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments = new LinkedList<>();

    public Task() {}

    public Task(String title) {
        this.title = title;
    }

    public Task(Long taskId) {
        this.id = taskId;
    }

    public static Task idStub(Long taskId) {
        return new Task(taskId);
    }

    public Task titleAndDescriptionAndStatus(String title, String description, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
        return this;
    }

    public Task content(TaskContent content) {
        this.content = content;
        return this;
    }

    public Task createAndExpiredDates(LocalDateTime createDate, LocalDate expiredDate) {
        this.createDate = createDate;
        this.expiredDate = expiredDate;
        return this;
    }

    public Task path(Project project, Category category, Group group) {
        this.project = project;
        this.category = category;
        this.group = group;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskContent getContent() {
        return content;
    }

    public void setContent(TaskContent content) {
        this.content = content;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
