package model;

public class ProprietaryDoesNotExistException extends Exception {
    public ProprietaryDoesNotExistException() {
        super();
    }

    public ProprietaryDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
