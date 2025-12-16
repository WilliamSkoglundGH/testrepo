package com.skoglund.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.skoglund.entity.Rental;
import com.skoglund.entity.item.Item;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RentalRegistry {
    private ObservableList<Rental> allRentals = FXCollections.observableArrayList();

    public RentalRegistry(){
        loadRentalsFromFile();
    }

    public ObservableList<Rental> getAllRentals(){
        return allRentals;
    }

    public void addNewRental(Rental rental){
        allRentals.add(rental);
    }

    private List<Rental> getRentalsFromFile(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try{
            File rentalJsonFile = new File("rentals.json");
            if(!rentalJsonFile.exists() || rentalJsonFile.length() == 0){
                mapper.writeValue(rentalJsonFile, new ArrayList<>());
            }
            List<Rental> fromFile = Arrays.asList(mapper.readValue(new File("rentals.json"),
                    Rental[].class));
            return fromFile;
        }catch(IOException e){
            System.out.println("Filen: rentals.json kunde inte läsas in korrekt: " + e.getMessage());
            System.out.println("Felet måste åtgärdas innan applikation kan köras");
            Platform.exit();
            return null;
        }
    }

    public void saveRentalListToFile(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try{
            mapper.writeValue(new File("rentals.json"), allRentals);
        }catch(IOException e){
            System.out.println("Att skriva till filen rentals.json misslyckades" +
                    e.getMessage());
            System.out.println("Uthyrningsregistret sparades ej till filen");
        }
    }

    public void loadRentalsFromFile(){
        allRentals.clear();
        allRentals.setAll(getRentalsFromFile());
    }

    public ObservableList<Rental> getActiveRentals() {
        ObservableList<Rental> activeRentals = FXCollections.observableArrayList();

        for (Rental rental : allRentals) {
            if (rental.isActiveRental()) {
                activeRentals.add(rental);
            }
        }
        return activeRentals;
    }
}
