package oop;

public class Drink extends Product {

    private int volumeMl; // Об'єм у мілілітрах

    public Drink(String name, double price, int volumeMl) {
        super(name, price);
        this.volumeMl = volumeMl;
    }

    @Override
    public String getDisplayInfo() { // так само як і для снеків, але вимірбється не в грамах, а в мілілітрах
        return getName() + " (" + volumeMl + "мл) - " + getPrice() + " грн";
    }
}