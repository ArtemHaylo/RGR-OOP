package patterns;

import oop.Drink;
import oop.Snack;

public interface ProductFactory { // це юуде інтерфейс, який описує фаюрику, яка вміє створювати і снеки, і напої
    Snack createSnack();
    Drink createDrink();
}

