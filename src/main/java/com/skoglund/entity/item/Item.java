package com.skoglund.entity.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.skoglund.entity.ItemData;

import java.util.Random;

public abstract class Item {
    private String itemId;
    private String brand;
    private String color;
    private boolean isAvailable;

    public Item(){

    }
    public Item(String brand, String color){
        this.itemId = createRandomId();
        this.brand = brand;
        this.color = color;
        this.isAvailable = true;
    }

    public String getItemId(){
        return itemId;
    }

    public String getBrand(){
        return this.brand;
    }

    public String getColor(){
        return this.color;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void setBrand(String brand){
        this.brand = brand;
    }

    public void setColor(String color){
        this.color = color;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    private String createRandomId(){
        StringBuilder randomId = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 7; i++){
            randomId.append(random.nextInt(10));
        }
        return randomId.toString();
    }

    public void setToNotAvailable(){
        this.isAvailable = false;
    }

    public void setToAvailable(){
        this.isAvailable = true;
    }

    public abstract String getItemType();

    public abstract ItemData toDataItemForm();

}
