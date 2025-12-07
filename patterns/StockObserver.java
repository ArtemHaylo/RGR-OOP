package patterns;

/**
 * Інтерфейс спостерігача за запасами.
 */
public interface StockObserver {
    /**
     * Викликається, коли кількість в слоті зменшується або впала нижче порогу.
     * @param slotCode код слоту (наприклад A1)
     * @param quantity поточна кількість
     */
    void onStockChanged(String slotCode, int quantity);
}