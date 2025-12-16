package com.skoglund.gui.sceneWindows.itemSceneWindows;

import com.skoglund.entity.item.FishingReel;
import com.skoglund.entity.item.LureSet;
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

public class AddNewLureSetWindow {
    private InventoryService inventoryService;
    private ConfirmationWindow confirmationWindow;

    public AddNewLureSetWindow(){

    }
    public AddNewLureSetWindow(InventoryService inventoryService, ConfirmationWindow confirmationWindow){
        this.inventoryService = inventoryService;
        this.confirmationWindow = confirmationWindow;
    }


    public void showAddNewLureSetWindow(){
        Stage addNewLureSet = new Stage();
        addNewLureSet.initModality(Modality.APPLICATION_MODAL);
        addNewLureSet.setMinWidth(700);
        addNewLureSet.setMinHeight(500);
        addNewLureSet.setTitle("Lägg till utrustning");

        Label titel = new Label("Lägg ett nytt betesset till klubben");
        titel.setStyle("-fx-font-size:22; -fx-padding: 7px;" +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");
        HBox windowTitel = new HBox(titel);
        windowTitel.setAlignment(Pos.CENTER);

        Label lureSetInfo = new Label("Fyll i all information om betessettet");
        HBox windowSubTitel = new HBox(lureSetInfo);
        windowSubTitel.setAlignment(Pos.CENTER);

        VBox windowTitels = new VBox(titel,windowSubTitel);
        windowTitels.setSpacing(7);

        TextField brandTextField = new TextField();
        brandTextField.setPromptText("Ange märke:");

        TextField colorTextField = new TextField();
        colorTextField.setPromptText("Ange färg:");

        TextField lureTypeTextField = new TextField();
        lureTypeTextField.setPromptText("Ange betestyp:");

        Button addItemButton = new Button("Lägg till fiskerullen");
        addItemButton.setOnAction(e -> {
            String brand = brandTextField.getText();
            String color = colorTextField.getText();
            String lureType = lureTypeTextField.getText();
            try{
                ValidationMethods.validateNewLureSet(brand, color, lureType);
                LureSet newLureSet = new LureSet(brand, color, lureType);
                inventoryService.addNewItem(newLureSet);
                confirmationWindow.showConfirmationWindow("Nytt betesset adderat",
                        "Betessettet har lagts till i klubben!",
                        "Det nya betessettet är nu adderad till klubbens lager");


            }catch(InvalidInputException exception){
                confirmationWindow.showConfirmationWindow("Process misslyckad",
                        "Betessettet kunde inte läggas till i klubben!",
                        exception.getMessage());
                brandTextField.clear();
                colorTextField.clear();
                lureTypeTextField.clear();
            }
        });
        VBox fishingPoleTextFields = new VBox(brandTextField, colorTextField, lureTypeTextField);

        Button closeWindow = new Button("Stäng fönster");
        closeWindow.setOnAction(e -> addNewLureSet.close());

        HBox buttons = new HBox(addItemButton, closeWindow);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(100);


        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(15));
        rootLayout.setTop(windowTitels);
        rootLayout.setCenter(fishingPoleTextFields);
        rootLayout.setBottom(buttons);

        Scene scene = new Scene(rootLayout);
        addNewLureSet.setScene(scene);
        addNewLureSet.showAndWait();
    }
}
