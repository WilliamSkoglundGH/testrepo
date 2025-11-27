package com.skoglund.entity.item;

public class LureSet extends Item {
    private String lureType;
    private final int amountOfLures = 5;

    public LureSet(){

    }
    public LureSet(String brand, String color, String lureType){
        super(brand, color);
        this.lureType = lureType;
    }
}
