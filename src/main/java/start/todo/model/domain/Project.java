package start.todo.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import start.todo.model.view.ModelView;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ModelView.BasicFields.class)
    private Long id;

    @JsonView(ModelView.BasicFields.class)
    private String title;

    @JsonView(ModelView.BasicFields.class)
    private String description;

    @JsonView(ModelView.BasicFields.class)
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<UserProject> users = new LinkedList<>();

    @OneToMany(mappedBy = "project")
    @JsonView(ModelView.FieldsCategories.class)
    private List<Category> categories = new LinkedList<>();

    public Project() {}

    public Project(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Project(String title, String description, LocalDateTime createDate) {
        this.title = title;
        this.description = description;
        this.createDate = createDate;
    }

    public Project(Long projectId) {
        this.id = projectId;
    }

    public static Project idStub(Long projectId) {
        return new Project(projectId);
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public List<UserProject> getUsers() {
        return users;
    }

    public void setUsers(List<UserProject> users) {
        this.users = users;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) &&
                Objects.equals(title, project.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
