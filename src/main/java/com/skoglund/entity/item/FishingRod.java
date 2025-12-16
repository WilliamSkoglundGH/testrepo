package com.skoglund.entity.item;

import com.skoglund.entity.ItemData;

public class FishingRod extends Item{
    private double rodLength;
    private int castingWeight;
    private String rodType;

    public FishingRod(){

    }

    public FishingRod(String brand, String color, double rodLength, int castingWeight,
                      String rodType){
        super(brand,color);
        this.rodLength = rodLength;
        this.castingWeight = castingWeight;
        this.rodType = rodType;
    }

    @Override
    public String getItemType() {
        return "Fiskespö";
    }

    @Override
    public ItemData toDataItemForm() {
        return new ItemData(getItemId(), getBrand(), getColor(), getItemType(), isAvailable(),
                rodLength, castingWeight, rodType);
    }

    //Används för GUI, i choice box
    @Override
    public String toString(){
        return getItemType();
    }
}
