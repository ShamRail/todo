package start.todo.model.view;

public class ModelView {

    public static class BasicFields {}
    public static class FieldsPath extends BasicFields {}

    // Views for Project
    public static class FieldsCategories extends BasicFields {}

    // Views for Category
    public static class FieldsProject extends BasicFields {}
    public static class FieldsProjectGroups extends FieldsProject {}

    // Views for Group
    public static class FieldsCategory extends BasicFields {}
    public static class FieldsCategoryTasks extends FieldsCategory {}

    // Views for Task
    public static class FieldsGroup extends BasicFields {}
    public static class FieldsGroupContent extends FieldsGroup {}
    public static class FieldsGroupContentComments extends FieldsGroupContent {}

}
