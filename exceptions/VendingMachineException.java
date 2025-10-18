package exceptions;

public class VendingMachineException extends Exception { // це клас для усіх помилок
    public VendingMachineException(String message) {
        super(message);
    }
}