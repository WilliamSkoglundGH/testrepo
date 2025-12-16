package com.skoglund.entity;

import java.util.*;

public class Member {
    private String name;
    private String ageGroup;
    private String id;
    // Dessa listor startar tomma när en medlem skapas första gången.
    // De fylls på och förändras under applikationens gång (add/remove).
    // Vid återstart laddas de med sparad data från JSON
    private final List<String> rentalHistory = new ArrayList<>();

    public Member(){

    }
    public Member(String name, String ageGroup){
        this.name = name;
        this.ageGroup = ageGroup;
        this.id = createRandomId();
    }

    public String getName() {
        return name;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public List<String> getRentalHistory() {
        return rentalHistory;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addNewRentalToHistory(String rentalInfo){
        rentalHistory.add(rentalInfo);
    }

    private String createRandomId(){
        StringBuilder randomId = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 7; i++){
            randomId.append(random.nextInt(10));
        }
        return randomId.toString();
    }
}
