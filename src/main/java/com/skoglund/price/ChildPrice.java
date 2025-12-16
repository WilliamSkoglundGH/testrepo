package com.skoglund.price;

public class ChildPrice implements PricePolicy{
    @Override
    public double getPricePerDay() {
        return 250;
    }
}
