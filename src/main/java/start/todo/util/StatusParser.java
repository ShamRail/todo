package start.todo.util;

import start.todo.model.domain.TaskStatus;

public class StatusParser {

    public static TaskStatus parse(String in) {
        TaskStatus sts = null;
        switch (in) {
            case "COMPLETED":
                sts = TaskStatus.COMPLETED;
                break;
            case "IN_PROGRESS":
                sts = TaskStatus.IN_PROGRESS;
                break;
        }
        return sts;
    }

}
