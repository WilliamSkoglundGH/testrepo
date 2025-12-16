package com.skoglund.gui.sceneWindows.itemSceneWindows;

import com.skoglund.gui.sceneWindows.sharedWindows.ConfirmationWindow;
import com.skoglund.service.InventoryService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChoseNewItemWindow {
    private InventoryService inventoryService;
    private ConfirmationWindow confirmationWindow;

    //GUI fönster specifika för detta fönster
    private AddNewFishingRodWindow addNewFishingRodWindow;
    private AddNewFishingReelWindow addNewFishingReelWindow;
    private AddNewLureSetWindow addNewLureSetWindow;

    public ChoseNewItemWindow(){

    }
    public ChoseNewItemWindow(InventoryService inventoryService, ConfirmationWindow confirmationWindow){
        this.inventoryService = inventoryService;
        this.confirmationWindow = confirmationWindow;
    }

    public void showAddNewItemWindow(){
        Stage addNewItem = new Stage();
        addNewItem.initModality(Modality.APPLICATION_MODAL);
        addNewItem.setMinWidth(500);
        addNewItem.setMinHeight(400);
        addNewItem.setTitle("Välj utrustning");

        Label titel = new Label("Välj utrustning att lägga till");
        titel.setStyle("-fx-font-size:22; -fx-padding: 7px;" +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");
        HBox windowTitel = new HBox(titel);
        windowTitel.setAlignment(Pos.CENTER);

        Label choseItem = new Label("Välj utrustningstyp:");
        ChoiceBox<String> itemChoiceBox = new ChoiceBox<>();
        itemChoiceBox.getItems().addAll("Fiskespö", "Fiskerulle", "Betesset");

        VBox choseItemm = new VBox(choseItem,itemChoiceBox);
        choseItemm.setSpacing(7);
        choseItemm.setAlignment(Pos.CENTER);

        Button showNextWindowButton = new Button("Nästa");
        showNextWindowButton.setStyle("-fx-background-color: green;" +
                ";-fx-text-fill: white; -fx-font-weight: bold;");
        showNextWindowButton.setOnAction(e -> {
            String chosenItem = itemChoiceBox.getValue();
            if(chosenItem == null){
                confirmationWindow.showConfirmationWindow("Vald utrustning saknas",
                        "Vald utrustning saknas",
                        "Du måste välja en utrustning från valrutan");
            }
            else{
                if(chosenItem.equalsIgnoreCase("Fiskespö")){
                    addNewFishingRodWindow = new AddNewFishingRodWindow(inventoryService,confirmationWindow);
                    addNewFishingRodWindow.showAddNewFishingRodWindow();
                }
                if(chosenItem.equalsIgnoreCase("Fiskerulle")){
                    addNewFishingReelWindow = new AddNewFishingReelWindow(inventoryService, confirmationWindow);
                    addNewFishingReelWindow.showAddNewFishingReelWindow();
                }
                if(chosenItem.equalsIgnoreCase("Betesset")){
                    addNewLureSetWindow = new AddNewLureSetWindow(inventoryService, confirmationWindow);
                    addNewLureSetWindow.showAddNewLureSetWindow();
                }
            }
        });
        Button closeWindow = new Button("Stäng fönster");
        closeWindow.setStyle("-fx-background-color: red;" +
                ";-fx-text-fill: white; -fx-font-weight: bold;");
        closeWindow.setOnAction(e -> addNewItem.close());
        HBox buttons = new HBox(closeWindow, showNextWindowButton);
        buttons.setSpacing(100);
        buttons.setAlignment(Pos.CENTER);

        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(15));
        rootLayout.setTop(windowTitel);
        rootLayout.setCenter(choseItemm);
        rootLayout.setBottom(buttons);
        Scene scene = new Scene(rootLayout);
        addNewItem.setScene(scene);
        addNewItem.showAndWait();
    }
}
