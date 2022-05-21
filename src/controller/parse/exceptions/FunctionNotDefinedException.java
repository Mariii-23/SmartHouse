package controller.parse.exceptions;

public class FunctionNotDefinedException extends ParseEventException {
    public FunctionNotDefinedException() {
        super();
    }

    public FunctionNotDefinedException(String message) {
        super(message);
    }
}
