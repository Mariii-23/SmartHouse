package model.smart_house;

public class DeviceNotExistException extends Exception {
    public DeviceNotExistException() {
    }

    public DeviceNotExistException(String message) {
        super(message);
    }
}
