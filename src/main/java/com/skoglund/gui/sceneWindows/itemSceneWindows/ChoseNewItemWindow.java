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
        Stage addNewItemStage = new Stage();
        addNewItemStage.initModality(Modality.APPLICATION_MODAL);
        addNewItemStage.setMinWidth(500);
        addNewItemStage.setMinHeight(400);
        addNewItemStage.setTitle("Välj utrustning");

        Label titleLabel = new Label("Välj utrustning att lägga till");
        titleLabel.setStyle("-fx-font-size:22; -fx-padding: 7px;" +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");
        HBox windowTitelHBox = new HBox(titleLabel);
        windowTitelHBox.setAlignment(Pos.CENTER);

        Label choseItemLabel = new Label("Välj utrustningstyp:");
        ChoiceBox<String> itemChoiceBox = new ChoiceBox<>();
        itemChoiceBox.getItems().addAll("Fiskespö", "Fiskerulle", "Betesset");

        VBox choseItemVBox = new VBox(choseItemLabel,itemChoiceBox);
        choseItemVBox.setSpacing(7);
        choseItemVBox.setAlignment(Pos.CENTER);

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
        Button closeWindowButton = new Button("Stäng fönster");
        closeWindowButton.setStyle("-fx-background-color: red;" +
                ";-fx-text-fill: white; -fx-font-weight: bold;");
        closeWindowButton.setOnAction(e -> addNewItemStage.close());
        HBox buttonsHBox = new HBox(closeWindowButton, showNextWindowButton);
        buttonsHBox.setSpacing(100);
        buttonsHBox.setAlignment(Pos.CENTER);

        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(15));
        rootLayout.setTop(windowTitelHBox);
        rootLayout.setCenter(choseItemVBox);
        rootLayout.setBottom(buttonsHBox);
        Scene scene = new Scene(rootLayout);
        addNewItemStage.setScene(scene);
        addNewItemStage.showAndWait();
    }
}
