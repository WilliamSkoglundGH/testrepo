package com.skoglund.gui.sceneWindows.itemSceneWindows;

import com.skoglund.entity.item.Item;
import com.skoglund.service.InventoryService;
import com.skoglund.util.ValidationMethods;
import com.skoglund.util.exceptions.InvalidInputException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditItemWindow {
    public EditItemWindow(){

    }

    public void showEditItemWindow(Item item, TableView<Item> itemTableView,
                                   InventoryService inventoryService){
        Stage editItemStage = new Stage();
        editItemStage.initModality(Modality.APPLICATION_MODAL);
        editItemStage.setMinWidth(700);
        editItemStage.setMinHeight(500);
        editItemStage.setTitle("Ändra utrustningsinformation");

        Label titleLabel = new Label("Ändra utrustningsuppgifter");
        titleLabel.setStyle("-fx-font-size:22; -fx-padding: 7px;" +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");
        VBox windowTitelVBox = new VBox(titleLabel);
        windowTitelVBox.setAlignment(Pos.CENTER);

        TextField newBrandTextFiled = new TextField();
        newBrandTextFiled.setPromptText("Nytt märke:");

        TextField newColorTextField = new TextField();
        newColorTextField.setPromptText("Ny färg:");

        ChoiceBox<String> newItemStatusChoiceBox = new ChoiceBox<>();
        newItemStatusChoiceBox.setPrefWidth(90);
        newItemStatusChoiceBox.setValue("Tillgängligt");
        newItemStatusChoiceBox.getItems().addAll("Tillgängligt", "Icke tillgängligt");

        Label instructionLabel = new Label("Ändra utrustningsinfo:");

        HBox changesTextFieldsHBox = new HBox(instructionLabel, newBrandTextFiled, newColorTextField,
                newItemStatusChoiceBox);
        changesTextFieldsHBox.setPadding(new Insets(0,0,0,10));
        changesTextFieldsHBox.setSpacing(15);

        Label brandLabel = new Label("Märke:");
        brandLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");

        Label colorLabel = new Label("Färg:");
        colorLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");

        Label itemStatusLabel = new Label("Status:");
        itemStatusLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");

        Label itemBrand = new Label(item.getBrand());
        Label itemColor = new Label(item.getColor());
        Label itemStatus = new Label();
        if(item.isAvailable()) {
            itemStatus.setText("Tillgängligt");
        }
        else {
            itemStatus.setText("Icke tillgängligt");
        }

        GridPane currentItemInfoGridPane = new GridPane();
        currentItemInfoGridPane.setPadding(new Insets(10, 10, 10, 10));
        currentItemInfoGridPane.setVgap(6);
        currentItemInfoGridPane.setHgap(10);

        GridPane.setConstraints(brandLabel, 0, 0);
        GridPane.setConstraints(itemBrand, 1, 0);

        GridPane.setConstraints(colorLabel, 0, 1);
        GridPane.setConstraints(itemColor, 1, 1);

        GridPane.setConstraints(itemStatusLabel, 0, 2);
        GridPane.setConstraints(itemStatus, 1, 2);

        currentItemInfoGridPane.getChildren().addAll(brandLabel, itemBrand, colorLabel, itemColor,
                itemStatusLabel, itemStatus);

        Button saveButton = new Button("Spara ändringar");
        saveButton.setStyle("-fx-background-color: green;" +
                ";-fx-text-fill: white; -fx-font-weight: bold;");
        saveButton.setOnAction(e -> {
            try{
                String newBrand = newBrandTextFiled.getText();
                String newColor = newColorTextField.getText();
                ValidationMethods.validateItemBaseInfo(newBrand, newColor);
                boolean newItemAvailability;
                if(newItemStatusChoiceBox.getValue().equals("Tillgängligt")) {
                    newItemAvailability = true;
                }
                else {
                    newItemAvailability = false;
                }
                inventoryService.changeItemInfo(item, newBrand, newColor, newItemAvailability);
                inventoryService.saveItemListToFile();
                itemTableView.refresh();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Klart!");
                alert.setHeaderText(null);
                alert.setContentText("Ändringar av utrustning sparad!");
                alert.showAndWait();

                editItemStage.close();
            }catch(InvalidInputException exception){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fel");
                alert.setHeaderText("Ändring av utrustning misslyckades!");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        });

        Button cancelButton = new Button("Avbryt");
        cancelButton.setStyle("-fx-background-color: red;" +
                ";-fx-text-fill: white; -fx-font-weight: bold;");
        cancelButton.setOnAction(e -> editItemStage.close());

        HBox buttonsHBox = new HBox(saveButton,cancelButton);
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.setPadding(new Insets(0,0,15,0));
        buttonsHBox.setSpacing(180);

        VBox buttonsAndTextfieldsVBox = new VBox(changesTextFieldsHBox, buttonsHBox);
        buttonsAndTextfieldsVBox.setSpacing(50);

        Label gridPaneTitel = new Label("Nuvarande uppgifter:");
        gridPaneTitel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        gridPaneTitel.setPadding(new Insets(0,0,0,10));

        VBox showCurrentMemberInfoVBox = new VBox(gridPaneTitel, currentItemInfoGridPane);
        showCurrentMemberInfoVBox.setSpacing(7);
        showCurrentMemberInfoVBox.setPadding(new Insets(10));


        BorderPane rootLayout = new BorderPane();
        rootLayout.setTop(windowTitelVBox);
        rootLayout.setBottom(buttonsAndTextfieldsVBox);
        rootLayout.setCenter(showCurrentMemberInfoVBox);

        Scene scene = new Scene(rootLayout);
        editItemStage.setScene(scene);
        editItemStage.showAndWait();
    }
}