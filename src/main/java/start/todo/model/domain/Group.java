package start.todo.model.domain;

import com.fasterxml.jackson.annotation.JsonView;
import start.todo.model.view.ModelView;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ModelView.BasicFields.class)
    private Long id;

    @JsonView(ModelView.BasicFields.class)
    private String title;

    @JsonView(ModelView.BasicFields.class)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @OneToMany(mappedBy = "group")
    private List<Task> groups = new LinkedList<>();

    public Group() {}

    public Group(String title, String description, Category category, Project project) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.project = project;
    }

    public Group titleAndDescription(String title, String description) {
        this.title = title;
        this.description = description;
        return this;
    }

    public Group path(Project project, Category category) {
        this.project = project;
        this.category = category;
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

    public List<Task> getGroups() {
        return groups;
    }

    public void setGroups(List<Task> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id) &&
                Objects.equals(title, group.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
