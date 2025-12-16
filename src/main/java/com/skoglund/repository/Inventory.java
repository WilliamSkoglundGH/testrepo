package com.skoglund.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.skoglund.entity.ItemData;
import com.skoglund.entity.item.FishingReel;
import com.skoglund.entity.item.FishingRod;
import com.skoglund.entity.item.Item;
import com.skoglund.entity.item.LureSet;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inventory {
    private final ObservableList<Item> itemList = FXCollections.observableArrayList();

    public Inventory(){
        loadItemListFromFile();
    }

    public ObservableList<Item> getItemList(){
        return itemList;
    }

    public void addItem(Item item){
        itemList.add(item);
    }

    public void removeItem(Item item){
        itemList.remove(item);
    }

    public void saveItemListToFile() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        List<ItemData> itemDataList = new ArrayList<>();
        for(Item item : itemList){
            ItemData itemData = item.toDataItemForm();
            itemDataList.add(itemData);
            }
        try{
            mapper.writeValue(new File("items.json"), itemDataList);
        }catch(IOException e){
            System.out.println("Att skriva till filen items.json misslyckades" +
                    e.getMessage());
            System.out.println("Utrustningslistan sparades ej till filen");
        }
    }

    public List<Item> getItemListFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try{
            File itemJsonFile = new File("items.json");
            if(!itemJsonFile.exists() || itemJsonFile.length() == 0){
                mapper.writeValue(new File("items.json"), new ArrayList<>());
            }
            List<ItemData> fromFile = Arrays.asList(mapper.readValue(new File("items.json"),
                    ItemData[].class));

            List<Item> updatedItemList = new ArrayList<>();
            Item item;
            for(ItemData itemData : fromFile){
                switch(itemData.getItemType()){
                    case "Fiskerulle":
                        item = new FishingReel(itemData.getBrand(), itemData.getColor(),
                                itemData.getGearRatio(), itemData.getReelType(), itemData.getMaxDrag());
                        item.setItemId(itemData.getItemId());
                        item.setAvailable(itemData.isAvailable());
                        updatedItemList.add(item);
                        break;

                    case "Fiskespö":
                        item = new FishingRod(itemData.getBrand(), itemData.getColor(),
                                itemData.getRodLength(), itemData.getCastingWeight(), itemData.getRodType());
                        item.setItemId(itemData.getItemId());
                        item.setAvailable(itemData.isAvailable());
                        updatedItemList.add(item);
                        break;

                    case "Betesset":
                        item = new LureSet(itemData.getBrand(), itemData.getColor(), itemData.getLureType());
                        item.setItemId(itemData.getItemId());
                        item.setAvailable(itemData.isAvailable());
                        updatedItemList.add(item);
                        break;
                }
            }
            return updatedItemList;

        }catch(IOException e){
            System.out.println("Filen: items.json kunde inte läsas in korrekt: " + e.getMessage());
            System.out.println("Felet måste åtgärdas innan applikation kan köras");
            Platform.exit();
            return null;
        }
    }

    public void loadItemListFromFile(){
        itemList.clear();
        itemList.setAll(getItemListFromFile());
    }

    public Item getItem(String itemId){
        Item foundItem = itemList.stream().filter(i -> itemId.equalsIgnoreCase(i.getItemId())).
                findFirst().get();
        return foundItem;
    }
    public ObservableList<Item> getAvailableItems() {
        ObservableList<Item> availableItems = FXCollections.observableArrayList();

        for (Item item : itemList) {
            if (item.isAvailable()) {
                availableItems.add(item);
            }
        }
        return availableItems;
    }
}
