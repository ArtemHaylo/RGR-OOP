package patterns;

import exceptions.InsufficientFundsException;
import exceptions.OutOfStockException;
import exceptions.VendingMachineException;
import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
    private static volatile VendingMachine instance; // потокобезопасный синглтон
    private final Map<String, Slot> slots;
    private double currentBalance;

    private VendingMachine() {
        this.slots = new HashMap<>();
        this.currentBalance = 0;
    }

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

    // Загружаем слот; код нормализуется в верхний регистр
    public synchronized void loadSlot(String code, Slot slot) {
        if (code == null || slot == null) {
            System.err.println("Невірні аргументи для loadSlot");
            return;
        }
        String key = code.trim().toUpperCase();
        slots.put(key, slot);
        System.out.println("Завантажено комірку " + key + ": " + slot.getProduct().getName());
    }

    // Очищает автомат (Main вызывал reset() при старте)
    public synchronized void reset() {
        slots.clear();
        currentBalance = 0;
        System.out.println("Вендінг-автомат очищено.");
    }

    // Внос грошей с валидацией
    public synchronized void insertMoney(double amount) throws VendingMachineException {
        if (amount <= 0) {
            throw new VendingMachineException("Сума для внесення повинна бути більшою за 0");
        }
        currentBalance += amount;
        System.out.println("Внесено: " + amount + " грн. Поточний баланс: " + currentBalance + " грн.");
    }

    // Выбор товара — теперь бросаем исключения вместо тихого println
    public synchronized void selectProduct(String code) throws VendingMachineException {
        if (code == null || code.trim().isEmpty()) {
            throw new VendingMachineException("Невірний код товару");
        }
        String key = code.trim().toUpperCase();
        Slot slot = slots.get(key);

        if (slot == null) {
            throw new VendingMachineException("Невірна комірка: " + key);
        }

        double price = slot.getPrice();

        if (currentBalance < price) {
            throw new InsufficientFundsException(price, currentBalance);
        }

        try {
            slot.dispense();
        } catch (OutOfStockException e) {
            // передаём дальше как специфичное исключение
            throw e;
        }

        currentBalance -= price;
        System.out.println("Покупка успішна. Залишок: " + currentBalance + " грн.");
    }

    // Вернуть сдачу
    public synchronized double getChange() {
        double change = currentBalance;
        currentBalance = 0;
        return change;
    }

    // Геттер баланса, Main использует его для информирования пользователя
    public synchronized double getCurrentBalance() {
        return currentBalance;
    }

    public synchronized void displayAllProducts() {
        System.out.println("\n--- Асортимент ---");
        for (Map.Entry<String, Slot> entry : slots.entrySet()) {
            String info = entry.getValue().getProduct().getDisplayInfo();
            System.out.println("[" + entry.getKey() + "] " + info);
        }
        System.out.println("------------------\n");
    }

    // Возвращает объект слота по коду (или null, если нет)
    public synchronized Slot getSlot(String code) {
        if (code == null) return null;
        return slots.get(code.trim().toUpperCase());
    }

    // Новая функциональность: суммарное количество товаров во всех слотах
    public synchronized int getTotalProductCount() {
        int total = 0;
        for (Slot s : slots.values()) {
            total += s.getQuantity();
        }
        return total;
    }

    // Печатает общее количество товаров и разбивку по слотам
    public synchronized void displayProductCounts() {
        System.out.println("\n--- Количество товаров в автомате ---");
        System.out.println("Общее количество: " + getTotalProductCount());
        for (Map.Entry<String, Slot> e : slots.entrySet()) {
            System.out.println("[" + e.getKey() + "] " + e.getValue().getProduct().getName() + " — " + e.getValue().getQuantity());
        }
        System.out.println("--------------------------------------\n");
    }
}