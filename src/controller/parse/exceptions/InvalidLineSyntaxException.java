package controller.parse.exceptions;

public class InvalidLineSyntaxException extends ParseEventException {
    public InvalidLineSyntaxException() {
        super();
    }

    public InvalidLineSyntaxException(String message) {
        super(message);
    }
}
