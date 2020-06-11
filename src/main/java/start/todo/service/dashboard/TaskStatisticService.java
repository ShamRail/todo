package start.todo.service.dashboard;

import start.todo.model.Statistic;
import start.todo.model.domain.Project;
import start.todo.model.domain.User;

public interface TaskStatisticService {

    Statistic formProjectStatistic(Project project);
    Statistic totalStatistic(User user);

}
