package model;

public class ProprietaryAlreadyExistException extends Exception {
    public ProprietaryAlreadyExistException() {
        super();
    }

    public ProprietaryAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
