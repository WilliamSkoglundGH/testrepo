package com.skoglund.entity.item;

import com.skoglund.entity.ItemData;

public class LureSet extends Item {
    private String lureType;
    private final int amountOfLures = 5;

    public LureSet(){

    }

    public LureSet(String brand, String color, String lureType){
        super(brand, color);
        this.lureType = lureType;
    }

    @Override
    public String getItemType() {
        return "Betesset";
    }

    @Override
    public ItemData toDataItemForm() {
        return new ItemData(getItemId(),getBrand(),getColor(),getItemType(),isAvailable(),lureType);
    }

    //Används för GUI, i choice box
    @Override
    public String toString(){
        return getItemType();
    }
}

