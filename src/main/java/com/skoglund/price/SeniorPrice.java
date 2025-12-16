package com.skoglund.price;

public class SeniorPrice implements PricePolicy{
    @Override
    public double getPricePerDay() {
        return 350;
    }
}
