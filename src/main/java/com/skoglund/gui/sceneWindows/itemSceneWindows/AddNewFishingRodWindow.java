package com.skoglund.gui.sceneWindows.itemSceneWindows;

import com.skoglund.entity.item.FishingRod;
import com.skoglund.gui.sceneWindows.sharedWindows.ConfirmationWindow;
import com.skoglund.repository.Inventory;
import com.skoglund.service.InventoryService;
import com.skoglund.util.ValidationMethods;
import com.skoglund.util.exceptions.InvalidInputException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class AddNewFishingRodWindow {
    private InventoryService inventoryService;
    private ConfirmationWindow confirmationWindow;

    public AddNewFishingRodWindow(){

    }
    public AddNewFishingRodWindow(InventoryService inventoryService, ConfirmationWindow confirmationWindow){
        this.inventoryService = inventoryService;
        this.confirmationWindow = confirmationWindow;
    }

    public void showAddNewFishingRodWindow(){
        Stage addNewFishingPole = new Stage();
        addNewFishingPole.initModality(Modality.APPLICATION_MODAL);
        addNewFishingPole.setMinWidth(700);
        addNewFishingPole.setMinHeight(500);
        addNewFishingPole.setTitle("Lägg till utrustning");

        Label titel = new Label("Lägg till ett nytt fiskespö till klubben");
        titel.setStyle("-fx-font-size:22; -fx-padding: 7px;" +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");
        HBox windowTitel = new HBox(titel);
        windowTitel.setAlignment(Pos.CENTER);

        Label fishingPoleInfo = new Label("Fyll i all information om fiskespöt");
        HBox windowSubTitel = new HBox(fishingPoleInfo);
        windowSubTitel.setAlignment(Pos.CENTER);

        VBox windowTitels = new VBox(titel,windowSubTitel);
        windowTitels.setSpacing(7);


        TextField brandTextField = new TextField();
        brandTextField.setPromptText("Ange märke:");

        TextField colorTextField = new TextField();
        colorTextField.setPromptText("Ange färg:");

        TextField rodLengthTextField = new TextField();
        rodLengthTextField.setPromptText("Ange spölängd");

        TextField castingWeightTextField = new TextField();
        castingWeightTextField.setPromptText("Ange kastvikt:");

        TextField rodTypeTextField = new TextField();
        rodTypeTextField.setPromptText("Ange spötyp:");

        Button addItemButton = new Button("Lägg till fiskespöt");
        addItemButton.setOnAction(e -> {
            String brand = brandTextField.getText();
            String color = colorTextField.getText();
            String rodLength = rodLengthTextField.getText();
            String castingWeight = castingWeightTextField.getText();
            String rodType = rodTypeTextField.getText();

            try{
                ValidationMethods.validateNewFishingPole(brand, color, rodLength, castingWeight, rodType);
                double rodLengthDouble = Double.parseDouble(rodLength);
                int castingWeightInt = Integer.parseInt(castingWeight);
                FishingRod newFishingRod = new FishingRod(brand, color, rodLengthDouble, castingWeightInt,
                        rodType);
                inventoryService.addNewItem(newFishingRod);
                confirmationWindow.showConfirmationWindow("Nytt fiskespö adderat",
                        "Fiskespöt har lagts till i klubben!",
                        "Det nya fiskespöt är nu adderat till klubbens lager");


            }catch(InvalidInputException exception){
                confirmationWindow.showConfirmationWindow("Process misslyckad",
                        "Fiskespöt kunde inte läggas till i klubben!",
                        exception.getMessage());
                brandTextField.clear();
                colorTextField.clear();
                rodLengthTextField.clear();
                castingWeightTextField.clear();
                rodTypeTextField.clear();
            }
        });
        VBox fishingPoleTextFields = new VBox(brandTextField, colorTextField, rodLengthTextField,
                castingWeightTextField, rodTypeTextField);

        Button closeWindow = new Button("Stäng fönster");
        closeWindow.setOnAction(e -> addNewFishingPole.close());

        HBox buttons = new HBox(addItemButton, closeWindow);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(100);


        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(15));
        rootLayout.setTop(windowTitels);
        rootLayout.setCenter(fishingPoleTextFields);
        rootLayout.setBottom(buttons);

        Scene scene = new Scene(rootLayout);
        addNewFishingPole.setScene(scene);
        addNewFishingPole.showAndWait();

    }
}
