package start.todo.model.view;

public class ModelView {

    public static class BasicFields {}
    public static class Children {}
    public static class Participants {}

    // Views for Project
    public static class UserOwner extends BasicFields {}
    public static class ProjectOwner extends UserOwner {}
    public static class ProjectOwnerCategories extends ProjectOwner {}

    // Views for Category
    public static class CategoryProject extends BasicFields {}
    public static class CategoryProjectGroups extends CategoryProject {}

    // Views for Group
    public static class GroupCategory extends BasicFields {}
    public static class GroupCategoryTask extends GroupCategory {}

    // Views for Task
    public static class TaskGroup extends BasicFields {}
    public static class TaskGroupContent extends TaskGroup {}
    public static class TaskGroupContentComments extends TaskGroupContent {}

    // Views for comment


}
