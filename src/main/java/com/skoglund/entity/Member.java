package com.skoglund.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Member {
    private String name;
    private String ageGroup;
    private String id;
    private List<Rental> activeRentals;
    private List<String> rentalHistory;

    public Member(){

    }
    public Member(String name, String ageGroup){
        this.name = name;
        this.ageGroup = ageGroup;
        this.id = createRandomId();
        activeRentals = new ArrayList<>();
        rentalHistory = new ArrayList<>();
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
