package oop;

public class Snack extends Product {

    private int weightGrams; // вага у грамах

    public Snack(String name, double price, int weightGrams) {
        super(name, price); 
        this.weightGrams = weightGrams;
    }

    @Override
    public String getDisplayInfo() { // це для інформації на дисплею, типу буде спочатку ім'я, потім грами і ціна в грн
        return getName() + " (" + weightGrams + "г) - " + getPrice() + " грн";
    }
}