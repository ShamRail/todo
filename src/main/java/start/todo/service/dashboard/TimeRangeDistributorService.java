package start.todo.service.dashboard;

import start.todo.model.domain.Task;
import start.todo.model.domain.User;

import java.util.List;

public interface TimeRangeDistributorService {

    List<Task> todayTasks(User user);
    List<Task> thisWeekTasks(User user);
    List<Task> outDatedTasks(User user);

}
