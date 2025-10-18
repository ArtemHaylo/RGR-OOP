package patterns;

import oop.Drink;
import oop.Snack;

public class HealthyFoodFactory implements ProductFactory { // так само як і JunkFoodFactory, але зі здоровою їжею
    @Override
    public Snack createSnack() {
        return new Snack("Протеїновий батончик", 40.0, 50);
    }

    @Override
    public Drink createDrink() {
        return new Drink("Вода Бювєт", 20.0, 500);
    }
}