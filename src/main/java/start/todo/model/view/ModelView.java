package start.todo.model.view;

public class ModelView {

    public interface BasicFields {}
    public interface FieldsPathResponsible extends FieldsResponsible {}

    // Views for Project
    public interface FieldsCategories extends BasicFields {}

    // Views for Category
    public interface FieldsProject extends BasicFields {}
    public interface FieldsProjectGroups extends FieldsProject {}

    // Views for Group
    public interface FieldsCategory extends BasicFields {}
    public interface FieldsCategoryTasks extends FieldsCategory {}

    // Views for Task

    public interface FieldsResponsible extends BasicFields {}
    public interface FieldsGroup extends BasicFields {}
    public interface FieldsGroupContent extends FieldsGroup {}
    public interface FieldsGroupContentComments extends FieldsGroupContent, FieldsResponsible {}

}
