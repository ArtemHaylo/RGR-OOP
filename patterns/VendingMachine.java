package patterns;

import exceptions.*;
import java.util.HashMap;
import java.util.Map;
import patterns.PaymentStrategy;

public class VendingMachine {
    private static volatile VendingMachine instance;
    private final Map<String, Slot> slots;
    private double currentBalance;

    private VendingMachine() {
        this.slots = new HashMap<>();
        this.currentBalance = 0;
    }

    public static VendingMachine getInstance() {
        if (instance == null) {
            synchronized (VendingMachine.class) {
                if (instance == null) instance = new VendingMachine();
            }
        }
        return instance;
    }

    public synchronized void loadSlot(String code, Slot slot) {
        slots.put(code.trim().toUpperCase(), slot);
    }

    public synchronized void reset() {
        slots.clear();
        currentBalance = 0;
    }

    // === ОЖИВЛЕННЯ STRATEGY ===
    // Тепер ми передаємо стратегію оплати
    public synchronized void insertMoney(double amount, PaymentStrategy strategy) throws Exception {
        if (amount <= 0) throw new VendingMachineException("Сума має бути > 0");
        
        // Використовуємо стратегію для проведення транзакції
        boolean success = strategy.pay(amount);
        
        if (success) {
            currentBalance += amount;
            System.out.println("Баланс поповнено на: " + amount + " грн. Загалом: " + currentBalance);
        } else {
            System.err.println("Помилка оплати через обрану стратегію.");
        }
    }

    public synchronized void selectProduct(String code) throws VendingMachineException {
        String key = code.trim().toUpperCase();
        Slot slot = slots.get(key);
        if (slot == null) throw new VendingMachineException("Невірна комірка: " + key);

        double price = slot.getPrice();
        if (currentBalance < price) {
            throw new InsufficientFundsException(price, currentBalance);
        }

        try {
            slot.dispense(); // Це викличе Observer, якщо налаштовано
        } catch (OutOfStockException e) {
            throw e;
        }

        currentBalance -= price;
        System.out.println("Залишок: " + currentBalance + " грн.");
    }

    public synchronized double getChange() {
        double change = currentBalance;
        currentBalance = 0;
        return change;
    }

    public synchronized void displayAllProducts() {
        System.out.println("\n--- Асортимент ---");
        for (Map.Entry<String, Slot> entry : slots.entrySet()) {
            Slot slot = entry.getValue();
            System.out.println("[" + entry.getKey() + "] " + entry.getValue().getProduct().getDisplayInfo() + " Кількість:" + slot.GetQuantity());
        }
        System.out.println("------------------\n");
    }
    
    // Метод для отримання слоту (потрібен для налаштування Observer у Main)
    public synchronized Slot getSlot(String code) {
        return slots.get(code.trim().toUpperCase());
    }
    
    public synchronized double getCurrentBalance() { return currentBalance; }
}