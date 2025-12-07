package patterns;

import exceptions.OutOfStockException;
import java.util.ArrayList;
import java.util.List;
import oop.Product;
import patterns.StockObserver;

public class Slot {
    private Product product;
    private int quantity;
    // Список спостерігачів
    private final List<StockObserver> observers = new ArrayList<>();

    public Slot(Product product, int quantity) {
        if (product == null) throw new IllegalArgumentException("Product must not be null");
        if (quantity < 0) throw new IllegalArgumentException("Quantity must be >= 0");
        this.product = product;
        this.quantity = quantity;
    }

    // Метод для додавання спостерігача
    public void addObserver(StockObserver observer) {
        observers.add(observer);
    }

    public Product getProduct() { return product; }
    public double getPrice() { return product.getPrice(); }
    public int GetQuantity() {return quantity;}

    public void dispense() throws OutOfStockException {
        if (quantity > 0) {
            quantity--;
            System.out.println("Видано: " + product.getName());
            
            // ПОВІДОМЛЯЄМО СПОСТЕРІГАЧІВ
            notifyObservers();
        } else {
            throw new OutOfStockException(product.getName());
        }
    }

    private void notifyObservers() {
        for (StockObserver observer : observers) {
            observer.onStockChanged(product.getName(), quantity);
        }
    }
}