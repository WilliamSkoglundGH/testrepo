package com.skoglund.entity;

public class Rental {
    private String memberId;
    private String itemId;
    private int rentalTime;
    private double price;
    private boolean activeRental;



    public Rental(){

    }
    public Rental(String memberId, String itemId, int rentalTime, double price){
        this.memberId = memberId;
        this.itemId = itemId;
        this.rentalTime = rentalTime;
        this.price = price;
        this.activeRental = true;
    }

    public String getMemberId(){
        return memberId;
    }

    public String getItemId() {
        return itemId;
    }

    public int getRentalTime() {
        return rentalTime;
    }

    public double getPrice() {
        return price;
    }

    public boolean isActiveRental() {
        return activeRental;
    }

    public void setToFinished(){
        activeRental = false;
    }

    //Getters för tableviewen
    /*public String getItemType(){
        return item.getItemType();
    }

     */
}
