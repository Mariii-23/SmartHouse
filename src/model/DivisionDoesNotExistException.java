package model;

public class DivisionDoesNotExistException extends Exception {
    public DivisionDoesNotExistException() {
    }

    public DivisionDoesNotExistException(String message) {
        super(message);
    }
}
