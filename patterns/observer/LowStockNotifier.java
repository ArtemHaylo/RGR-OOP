package patterns.observer;

/**
 * Простий спостерігач, який виводить повідомлення про низький запас.
 */
public class LowStockNotifier implements StockObserver {
    private final int threshold;

    public LowStockNotifier(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void onStockChanged(String slotCode, int quantity) {
        if (quantity <= threshold) {
            System.out.println("⚠️ Низький запас в " + slotCode + ": " + quantity + " шт.");
        }
    }
}