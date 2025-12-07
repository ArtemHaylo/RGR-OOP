package patterns;

import exceptions.VendingMachineException;
import java.util.Scanner;

public class BuyProductCommand implements Command {
    private final VendingMachine machine;
    private final Scanner scanner;

    public BuyProductCommand(VendingMachine machine, Scanner scanner) {
        this.machine = machine;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введіть код товару (наприклад A1): ");
        String code = scanner.nextLine();
        try {
            machine.selectProduct(code);
        } catch (VendingMachineException e) {
            System.out.println("❗️ Помилка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❗️ Невідома помилка.");
        }
    }
}