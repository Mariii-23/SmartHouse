package model.smart_house;

public class EnergySupplierAlreadyExistsException extends Exception {
    public EnergySupplierAlreadyExistsException() {
    }

    public EnergySupplierAlreadyExistsException(String message) {
        super(message);
    }
}
