package patterns;

import oop.Drink;
import oop.Product;
import oop.Snack;

/**
 * Патерн Builder (Будівельник).
 * Дозволяє покроково створювати об'єкти Product.
 */
public class ProductBuilder {
    private String name;
    private double price;
    private int parameter; // вага (г) або об'єм (мл)
    private boolean isDrink;

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    // Універсальний параметр для ваги або об'єму
    public ProductBuilder setParameter(int parameter) {
        this.parameter = parameter;
        return this;
    }

    public ProductBuilder asDrink() {
        this.isDrink = true;
        return this;
    }

    public ProductBuilder asSnack() {
        this.isDrink = false;
        return this;
    }

    public Product build() {
        if (isDrink) {
            return new Drink(name, price, parameter);
        } else {
            return new Snack(name, price, parameter);
        }
    }
}