package patterns;

import exceptions.InsufficientFundsException;
import exceptions.VendingMachineException;
import exceptions.OutOfStockException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VendingMachine {

    private static volatile VendingMachine instance;
    private final Map<String, Slot> slots;
    private double currentBalance;

    private VendingMachine() {
        this.slots = new HashMap<>();
        this.currentBalance = 0;
    }

    // Thread-safe singleton (double-checked)
    public static VendingMachine getInstance() {
        if (instance == null) {
            synchronized (VendingMachine.class) {
                if (instance == null) {
                    instance = new VendingMachine();
                }
            }
        }
        return instance;
    }

    // Добавлен для тестов и инициализации
    public void reset() {
        synchronized (this) {
            slots.clear();
            currentBalance = 0;
        }
    }

    public void loadSlot(String code, Slot slot) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Код слоту не може бути пустим");
        }
        if (slot == null) {
            throw new IllegalArgumentException("Slot не може бути null");
        }
        slots.put(code, slot);
        System.out.println("Завантажено комірку " + code + ": " + slot.getProduct().getName());
    }

    public synchronized void insertMoney(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Сума має бути додатною.");
    }
    currentBalance += amount;
    System.out.println("Внесено: " + amount + " грн. Баланс: " + currentBalance);
    }


    public synchronized void selectProduct(String code) throws VendingMachineException {
    Slot slot = slots.get(code);

    if (slot == null)
        throw new VendingMachineException("Комірки не існує: " + code);

    double price = slot.getPrice();

    if (currentBalance < price)
        throw new InsufficientFundsException(price, currentBalance);

    public synchronized void dispense() throws OutOfStockException {
        if (quantity > 0) {
        quantity--;
        System.out.println("Видано: " + product.getName());
    } else {
        throw new OutOfStockException(product.getName());
    }
    }


    currentBalance -= price;
    System.out.println("Покупка успішна. Новий баланс: " + currentBalance);
    }


    public synchronized double getChange() {
    double change = currentBalance;
    currentBalance = 0;
    return change;
    }


    public double getBalance() {
        synchronized (this) {
            return currentBalance;
        }
    }

    public Slot getSlot(String code) {
        return slots.get(code);
    }

    public Map<String, Slot> getAllSlots() {
        return Collections.unmodifiableMap(slots);
    }

    public void displayAllProducts() {
        System.out.println("\n--- Асортимент ---");
        for (Map.Entry<String, Slot> entry : slots.entrySet()) {
            String info = entry.getValue().getProduct().getDisplayInfo();
            System.out.println("[" + entry.getKey() + "] " + info + " (кількість: " + entry.getValue().getQuantity() + ")");
        }
        System.out.println("------------------\n");
    }
}
