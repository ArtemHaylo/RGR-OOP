import exceptions.*;
import patterns.*;

public class Main {
    public static void main(String[] args) {
        
        VendingMachine machine = VendingMachine.getInstance(); //отрмуємо єдиний екземпляр автомата

        ProductFactory junkFoodFactory = new JunkFoodFactory(); // створюємо фабрику нездорової їжі
        
        ProductFactory healthyFoodFactory = new HealthyFoodFactory(); // створюємо фабрику здорової їжі

        //завантажуємо автомат, використовуючи Фабричні методи
        //ми не пишемо "new Snack(...)", ми просимо фабрику
        machine.loadSlot("A1", new Slot(junkFoodFactory.createSnack(), 10)); // чіпси
        machine.loadSlot("A2", new Slot(junkFoodFactory.createDrink(), 5));  // кола
        machine.loadSlot("B1", new Slot(healthyFoodFactory.createSnack(), 7)); // батончик
        machine.loadSlot("B2", new Slot(healthyFoodFactory.createDrink(), 8)); // вода

        machine.displayAllProducts();

        System.out.println("--- Сценарій 1: Успішна покупка ---");
        try {
            machine.insertMoney(50); // вносимо 50 грн
            machine.selectProduct("A1"); // купуємо чіпси (35.0)
        
        } catch (VendingMachineException e) {
            System.err.println("Помилка покупки: " + e.getMessage());
        }
        System.out.println("Решта: " + machine.getChange() + " грн.\n"); // 15.0


        System.out.println("--- Сценарій 2: Недостатньо коштів ---");
        try {
            machine.insertMoney(20); // вносимо 20 грн
            machine.selectProduct("B1"); // пробуємо купити батончик (40.0)
        
        } catch (InsufficientFundsException e) {
            System.err.println("ПЕРЕХОПЛЕНО: " + e.getMessage()); 
        } catch (VendingMachineException e) {
            System.err.println("Помилка покупки: " + e.getMessage());
        }
        System.out.println("Решта: " + machine.getChange() + " грн.\n"); // 20.0 (гроші повертаються)


        System.out.println("--- Сценарій 3: Товар закінчився ---");
        try {
            machine.insertMoney(200); // вносимо багато грошей
            machine.selectProduct("A2"); // 5
            machine.selectProduct("A2"); // 4
            machine.selectProduct("A2"); // 3
            machine.selectProduct("A2"); // 2
            machine.selectProduct("A2"); // 1
            System.out.println("...купуємо останню Колу...");
            machine.selectProduct("A2"); // 0 - зараз буде помилка
        
        } catch (OutOfStockException e) {
            System.err.println("ПЕРЕХОПЛЕНО: " + e.getMessage()); 
        } catch (VendingMachineException e) {
            System.err.println("Помилка покупки: " + e.getMessage());
        }
        System.out.println("Решта: " + machine.getChange() + " грн.\n");
    }
}