package com.skoglund.gui.scenes;

import com.skoglund.entity.Rental;
import com.skoglund.gui.SceneHandler;
import com.skoglund.gui.sceneWindows.rentalSceneWindows.CreateNewRentalWindow;
import com.skoglund.gui.sceneWindows.sharedWindows.ConfirmationWindow;
import com.skoglund.repository.Inventory;
import com.skoglund.repository.MemberRegistry;
import com.skoglund.service.InventoryService;
import com.skoglund.service.RentalService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class RentalScene {
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
    public RentalScene(RentalService rentalService,
                       ConfirmationWindow confirmationWindow, InventoryService inventoryService,
                       MemberRegistry memberRegistry, SceneHandler sceneHandler,
                       Inventory inventory){
        this.rentalService = rentalService;
        this.confirmationWindow = confirmationWindow;
        this.inventoryService = inventoryService;
        this.memberRegistry = memberRegistry;
        this.sceneHandler = sceneHandler;
        this.inventory = inventory;
    }

    public Scene showRentalScene(){

        Label titleLabel = new Label("Välkommen till sidan för att hantera klubbens uthyrningar");
        titleLabel.setStyle("-fx-font-size:25; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS';");

        Label subTitleLabel = new Label("Här under visas klubbens aktiva uthyrningar");
        subTitleLabel.setStyle("-fx-font-size:21; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS';");

        VBox windowTitelsVBox = new VBox(titleLabel, subTitleLabel);
        windowTitelsVBox.setSpacing(7);
        windowTitelsVBox.setAlignment(Pos.CENTER);

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

        Button createNewRentalButton = new Button("Boka ny uthyrning");
        createNewRentalButton.setOnAction(e ->{
            inventory.loadItemListFromFile();
            memberRegistry.loadMemberListFromFile();
            createNewRentalWindow = new CreateNewRentalWindow(inventoryService,memberRegistry,rentalService,
                    confirmationWindow);
            createNewRentalWindow.showNewRentalWindow();
            rentalTableView.setItems(rentalService.getActiveRentals());
        });

        Button finishRentalButton = new Button("Avsluta uthyrning");
        finishRentalButton.setOnAction(e -> {
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

        Button showRentalIncomeButton = new Button("Visa klubbens intäkter");
        showRentalIncomeButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Intäkter");
            alert.setHeaderText("Klubbens sammanlagda intäkter");
            alert.setContentText(rentalService.calculateClubRentalIncome() + " kr");
            alert.showAndWait();
        });

        HBox tableButtonsHBox = new HBox(createNewRentalButton, finishRentalButton, showRentalIncomeButton);
        tableButtonsHBox.setSpacing(30);

        VBox tableViewAndButtonsVBox = new VBox(rentalTableView, tableButtonsHBox);
        tableViewAndButtonsVBox.setSpacing(10);

        Button returnToMainMenuButton = new Button("Återgå till huvudmeny");
        returnToMainMenuButton.setOnAction(e ->{
            sceneHandler.switchToMainMenu();
        });
        HBox returnButtonHBox = new HBox(returnToMainMenuButton);
        returnButtonHBox.setAlignment(Pos.BOTTOM_RIGHT);


        BorderPane rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: #2C3E50;");
        rootLayout.setPadding(new Insets(30));
        rootLayout.setTop(windowTitelsVBox);
        rootLayout.setCenter(tableViewAndButtonsVBox);
        rootLayout.setBottom(returnButtonHBox);


        Scene rentalScene = new Scene(rootLayout);
        return rentalScene;


    }
}
