package patterns;

import exceptions.OutOfStockException;
import oop.Product;

public class Slot { //цей клас юуде зберігати ігформацію про конкретний продукт та його кількість
    private Product product;
    private int quantity;

    public Slot(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public double getPrice() {
        return product.getPrice();
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
