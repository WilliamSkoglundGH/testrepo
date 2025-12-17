package com.skoglund.gui.scenes;

import com.skoglund.entity.item.Item;
import com.skoglund.gui.SceneHandler;
import com.skoglund.gui.sceneWindows.itemSceneWindows.ChoseNewItemWindow;
import com.skoglund.gui.sceneWindows.itemSceneWindows.EditItemWindow;
import com.skoglund.gui.sceneWindows.sharedWindows.ConfirmationWindow;
import com.skoglund.repository.Inventory;
import com.skoglund.service.InventoryService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemScene {
    private Inventory inventory;
    private InventoryService inventoryService;
    private ConfirmationWindow confirmationWindow;

    //Tabell specifik för denna scen
    private TableView<Item> itemTableView;

    //Scen hanteraren som sköter scenbyten
    private SceneHandler sceneHandler;

    private ChoiceBox<String> itemFilterChoiceBox;

    public ItemScene() {

    }

    public ItemScene(Inventory inventory, InventoryService inventoryService,
                     ConfirmationWindow confirmationWindow, SceneHandler sceneHandler) {
        this.inventory = inventory;
        this.inventoryService = inventoryService;
        this.confirmationWindow = confirmationWindow;
        this.sceneHandler = sceneHandler;
    }

    public Scene showItemScene() {
        ChoseNewItemWindow choseNewItemWindow = new ChoseNewItemWindow(inventoryService, confirmationWindow);
        EditItemWindow editItemWindow = new EditItemWindow();


        Label titleLabel = new Label("Välkommen till sidan för att hantera klubbens utrustning");
        titleLabel.setStyle("-fx-font-size:25; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS';");

        Label subTitleLabel = new Label("Här under visas klubbens lager med utrustning");
        subTitleLabel.setStyle("-fx-font-size:21; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS';");

        VBox windowTitelsVBox = new VBox(titleLabel, subTitleLabel);
        windowTitelsVBox.setSpacing(7);
        windowTitelsVBox.setAlignment(Pos.CENTER);

        TableColumn<Item, String> itemTypeColumn = new TableColumn<>("Utrustning:");
        itemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("itemType"));

        TableColumn<Item, String> itemBrandColumn = new TableColumn<>("Märke:");
        itemBrandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Item, String> itemColorColumn = new TableColumn<>("Färg:");
        itemColorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

        itemTableView = new TableView<>();
        itemTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        itemTableView.getColumns().addAll(itemTypeColumn, itemBrandColumn, itemColorColumn);
        itemTableView.setItems(inventory.getItemList());

        Button addNewItemButton = new Button("Lägg till ny utrustning");
        addNewItemButton.setOnAction(e -> {
            choseNewItemWindow.showAddNewItemWindow();
            changeTableViewFilter();
        });

        Button removeItemButton = new Button("Ta bort utrustning");
        removeItemButton.setOnAction(e -> {
            Item chosenItem = itemTableView.getSelectionModel().getSelectedItem();
            if (chosenItem == null) {
                confirmationWindow.showConfirmationWindow("Process misslyckad",
                        "Borttagning av utrustning misslyckades",
                        "Du måste först markera en utrustning i tabellen innan du klickar på knappen");
            } else {
                inventoryService.removeItem(chosenItem);
                confirmationWindow.showConfirmationWindow("Process lyckad",
                        "Utrustning borttagen!",
                        chosenItem.getItemType() + " är nu borttaget från klubbens lager");
                changeTableViewFilter();
            }

        });

        Button editItemButton = new Button("Redigera utrustning");
        editItemButton.setOnAction(e -> {
            Item hightlitedItem = itemTableView.getSelectionModel().getSelectedItem();
            if (hightlitedItem == null) {
                confirmationWindow.showConfirmationWindow("Vald utrustning saknas",
                        "Ingen utrustning vald",
                        "Du måste markera (klicka på) en utrustning i tabellen innan du klickar på knappen" +
                                "(Redigera utrustning)");
            } else {
                editItemWindow.showEditItemWindow(hightlitedItem, itemTableView, inventoryService);
                changeTableViewFilter();
            }

        });

        Button updateTableViewButton = new Button("Uppdatera tabell");
        updateTableViewButton.setOnAction(e -> {
            try{
                inventory.loadItemListFromFile();
                changeTableViewFilter();
            }catch (IOException exception) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Uppdatering misslyckades");
                errorAlert.setContentText("Att uppdatera tabellen misslyckades!");
                System.out.println("FEL: läsandet av fil: items.json misslyckades: " +
                        exception.getMessage());
                errorAlert.showAndWait();
            }
            });

        Button saveChangesButton = new Button("Spara ändringar");
        saveChangesButton.setOnAction(e -> {
            try{
                inventory.saveItemListToFile();
            }catch(IOException exception){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Sparandet misslyckades");
                errorAlert.setContentText("Att spara dina ändringar misslyckades!");
                System.out.println("FEL: skrivandet till fil: items.json misslyckades: " +
                        exception.getMessage());
                errorAlert.showAndWait();
            }
        });

        Button returnToMainMenuButton = new Button("Återgå till huvudmeny");
        returnToMainMenuButton.setStyle("-fx-background-color: red;" +
                ";-fx-text-fill: white; -fx-font-weight: bold;");
        returnToMainMenuButton.setOnAction(e -> {
            sceneHandler.switchToMainMenu();
        });
        HBox returnButton = new HBox(returnToMainMenuButton);
        returnButton.setAlignment(Pos.BOTTOM_RIGHT);

        itemFilterChoiceBox = new ChoiceBox<>();
        itemFilterChoiceBox.getItems().addAll("Alla", "Tillgängliga", "Uthyrda");
        itemFilterChoiceBox.setValue("Alla");
        itemFilterChoiceBox.setOnAction(e -> changeTableViewFilter());

        HBox buttonsHBox = new HBox(addNewItemButton, removeItemButton, editItemButton,
                updateTableViewButton, saveChangesButton, itemFilterChoiceBox);
        buttonsHBox.setSpacing(20);

        VBox buttonsAndTableViewVBox = new VBox(itemTableView, buttonsHBox);

        BorderPane rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: #2C3E50;");
        rootLayout.setPadding(new Insets(30));
        rootLayout.setTop(windowTitelsVBox);
        rootLayout.setCenter(buttonsAndTableViewVBox);
        rootLayout.setBottom(returnButton);

        Scene scene = new Scene(rootLayout);
        return scene;
    }

    private void changeTableViewFilter(){
        List<Item> filteredList = new ArrayList<>();
        for (Item item : inventory.getItemList()) {
            if (itemFilterChoiceBox.getValue().equals("Alla")) {
                filteredList.add(item);
            }
            if (itemFilterChoiceBox.getValue().equals("Tillgängliga") && item.isAvailable()) {
                filteredList.add(item);
            }
            if (itemFilterChoiceBox.getValue().equals("Uthyrda") && !item.isAvailable()) {
                filteredList.add(item);
            }
        }
        itemTableView.setItems(FXCollections.observableArrayList(filteredList));
    }
}
