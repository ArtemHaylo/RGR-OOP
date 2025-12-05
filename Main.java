import exceptions.InsufficientFundsException;
import exceptions.VendingMachineException;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Scanner;
import oop.Chips;
import patterns.*;

public class Main {

    private static final DecimalFormat MONEY_FMT = new DecimalFormat("#0.00");

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
        machine.loadSlot("C1", new Slot(new Chips("Чіпси Лейс", 22.5), 5));

        boolean running = true;

        System.out.println("=== ВІТАЄМО В АВТОМАТІ ===");

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    machine.displayAllProducts();
                    break;

                case "2":
                    System.out.print("Введіть суму для внесення: ");
                    String raw = scanner.nextLine().trim();
                    try {
                        double money = parseMoney(raw);
                        if (money <= 0) {
                            System.err.println("❗️ Введіть додатню суму (більше 0).");
                        } else {
                            machine.insertMoney(money);
                            System.out.println("✅ Додано: " + MONEY_FMT.format(money) + " грн. Баланс: " + MONEY_FMT.format(machine.getCurrentBalance()));
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("❗️ Введіть коректне число (наприклад 50 або 10.5).");
                    } catch (VendingMachineException e) {
                        System.err.println("❗️ Помилка: " + e.getMessage());
                    }
                    break;

                case "3":
                    System.out.print("Введіть код товару (наприклад A1): ");
                    String code = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
                    if (code.isEmpty()) {
                        System.err.println("❗️ Код не може бути пустим.");
                        break;
                    }
                    try {
                        machine.selectProduct(code);
                        System.out.println("✅ Товар видано. Поточний баланс: " + MONEY_FMT.format(machine.getCurrentBalance()) + " грн.");
                    } catch (InsufficientFundsException e) {
                        System.err.println("❗️ Недостатньо коштів: " + e.getMessage());
                    } catch (VendingMachineException e) {
                        System.err.println("❗️ Помилка: " + e.getMessage());
                    }
                    break;

                case "4":
                    double change = machine.getChange();
                    System.out.println("Решта: " + MONEY_FMT.format(change) + " грн.");
                    break;

                case "5":
                    running = false;
                    System.out.println("Дякуємо за покупку!");
                    break;

                default:
                    System.err.println("❗️ Невідома команда.");
            }

            System.out.println(); // пустая строка между итерациями
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("--- МЕНЮ ---");
        System.out.println("1. Показати всі товари");
        System.out.println("2. Внести гроші");
        System.out.println("3. Купити товар");
        System.out.println("4. Отримати решту");
        System.out.println("5. Вийти");
        System.out.print("Виберіть опцію: ");
    }

    private static double parseMoney(String raw) throws NumberFormatException {
        // Поддерживаем как десятичную точку, так и запятую
        if (raw == null || raw.isEmpty()) throw new NumberFormatException("empty");
        raw = raw.replace(',', '.').trim();
        return Double.parseDouble(raw);
    }
}