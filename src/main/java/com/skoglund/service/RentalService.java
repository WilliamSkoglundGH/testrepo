package com.skoglund.service;

import com.skoglund.entity.Member;
import com.skoglund.entity.Rental;
import com.skoglund.entity.item.Item;
import com.skoglund.price.*;
import com.skoglund.repository.Inventory;
import com.skoglund.repository.RentalRegistry;
import javafx.collections.ObservableList;

public class RentalService {
    private RentalRegistry rentalRegistry;
    private InventoryService inventoryService;
    private MembershipService membershipService;

    public RentalService(){

    }

    public RentalService(RentalRegistry rentalRegistry, InventoryService inventoryService,
                         MembershipService membershipService){
        this.rentalRegistry = rentalRegistry;
        this.inventoryService = inventoryService;
        this.membershipService = membershipService;
    }


    public void createNewRental(Member member, Item item, int rentalTime, double rentalPrice) {
        Rental newRental = new Rental(member.getId(), item.getItemId(), rentalTime, rentalPrice);
        rentalRegistry.addNewRental(newRental);
        item.setToNotAvailable();
        member.addNewRentalToHistory("Bokad uthyrning:\n Utrustning: " + item.getItemType() +
                " | " + rentalTime + " dagar | " + rentalPrice + " kr\n");
        saveRegisterChanges();
    }

    public void finishRental(Rental rental){
        rental.setToFinished();
        Item rentalItem = inventoryService.getItem(rental.getItemId());
        rentalItem.setToAvailable();
        membershipService.searchAndGetMember(rental.getMemberId()).addNewRentalToHistory(
                "Avslutad bokning:\n Utrustning: " + rentalItem.getItemType() +
                        "| " + rental.getRentalTime() +" dagar | " + rental.getPrice() + "kr\n");
        saveRegisterChanges();

    }

    public void showActiveRentals(){

    }

    public double showClubIncome(){
        return 0;
    }

    public void showAvailableItems(){

    }

    private void saveRegisterChanges(){
        inventoryService.saveItemListToFile();
        rentalRegistry.saveRentalListToFile();
        membershipService.saveMemberListToFile();
    }

    public PricePolicy getMemberPricePolicy(Member member) {
        String memberAgeGroup = member.getAgeGroup();

        switch(memberAgeGroup.toLowerCase()){
            case "vuxen":
                return new AdultPrice();

            case "barn":
                return new ChildPrice();

            case "pensionär":
                return new SeniorPrice();

            case "ungdom":
                return new YouthPrice();

            default:
                return null;
        }
    }

    public double calculateRentalPrice(int rentalTime, PricePolicy pricePolicy) {
        return rentalTime * pricePolicy.getPricePerDay();
    }

    public ObservableList<Rental> getActiveRentals(){
        return rentalRegistry.getActiveRentals();
    }

}
