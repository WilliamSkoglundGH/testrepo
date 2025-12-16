package com.skoglund.price;

public class YouthPrice implements PricePolicy{
    @Override
    public double getPricePerDay() {
        return 400;
    }
}
