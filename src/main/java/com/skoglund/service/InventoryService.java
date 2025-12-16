package com.skoglund.service;

import com.skoglund.entity.item.Item;
import com.skoglund.repository.Inventory;
import javafx.collections.ObservableList;

public class InventoryService {
    private Inventory inventory;

    public InventoryService(){

    }

    public InventoryService(Inventory inventory){
        this.inventory = inventory;
    }

    public void addNewItem(Item item){
        inventory.addItem(item);
    }

    public void removeItem(Item item){
        inventory.removeItem(item);
    }

    public Item getItem(String itemId){
        return inventory.getItem(itemId);
    }

    public void saveItemListToFile(){
        inventory.saveItemListToFile();
    }

    public ObservableList<Item> getAvailableItems() {
        return inventory.getAvailableItems();
    }

    public void changeItemInfo(Item item, String brand, String color, boolean availability){
        item.setBrand(brand);
        item.setColor(color);
        item.setAvailable(availability);
    }



}
