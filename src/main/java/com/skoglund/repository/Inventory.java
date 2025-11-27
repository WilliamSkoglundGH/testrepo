package com.skoglund.repository;

import com.skoglund.entity.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Item> itemList;

    public Inventory(){
        itemList = new ArrayList<>();
    }

    public void addItem(Item item){
        itemList.add(item);
    }

}
