package patterns;

import exceptions.InsufficientFundsException;
import exceptions.VendingMachineException;
import java.util.HashMap;
import java.util.Map;

public class VendingMachine {

    private static VendingMachine instance; //Singletone, тут ми гарантуємо, що в системі буде лише один екзмепляр автомата
    private Map<String, Slot> slots; //буде приблизно так - <"A1", Slot>
    private double currentBalance;

    private VendingMachine() { //приватний конструктор, щоб його не можна було викликати ззовні
        this.slots = new HashMap<>();
        this.currentBalance = 0;
    }

    public static VendingMachine getInstance() {
        if (instance == null) {
            instance = new VendingMachine();
        }
        return instance;
    }

    public void loadSlot(String code, Slot slot) {  // метод для завантаження товарів (це б робив власник автомата)
        slots.put(code, slot);
        System.out.println("Завантажено комірку " + code + ": " + slot.getProduct().getName());
    }

    public void insertMoney(double amount) { // тут вносимо кошти на баланс
        if (amount > 0) {
            currentBalance += amount;
            System.out.println("Внесено: " + amount + " грн. Поточний баланс: " + currentBalance + " грн.");
        }
    }

    public void selectProduct(String code) throws VendingMachineException {
        Slot slot = slots.get(code);

        if (slot == null) { //перевірка чи існує комірка 
            System.err.println("Помилка: Невірна комірка " + code);
            return; 
        }

        double price = slot.getPrice(); //запитуємо ціну в Slot

        if (currentBalance < price) { //перевірка балансу
            throw new InsufficientFundsException(price, currentBalance); // кидаємо наше виключення 
        }

        slot.dispense(); // видача товару

        currentBalance -= price; //списання грошей(((
        System.out.println("Покупка успішна. Залишок: " + currentBalance + " грн.");
    }

    public double getChange() {
        double change = currentBalance;
        currentBalance = 0; //онуляємо баланс при видачі решти
        return change;
    }

    public void displayAllProducts() {
        System.out.println("\n--- Асортимент ---");
        for (Map.Entry<String, Slot> entry : slots.entrySet()) { //викликаємо getDisplayInfo()
            String info = entry.getValue().getProduct().getDisplayInfo();
            System.out.println("[" + entry.getKey() + "] " + info);
        }
        System.out.println("------------------\n"); 
    }
}