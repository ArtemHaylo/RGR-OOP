package patterns;

/**
 * Інтерфейс платіжної стратегії.
 * Реализує різні механізми оплати без зміни логіки автомата.
 */
public interface PaymentStrategy {
    /**
     * Виконати оплату на вказану суму.
     * @param amount сума до списання
     * @return true якщо оплата успішна, false інакше
     * @throws Exception у випадку технічної помилки
     */
    boolean pay(double amount) throws Exception;
}