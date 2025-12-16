package com.skoglund.price;

public class AdultPrice implements PricePolicy{
    @Override
    public double getPricePerDay() {
        return 500;
    }
}
