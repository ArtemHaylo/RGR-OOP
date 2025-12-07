package patterns; 

import java.util.Scanner;
import patterns.CashPayment;
import patterns.PaymentStrategy;

public class InsertMoneyCommand implements Command {
    private final VendingMachine machine;
    private final Scanner scanner;

    public InsertMoneyCommand(VendingMachine machine, Scanner scanner) {
        this.machine = machine;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введіть суму: ");
        try {
            // Зчитуємо рядок і замінюємо кому на крапку для Double
            String raw = scanner.nextLine().trim().replace(',', '.');
            if (raw.isEmpty()) return;
            
            double amount = Double.parseDouble(raw);
            PaymentStrategy cash = new CashPayment();
            machine.insertMoney(amount, cash);
        } catch (Exception e) {
            System.out.println("Помилка: треба ввести додатнє число :(");
        }
    }
}