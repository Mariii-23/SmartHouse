package controller.parse.exceptions;

public class FunctionWrongNumberOfArgumentsException extends ParseEventException {
    public FunctionWrongNumberOfArgumentsException() {
        super();
    }

    public FunctionWrongNumberOfArgumentsException(String message) {
        super(message);
    }
}
