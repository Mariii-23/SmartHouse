package model.smart_house.smart_devices.bulb;

public class NoSuchToneException extends Exception {
    public NoSuchToneException() {
        super();
    }

    public NoSuchToneException(String message) {
        super(message);
    }
}
