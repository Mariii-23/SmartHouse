package controller.parse.exceptions;

public class DateBeforeSimulationDateException extends ParseEventException {
    public DateBeforeSimulationDateException() {
        super();
    }

    public DateBeforeSimulationDateException(String message) {
        super(message);
    }
}
