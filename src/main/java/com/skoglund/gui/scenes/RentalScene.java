package com.skoglund.gui.scenes;

import com.skoglund.entity.Rental;
import com.skoglund.gui.SceneHandler;
import com.skoglund.gui.sceneWindows.rentalSceneWindows.CreateNewRentalWindow;
import com.skoglund.gui.sceneWindows.sharedWindows.ConfirmationWindow;
import com.skoglund.repository.Inventory;
import com.skoglund.repository.MemberRegistry;
import com.skoglund.repository.RentalRegistry;
import com.skoglund.service.InventoryService;
import com.skoglund.service.RentalService;
import javafx.collections.transformation.FilteredList;
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


public class RentalScene {
    private RentalRegistry rentalRegistry;
    private RentalService rentalService;
    private ConfirmationWindow confirmationWindow;
    private InventoryService inventoryService;
    private Inventory inventory;
    //Denna klass använder ej detta, men fönstret denna klass öppnar gör det
    private MemberRegistry memberRegistry;

    //GUI fönster specifika för denna scen
    private CreateNewRentalWindow createNewRentalWindow;

    //Tabell specifik för denna scen
    private TableView<Rental> rentalTableView;

    //Scen hanterare som byter scener
    SceneHandler sceneHandler;

    public RentalScene(){

    }
    public RentalScene(RentalRegistry rentalRegistry, RentalService rentalService,
                       ConfirmationWindow confirmationWindow, InventoryService inventoryService,
                       MemberRegistry memberRegistry, SceneHandler sceneHandler,
                       Inventory inventory){
        this.rentalRegistry = rentalRegistry;
        this.rentalService = rentalService;
        this.confirmationWindow = confirmationWindow;
        this.inventoryService = inventoryService;
        this.memberRegistry = memberRegistry;
        this.sceneHandler = sceneHandler;
        this.inventory = inventory;
    }

    public Scene showRentalScene(){

        Label titel = new Label("Välkommen till sidan för att hantera klubbens uthyrningar");
        titel.setStyle("-fx-font-size:25; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS';");

        Label subTitel = new Label("Här under visas klubbens aktiva uthyrningar");
        subTitel.setStyle("-fx-font-size:21; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS';");

        VBox windowTitels = new VBox(titel, subTitel);
        windowTitels.setSpacing(7);
        windowTitels.setAlignment(Pos.CENTER);

        //Table columns
        TableColumn<Rental, String> memberIdColumn = new TableColumn<>("MedlemsID:");
        memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));

        TableColumn<Rental, String> itemIdColumn = new TableColumn<>("UtrustningID:");
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Rental, Integer> rentalTimePeriodColumn = new TableColumn<>("Uthyrningsperiod(dagar):");
        rentalTimePeriodColumn.setCellValueFactory(new PropertyValueFactory<>("rentalTime"));

        TableColumn<Rental, Double> rentalPriceColumn = new TableColumn<>("Pris(kr):");
        rentalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        rentalTableView = new TableView<>();
        rentalTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        rentalTableView.setItems(rentalService.getActiveRentals());
        rentalTableView.getColumns().addAll(memberIdColumn, itemIdColumn, rentalTimePeriodColumn,
                rentalPriceColumn);

        Button newRental = new Button("Boka ny uthyrning");
        newRental.setOnAction(e ->{
            inventory.loadItemListFromFile();
            memberRegistry.loadMemberListFromFile();
            createNewRentalWindow = new CreateNewRentalWindow(inventoryService,memberRegistry,rentalService,
                    confirmationWindow);
            createNewRentalWindow.showNewRentalWindow();
            rentalTableView.setItems(rentalService.getActiveRentals());
        });

        Button finishRental = new Button("Avsluta uthyrning");
        finishRental.setOnAction(e -> {
            Rental highlitedRental = rentalTableView.getSelectionModel().getSelectedItem();
            if(highlitedRental == null){
                confirmationWindow.showConfirmationWindow("Ingen uthyrning markerad",
                        "Ingen uthyrning markerad!",
                        "Du måste först markera en uthyrning i tabellen för att avsluta den");
            }
            else{
                rentalService.finishRental(highlitedRental);
                rentalTableView.setItems(rentalService.getActiveRentals());
                confirmationWindow.showConfirmationWindow("Uthyrning avslutad",
                        "Uthyrning avslutades!",
                        "ID för utrustning: " + highlitedRental.getItemId() + " ID för medlem: "
                                + highlitedRental.getMemberId() + "| Uthyrningen avslutad!");
            }
        });

        HBox tableButtons = new HBox(newRental, finishRental);
        tableButtons.setSpacing(30);

        VBox tableViewAndButtons = new VBox(rentalTableView, tableButtons);
        tableViewAndButtons.setSpacing(10);

        Button returnToMainMenuButton = new Button("Återgå till huvudmeny");
        returnToMainMenuButton.setOnAction(e ->{
            sceneHandler.switchToMainMenu();
        });
        HBox returnButton = new HBox(returnToMainMenuButton);
        returnButton.setAlignment(Pos.BOTTOM_RIGHT);


        BorderPane rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: #2C3E50;");
        rootLayout.setPadding(new Insets(30));
        rootLayout.setTop(windowTitels);
        rootLayout.setCenter(tableViewAndButtons);
        rootLayout.setBottom(returnButton);


        Scene rentalScene = new Scene(rootLayout);
        return rentalScene;


    }
}
