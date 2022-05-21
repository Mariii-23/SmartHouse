package model.smart_house;

public class WrongTypeOfDeviceException extends Exception {
    public WrongTypeOfDeviceException() {
        super();
    }

    public WrongTypeOfDeviceException(String message) {
        super(message);
    }
}
