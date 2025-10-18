package exceptions;

public class OutOfStockException extends VendingMachineException {
    public OutOfStockException(String productName) { // це вже клас, який наслідується від VendingMachineException і виникає, коли товар закінчився
        super("На жаль, товар '" + productName + "' закінчився.");
    }
}