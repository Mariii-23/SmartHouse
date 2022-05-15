package model;

public class EnergySupplierDoesNotExistException extends Exception {
    public EnergySupplierDoesNotExistException() {
        super();
    }

    public EnergySupplierDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
