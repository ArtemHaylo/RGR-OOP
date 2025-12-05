package patterns;

import oop.Drink;
import oop.Snack;

public class JunkFoodFactory implements ProductFactory { // це конкретна реалізація абстрактної фабрики, тут будуть повертатися продукти "нездоровї" їжі
    @Override
    public Snack createSnack() {
        return new Snack("Жувальна Гумка", 35.0, 80);
    }

    @Override
    public Drink createDrink() {
        return new Drink("Coca-Cola", 28.0, 500);
    }
}