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
        Stage addNewFishingReelStage = new Stage();
        addNewFishingReelStage.initModality(Modality.APPLICATION_MODAL);
        addNewFishingReelStage.setMinWidth(700);
        addNewFishingReelStage.setMinHeight(500);
        addNewFishingReelStage.setTitle("Lägg till utrustning");

        Label titleLabel = new Label("Lägg till ett en ny fiskerulle till klubben");
        titleLabel.setStyle("-fx-font-size:22; -fx-padding: 7px;" +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");
        HBox windowTitelHBox = new HBox(titleLabel);
        windowTitelHBox.setAlignment(Pos.CENTER);

        Label fishingReelInfoLabel = new Label("Fyll i all information om fiskerullen");
        HBox windowSubTitel = new HBox(fishingReelInfoLabel);
        windowSubTitel.setAlignment(Pos.CENTER);

        VBox windowTitelsVBox = new VBox(titleLabel,windowSubTitel);
        windowTitelsVBox.setSpacing(7);

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
                addNewFishingReelStage.close();


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
        VBox fishingPoleTextFieldsVBox = new VBox(brandTextField, colorTextField, gearRatioTextField,
                reelTypeTextField, maxDragTextField);

        Button closeWindowButton = new Button("Stäng fönster");
        closeWindowButton.setOnAction(e -> addNewFishingReelStage.close());

        HBox buttonsHBox = new HBox(addItemButton, closeWindowButton);
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.setSpacing(100);


        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(15));
        rootLayout.setTop(windowTitelsVBox);
        rootLayout.setCenter(fishingPoleTextFieldsVBox);
        rootLayout.setBottom(buttonsHBox);

        Scene scene = new Scene(rootLayout);
        addNewFishingReelStage.setScene(scene);
        addNewFishingReelStage.showAndWait();
    }
}
