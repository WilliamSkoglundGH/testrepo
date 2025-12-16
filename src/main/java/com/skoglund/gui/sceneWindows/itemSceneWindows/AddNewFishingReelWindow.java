package com.skoglund.gui.sceneWindows.itemSceneWindows;

import com.skoglund.entity.item.FishingReel;
import com.skoglund.gui.sceneWindows.sharedWindows.ConfirmationWindow;
import com.skoglund.service.InventoryService;
import com.skoglund.util.ValidationMethods;
import com.skoglund.util.exceptions.InvalidInputException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddNewFishingReelWindow {
    private InventoryService inventoryService;
    private ConfirmationWindow confirmationWindow;

    public AddNewFishingReelWindow(){

    }
    public AddNewFishingReelWindow(InventoryService inventoryService, ConfirmationWindow confirmationWindow){
        this.inventoryService = inventoryService;
        this.confirmationWindow = confirmationWindow;
    }
    public void showAddNewFishingReelWindow(){
        Stage addNewFishingReel = new Stage();
        addNewFishingReel.initModality(Modality.APPLICATION_MODAL);
        addNewFishingReel.setMinWidth(700);
        addNewFishingReel.setMinHeight(500);
        addNewFishingReel.setTitle("Lägg till utrustning");

        Label titel = new Label("Lägg till ett en ny fiskerulle till klubben");
        titel.setStyle("-fx-font-size:22; -fx-padding: 7px;" +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");
        HBox windowTitel = new HBox(titel);
        windowTitel.setAlignment(Pos.CENTER);

        Label fishingReelInfo = new Label("Fyll i all information om fiskerullen");
        HBox windowSubTitel = new HBox(fishingReelInfo);
        windowSubTitel.setAlignment(Pos.CENTER);

        VBox windowTitels = new VBox(titel,windowSubTitel);
        windowTitels.setSpacing(7);

        TextField brandTextField = new TextField();
        brandTextField.setPromptText("Ange märke:");

        TextField colorTextField = new TextField();
        colorTextField.setPromptText("Ange färg:");

        TextField gearRatioTextField = new TextField();
        gearRatioTextField.setPromptText("Ange utväxling:");

        TextField reelTypeTextField = new TextField();
        reelTypeTextField.setPromptText("Ange rullestyp:");

        TextField maxDragTextField = new TextField();
        maxDragTextField.setPromptText("Ange maxbroms:");

        Button addItemButton = new Button("Lägg till fiskerullen");
        addItemButton.setOnAction(e -> {
            String brand = brandTextField.getText();
            String color = colorTextField.getText();
            String gearRatio = gearRatioTextField.getText();
            String reelType = reelTypeTextField.getText();
            String maxDrag = maxDragTextField.getText();

            try{
                ValidationMethods.validateNewFishingReel(brand, color, gearRatio, reelType, maxDrag);
                int maxDragInt = Integer.parseInt(maxDrag);
                FishingReel newFishingReel = new FishingReel(brand, color, gearRatio, reelType, maxDragInt);
                inventoryService.addNewItem(newFishingReel);
                confirmationWindow.showConfirmationWindow("Ny fiskerulle adderad",
                        "Fiskerullen har lagts till i klubben!",
                        "Den nya fiskerullen är nu adderad till klubbens lager");


            }catch(InvalidInputException exception){
                confirmationWindow.showConfirmationWindow("Process misslyckad",
                        "Fiskerullen kunde inte läggas till i klubben!",
                        exception.getMessage());
                brandTextField.clear();
                colorTextField.clear();
                gearRatioTextField.clear();
                reelTypeTextField.clear();
                maxDragTextField.clear();
            }
        });
        VBox fishingPoleTextFields = new VBox(brandTextField, colorTextField, gearRatioTextField,
                reelTypeTextField, maxDragTextField);

        Button closeWindow = new Button("Stäng fönster");
        closeWindow.setOnAction(e -> addNewFishingReel.close());

        HBox buttons = new HBox(addItemButton, closeWindow);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(100);


        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(15));
        rootLayout.setTop(windowTitels);
        rootLayout.setCenter(fishingPoleTextFields);
        rootLayout.setBottom(buttons);

        Scene scene = new Scene(rootLayout);
        addNewFishingReel.setScene(scene);
        addNewFishingReel.showAndWait();
    }
}
