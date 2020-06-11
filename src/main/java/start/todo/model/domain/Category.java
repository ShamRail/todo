package start.todo.model.domain;

import com.fasterxml.jackson.annotation.JsonView;
import start.todo.model.view.ModelView;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ModelView.BasicFields.class)
    private Long id;

    @JsonView(ModelView.BasicFields.class)
    private String title;

    @JsonView(ModelView.BasicFields.class)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(value = {ModelView.FieldsProject.class, ModelView.FieldsProjectGroups.class})
    private Project project;

    @OneToMany(mappedBy = "category")
    @JsonView(ModelView.FieldsProjectGroups.class)
    private List<Group> groups = new LinkedList<>();

    public Category() {}

    public Category(String title, String description, Project project) {
        this.title = title;
        this.description = description;
        this.project = project;
    }

    public Category(Long categoryId) {
        this.id = categoryId;
    }

    public static Category idStub(Long categoryId) {
        return new Category(categoryId);
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
