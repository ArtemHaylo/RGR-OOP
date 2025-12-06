package patterns.payment;

/**
 * Проста реалізація готівкової оплати.
 * У нашому прикладі це просто заглушка — реальна логіка зберігається у VendingMachine (баланс).
 */
public class CashPayment implements PaymentStrategy {

    @Override
    public boolean pay(double amount) {
        // Для демонстрації: готівкова оплата завжди дозволена (реально перевіряти баланс у VendingMachine)
        return true;
    }
}