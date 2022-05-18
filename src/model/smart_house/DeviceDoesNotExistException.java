package model.smart_house;

public class DeviceDoesNotExistException extends Exception {
    public DeviceDoesNotExistException() {
    }

    public DeviceDoesNotExistException(String message) {
        super(message);
    }
}
