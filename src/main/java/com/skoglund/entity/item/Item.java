package com.skoglund.entity.item;

public abstract class Item {
    private String brand;
    private String color;
    private boolean isAvailable;

    public Item(){

    }
    public Item(String brand, String color){
        this.brand = brand;
        this.color = color;
        this.isAvailable = true;
    }


}
