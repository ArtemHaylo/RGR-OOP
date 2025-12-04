import exceptions.InsufficientFundsException;
import exceptions.OutOfStockException;
import exceptions.VendingMachineException;
import oop.Snack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import patterns.*;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {

    private VendingMachine machine;
    private ProductFactory junk;
    private ProductFactory healthy;

    @BeforeEach
    void setUp() {
        machine = VendingMachine.getInstance();
        machine.reset();

        junk = new JunkFoodFactory();
        healthy = new HealthyFoodFactory();

        machine.loadSlot("A1", new Slot(junk.createSnack(), 10));
        machine.loadSlot("A2", new Slot(junk.createDrink(), 5));
        machine.loadSlot("B1", new Slot(healthy.createSnack(), 7));
        machine.loadSlot("B2", new Slot(healthy.createDrink(), 8));
    }

    @Test
    void testSingleton() {
        VendingMachine other = VendingMachine.getInstance();
        assertSame(machine, other);
    }

    @Test
    void testSuccessfulPurchaseAndChange() throws VendingMachineException {
        machine.insertMoney(50);
        machine.selectProduct("A1"); // price 35
        // оставшийся баланс 15, getChange сбросит его
        assertEquals(15.0, machine.getChange(), 1e-6);
        // количество уменьшилось на 1
        assertEquals(9, machine.getSlot("A1").getQuantity());
    }

    @Test
    void testInsertNegativeMoneyThrows() {
        VendingMachineException ex = assertThrows(VendingMachineException.class, () -> machine.insertMoney(-10));
        assertTrue(ex.getMessage().toLowerCase().contains("додатнь"));
    }

    @Test
    void testInsufficientFundsThrows() throws VendingMachineException {
        machine.insertMoney(10);
        assertThrows(InsufficientFundsException.class, () -> machine.selectProduct("B1"));
        // баланс не должен быть списан — getChange вернёт 10
        assertEquals(10.0, machine.getChange(), 1e-6);
    }

    @Test
    void testOutOfStockThrows() throws VendingMachineException {
        machine.insertMoney(500);
        // A2 в загрузке 5 штук
        for (int i = 0; i < 5; i++) {
            machine.selectProduct("A2");
        }
        assertThrows(OutOfStockException.class, () -> machine.selectProduct("A2"));
    }

    @Test
    void testInvalidSlotThrows() {
        VendingMachineException ex = assertThrows(VendingMachineException.class, () -> machine.selectProduct("Z9"));
        assertTrue(ex.getMessage().contains("не існує") || ex.getMessage().contains("не существует") || ex.getMessage().toLowerCase().contains("z9"));
    }

    @Test
    void testSlotConstructorValidation() {
        Snack s = new Snack("Тест", 10.0, 50);
        assertThrows(IllegalArgumentException.class, () -> new Slot(s, -1));
        assertThrows(IllegalArgumentException.class, () -> new Slot(null, 1));
    }

    @Test
    void testProductPriceValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Snack("Bad", -5.0, 10));
    }

    @Test
    void testFactoriesCreateDifferentNames() {
        assertNotEquals(junk.createSnack().getName(), healthy.createSnack().getName());
        assertNotEquals(junk.createDrink().getName(), healthy.createDrink().getName());
    }

    @Test
    void testInsertMoneyAccumulates() throws VendingMachineException {
        machine.insertMoney(10);
        machine.insertMoney(15.5);
        assertEquals(25.5, machine.getBalance(), 1e-6);
    }
}
