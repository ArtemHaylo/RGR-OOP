package oop;

public abstract class Product {

    private String name; // назва
    private double price; // цііна

    public Product(String name, double price) {
        this.name = name;
        
        if (price <= 0) { // перевірка на те, щоб ціна була додатньою
            throw new IllegalArgumentException("Ціна має бути додатньою: " + price);
        }
        this.price = price;
    }
    public abstract String getDisplayInfo();

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

