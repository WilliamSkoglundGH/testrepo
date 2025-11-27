package com.skoglund.entity.item;

public class FishingPole extends Item{
    private String rodLength;
    private String castingWeight;
    private String rodType;

    public FishingPole(){

    }
    public FishingPole(String brand, String color, String rodLength, String castingWeight,
                       String rodType){
        super(brand,color);
        this.rodLength = rodLength;
        this.castingWeight = castingWeight;
        this.rodType = rodType;
    }
}
