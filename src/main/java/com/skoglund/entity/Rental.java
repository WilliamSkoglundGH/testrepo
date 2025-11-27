package com.skoglund.entity;

import com.skoglund.entity.item.Item;

public class Rental {
    Member member;
    Item item;

    public Rental(){

    }
    public Rental(Member member, Item item){
        this.member = member;
        this.item = item;
    }
}
