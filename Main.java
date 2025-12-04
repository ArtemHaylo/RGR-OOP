import exceptions.InsufficientFundsException;
import exceptions.VendingMachineException;
import java.util.Scanner;
import patterns.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        VendingMachine machine = VendingMachine.getInstance();
        // перед запуском приложения желательно очистить
        machine.reset();

        ProductFactory junkFoodFactory = new JunkFoodFactory();
        ProductFactory healthyFoodFactory = new HealthyFoodFactory();

        // Инициализация слотов
        machine.loadSlot("A1", new Slot(junkFoodFactory.createSnack(), 10));
        machine.loadSlot("A2", new Slot(junkFoodFactory.createDrink(), 5));
        machine.loadSlot("B1", new Slot(healthyFoodFactory.createSnack(), 7));
        machine.loadSlot("B2", new Slot(healthyFoodFactory.createDrink(), 8));

        boolean running = true;

        System.out.println("=== ВІТАЄМО В АВТОМАТІ ===");

        while (running) {
            System.out.println("""
                    \n--- МЕНЮ ---
                    1. Показати всі товари
                    2. Внести гроші
                    3. Купити товар
                    4. Отримати решту
                    5. Вийти
                    Виберіть опцію:
                    """);

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> machine.displayAllProducts();

                case "2" -> {
                    System.out.print("Введіть суму для внесення: ");
                    String raw = scanner.nextLine().trim();
                    try {
                        double money = Double.parseDouble(raw);
                        machine.insertMoney(money);
                    } catch (NumberFormatException e) {
                        System.err.println("❗️ Введіть коректне число (наприклад 50 або 10.5).");
                    } catch (VendingMachineException e) {
                        System.err.println("❗️ Помилка: " + e.getMessage());
                    }
                }

                case "3" -> {
                    System.out.print("Введіть код товару (наприклад A1): ");
                    String code = scanner.nextLine().trim();

                    try {
                        machine.selectProduct(code);
                    } catch (InsufficientFundsException e) {
                        System.err.println("❗️ Недостатньо коштів: " + e.getMessage());
                    } catch (VendingMachineException e) {
                        System.err.println("❗️ Помилка: " + e.getMessage());
                    }
                }

                case "4" -> {
                    System.out.println("Решта: " + machine.getChange() + " грн.");
                }

                case "5" -> {
                    running = false;
                    System.out.println("Дякуємо за покупку!");
                }

                default -> System.err.println("❗️ Невідома команда.");
            }
        }

        scanner.close();
    }
}

