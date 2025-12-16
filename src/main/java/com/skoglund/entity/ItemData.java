package com.skoglund.entity;

public class ItemData {
    //Gemensamma attribut
    private String itemId;
    private String brand;
    private String color;
    private String itemType;
    private boolean available;

    //Attribut specifika för FishingReel
    private String gearRatio;
    private String reelType;
    private Integer maxDrag;

    //Attribut specifika för FishingRod
    private Double rodLength;
    private Integer castingWeight;
    private String rodType;

    //Attribut specifika för LureSet
    private String lureType;

    public ItemData(){

    }

    //Konstruktor för sparande av FishingReel
    public ItemData(String itemId, String brand, String color, String itemType, boolean available,
                    String gearRatio, String reelType, Integer maxDrag){
        this.itemId = itemId;
        this.brand = brand;
        this.color = color;
        this.itemType = itemType;
        this.available = available;
        this.gearRatio = gearRatio;
        this.reelType = reelType;
        this.maxDrag = maxDrag;
    }
    //Konstruktor för sparande av FishingRod
    public ItemData(String itemId, String brand, String color, String itemType, boolean available,
                    Double rodLength, Integer castingWeight, String rodType){
        this.itemId = itemId;
        this.brand = brand;
        this.color = color;
        this.itemType = itemType;
        this.available = available;
        this.rodLength = rodLength;
        this.castingWeight = castingWeight;
        this.rodType = rodType;
    }
    //Konstruktor för sparande av Betesset
    public ItemData(String itemId, String brand, String color, String itemType, boolean available,
                    String lureType){
        this.itemId = itemId;
        this.brand = brand;
        this.color = color;
        this.itemType = itemType;
        this.available = available;
        this.lureType = lureType;
    }

    public String getItemId() {
        return itemId;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public String getItemType() {
        return itemType;
    }

    public boolean isAvailable(){
        return available;
    }

    public String getGearRatio() {
        return gearRatio;
    }

    public String getReelType() {
        return reelType;
    }

    public Integer getMaxDrag() {
        return maxDrag;
    }

    public Double getRodLength() {
        return rodLength;
    }

    public Integer getCastingWeight() {
        return castingWeight;
    }

    public String getRodType() {
        return rodType;
    }

    public String getLureType() {
        return lureType;
    }
}
