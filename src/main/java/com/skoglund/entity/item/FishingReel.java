package com.skoglund.entity.item;

public class FishingReel extends Item{
    private String gearRatio;
    private String reelType;
    private String maxDrag;

    public FishingReel(){

    }
    public FishingReel(String brand, String color, String gearRatio, String reelType,
                       String maxDrag){
        super(brand, color);
        this.gearRatio = gearRatio;
        this.reelType = reelType;
        this.maxDrag = maxDrag;
    }
}
