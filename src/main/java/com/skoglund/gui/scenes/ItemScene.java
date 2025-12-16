package com.skoglund.gui.scenes;

import com.skoglund.entity.item.Item;
import com.skoglund.gui.SceneHandler;
import com.skoglund.gui.sceneWindows.itemSceneWindows.ChoseNewItemWindow;
import com.skoglund.gui.sceneWindows.sharedWindows.ConfirmationWindow;
import com.skoglund.repository.Inventory;
import com.skoglund.service.InventoryService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ItemScene {
    private Inventory inventory;
    private InventoryService inventoryService;
    private ConfirmationWindow confirmationWindow;

    //GUI fönster specifika för denna scen
    private ChoseNewItemWindow choseNewItemWindow;

    //Tabell specifik för denna scen
    private TableView<Item> itemTableView;

    //Scen hanteraren som sköter scenbyten
    private SceneHandler sceneHandler;

    public ItemScene(){

    }
    public ItemScene(Inventory inventory, InventoryService inventoryService,
                     ConfirmationWindow confirmationWindow, SceneHandler sceneHandler){
        this.inventory = inventory;
        this.inventoryService = inventoryService;
        this.confirmationWindow = confirmationWindow;
        this.sceneHandler = sceneHandler;
    }

    public Scene showItemScene(){
        Label titel = new Label("Välkommen till sidan för att hantera klubbens utrustning");
        titel.setStyle("-fx-font-size:25; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS';");

        Label subTitel = new Label("Här under visas klubbens lager med utrustning");
        subTitel.setStyle("-fx-font-size:21; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS';");

        VBox windowTitels = new VBox(titel, subTitel);
        windowTitels.setSpacing(7);
        windowTitels.setAlignment(Pos.CENTER);

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
        addNewItemButton.setOnAction(e ->{
            choseNewItemWindow = new ChoseNewItemWindow(inventoryService,confirmationWindow);
            choseNewItemWindow.showAddNewItemWindow();
        });

        Button removeItemButton = new Button("Ta bort utrustning");
        removeItemButton.setOnAction(e -> {
            Item chosenItem = itemTableView.getSelectionModel().getSelectedItem();
            if(chosenItem == null){
                confirmationWindow.showConfirmationWindow("Process misslyckad",
                        "Borttagning av utrustning misslyckades",
                        "Du måste först markera en utrustning i tabellen innan du klickar på knappen");
            }
            else{
                inventoryService.removeItem(chosenItem);
                confirmationWindow.showConfirmationWindow("Process lyckad",
                        "Utrustning borttagen!",
                        chosenItem.getItemType() + " är nu borttaget från klubbens lager");
            }

        });

        Button updateTableViewButton = new Button("Uppdatera tabell");
        updateTableViewButton.setOnAction(e -> {
            inventory.loadItemListFromFile();
        });

        Button saveChangesButton = new Button("Spara ändringar");
        saveChangesButton.setOnAction(e -> {
            inventory.saveItemListToFile();
        });

        Button returnToMainMenuButton = new Button("Återgå till huvudmeny");
        returnToMainMenuButton.setOnAction(e ->{
            sceneHandler.switchToMainMenu();
        });
        HBox returnButton = new HBox(returnToMainMenuButton);
        returnButton.setAlignment(Pos.BOTTOM_RIGHT);

        HBox buttons = new HBox(addNewItemButton, removeItemButton,
                updateTableViewButton,saveChangesButton);
         buttons.setSpacing(20);

        VBox buttonsAndTableView = new VBox(itemTableView, buttons);

        BorderPane rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: #2C3E50;");
        rootLayout.setPadding(new Insets(30));
        rootLayout.setTop(windowTitels);
        rootLayout.setCenter(buttonsAndTableView);
        rootLayout.setBottom(returnButton);

        Scene scene = new Scene(rootLayout);
        return scene;
    }
}
