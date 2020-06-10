package start.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidArgsException extends RuntimeException {
    public InvalidArgsException(String message) {
        super(message);
    }
}
