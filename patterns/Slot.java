package patterns;

import exceptions.OutOfStockException;
import oop.Product;

public class Slot { // цей клас буде зберігати інформацію про конкретний продукт та його кількість
    private Product product;
    private int quantity;

    public Slot(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be >= 0");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public double getPrice() {
        return product.getPrice();
    }

    // Новый геттер количества — нужен для подсчётов и тестов
    public int getQuantity() {
        return quantity;
    }

    public void dispense() throws OutOfStockException {
        if (quantity > 0) {
            quantity--;
            System.out.println("Видано: " + product.getName());
        } else {
            throw new OutOfStockException(product.getName());
        }
    }
}