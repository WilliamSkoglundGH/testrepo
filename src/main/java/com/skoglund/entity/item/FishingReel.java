package com.skoglund.entity.item;


import com.skoglund.entity.ItemData;

public class FishingReel extends Item{
    private String gearRatio;
    private String reelType;
    private int maxDrag;

    public FishingReel(){

    }

    public FishingReel(String brand, String color, String gearRatio, String reelType,
                       int maxDrag){
        super(brand, color);
        this.gearRatio = gearRatio;
        this.reelType = reelType;
        this.maxDrag = maxDrag;
    }

    @Override
    public String getItemType() {
        return "Fiskerulle";
    }

    @Override
    public ItemData toDataItemForm() {
        return new ItemData(getItemId(),getBrand(), getColor(), getItemType(), isAvailable(),
                gearRatio, reelType, maxDrag);
    }

    //Används för GUI, i choice box
    @Override
    public String toString(){
        return getItemType();
    }
}
