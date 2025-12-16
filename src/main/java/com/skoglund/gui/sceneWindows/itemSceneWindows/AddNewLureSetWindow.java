package com.skoglund.gui.sceneWindows.itemSceneWindows;

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
        Stage addNewLureSetStage = new Stage();
        addNewLureSetStage.initModality(Modality.APPLICATION_MODAL);
        addNewLureSetStage.setMinWidth(700);
        addNewLureSetStage.setMinHeight(500);
        addNewLureSetStage.setTitle("Lägg till utrustning");

        Label titleLabel = new Label("Lägg ett nytt betesset till klubben");
        titleLabel.setStyle("-fx-font-size:22; -fx-padding: 7px;" +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");
        HBox windowTitelHBox = new HBox(titleLabel);
        windowTitelHBox.setAlignment(Pos.CENTER);

        Label lureSetInfoLabel = new Label("Fyll i all information om betessettet");
        HBox windowSubTitelHBox = new HBox(lureSetInfoLabel);
        windowSubTitelHBox.setAlignment(Pos.CENTER);

        VBox windowTitels = new VBox(titleLabel, windowSubTitelHBox);
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
                addNewLureSetStage.close();


            }catch(InvalidInputException exception){
                confirmationWindow.showConfirmationWindow("Process misslyckad",
                        "Betessettet kunde inte läggas till i klubben!",
                        exception.getMessage());
                brandTextField.clear();
                colorTextField.clear();
                lureTypeTextField.clear();
            }
        });
        VBox fishingPoleTextFieldsVBox = new VBox(brandTextField, colorTextField, lureTypeTextField);

        Button closeWindowButton = new Button("Stäng fönster");
        closeWindowButton.setOnAction(e -> addNewLureSetStage.close());

        HBox buttons = new HBox(addItemButton, closeWindowButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(100);


        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(15));
        rootLayout.setTop(windowTitels);
        rootLayout.setCenter(fishingPoleTextFieldsVBox);
        rootLayout.setBottom(buttons);

        Scene scene = new Scene(rootLayout);
        addNewLureSetStage.setScene(scene);
        addNewLureSetStage.showAndWait();
    }
}
