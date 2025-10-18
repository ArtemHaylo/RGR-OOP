package exceptions;

public class InsufficientFundsException extends VendingMachineException { // також наслідуєється від VendingMachineException і виникає коли користувач вніс недостатньо коштів
    public InsufficientFundsException(double needed, double provided) {
        super("Недостатньо коштів. Потрібно " + needed + " грн, внесено " + provided + " грн.");
    }
}