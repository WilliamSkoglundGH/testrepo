package com.skoglund.gui.sceneWindows.rentalSceneWindows;

import com.skoglund.entity.Member;
import com.skoglund.entity.item.Item;
import com.skoglund.gui.sceneWindows.sharedWindows.ConfirmationWindow;
import com.skoglund.price.PricePolicy;
import com.skoglund.repository.Inventory;
import com.skoglund.repository.MemberRegistry;
import com.skoglund.service.InventoryService;
import com.skoglund.service.RentalService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateNewRentalWindow {
    private InventoryService inventoryService;
    private MemberRegistry memberRegistry;
    private RentalService rentalService;
    private ConfirmationWindow confirmationWindow;

    public CreateNewRentalWindow(){

    }
    public CreateNewRentalWindow(InventoryService inventoryService, MemberRegistry memberRegistry,
                                 RentalService rentalService, ConfirmationWindow confirmationWindow){
        this.inventoryService = inventoryService;
        this.memberRegistry = memberRegistry;
        this.rentalService = rentalService;
        this.confirmationWindow = confirmationWindow;
    }

    public void showNewRentalWindow(){
        Stage createNewRental = new Stage();
        createNewRental.initModality(Modality.APPLICATION_MODAL);
        createNewRental.setMinWidth(900);
        createNewRental.setMinHeight(700);
        createNewRental.setTitle("Skapa ny uthyrning");

        Label titel = new Label("Boka ny uthyrning");
        titel.setStyle("-fx-font-size:22; -fx-padding: 7px;" +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");
        HBox windowTitel = new HBox(titel);
        windowTitel.setAlignment(Pos.CENTER);

        Label subTitel = new Label("För att genomföra en bokning markerar du en medlem i medlemstabellen,\n" +
                "du markera en utrustning i utrustningstabellen och sen klickar du på knappen: \n" +
                "(Genomför uthyrning)");
        HBox windowSubTitel = new HBox(subTitel);
        windowSubTitel.setAlignment(Pos.CENTER);

        VBox windowTitels = new VBox(windowTitel, windowSubTitel);
        windowTitels.setSpacing(10);

        TableColumn<Item, String> itemTypeColumn = new TableColumn<>("Utrustning:");
        itemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("itemType"));


        TableView<Item> availableItemsTableView = new TableView<>();
        availableItemsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        availableItemsTableView.getColumns().addAll(itemTypeColumn);
        availableItemsTableView.setItems(inventoryService.getAvailableItems());

        Label tableDescription = new Label("Tillgänglig utrustning");
        tableDescription.setStyle("-fx-font-size: 18;");

        Button showMoreInfo = new Button("Visa utrustningsinformation");
        showMoreInfo.setOnAction(e ->{

        });

        VBox availableItemsTabel = new VBox(tableDescription, availableItemsTableView, showMoreInfo);
        availableItemsTabel.setSpacing(10);
        availableItemsTabel.setPadding(new Insets(20));

        TableColumn<Member,String> nameColumn = new TableColumn<>("Namn:");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Member, String> idColumn = new TableColumn<>("ID:");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Member, String> ageGroupColumn = new TableColumn<>("Åldersgrupp:");
        ageGroupColumn.setCellValueFactory(new PropertyValueFactory<>("ageGroup"));

        TableView<Member> memberTableView = new TableView<>();
        memberTableView.getColumns().addAll(nameColumn, idColumn, ageGroupColumn);
        memberTableView.setItems(memberRegistry.getMemberList());

        Label memberTableViewLabel = new Label("Medlemmar:");
        memberTableViewLabel.setStyle("-fx-font-size: 18;");

        VBox members = new VBox(memberTableViewLabel, memberTableView);
        members.setSpacing(10);
        members.setPadding(new Insets(20));

        ComboBox<Integer> rentalPeriod = new ComboBox<>();
        rentalPeriod.getItems().addAll(1,2,4,7);
        rentalPeriod.setPromptText("Välj uthyrningsperiod(antal dagar)");

        Button bookRentalButton = new Button("Genomför uthyrning");
        bookRentalButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        bookRentalButton.setOnAction(e -> {
            if(memberTableView.getSelectionModel().getSelectedItem() == null ||
                    availableItemsTableView.getSelectionModel().getSelectedItem() == null ||
                    rentalPeriod.getValue() == null){
                confirmationWindow.showConfirmationWindow("Uthyrning misslyckad",
                        "Uthyrning misslyckad",
                        "Du måste markera en utrustning i tabellen, en medlem i tabellen \noch" +
                                " välja uthyrningsperiod för att genomföra en uthyrning");
            }
            else{
                Member highlitedMember = memberTableView.getSelectionModel().getSelectedItem();
                Item highlitedItem = availableItemsTableView.getSelectionModel().getSelectedItem();
                int chosenRentalPeriod = rentalPeriod.getValue();
                PricePolicy pricePolicyForChosenMember = rentalService.getMemberPricePolicy(highlitedMember);
                double rentalPrice = rentalService.calculateRentalPrice(chosenRentalPeriod,
                        pricePolicyForChosenMember);
                rentalService.createNewRental(highlitedMember, highlitedItem, chosenRentalPeriod, rentalPrice);
                confirmationWindow.showConfirmationWindow("Uthyrning genomförd",
                        "Uthyrningen lyckades!",
                        highlitedMember.getName() + " hyr: " + highlitedItem.getItemType() + " i " +
                        chosenRentalPeriod + " dagar") ;
                createNewRental.close();
            }

        });
        Button closeWindowButton = new Button("Stäng fönster");
        closeWindowButton.setStyle("-fx-background-color: red; -fx-text-fill: white; " +
                "-fx-font-weight: bold;");
        closeWindowButton.setOnAction(e -> createNewRental.close());

        HBox buttons = new HBox(bookRentalButton, closeWindowButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(100);
        buttons.setPadding(new Insets(0,0,10,0));


        BorderPane rootLayout = new BorderPane();
        rootLayout.setTop(windowTitels);
        rootLayout.setLeft(availableItemsTabel);
        rootLayout.setRight(members);
        rootLayout.setBottom(buttons);
        rootLayout.setCenter(rentalPeriod);

        Scene scene = new Scene(rootLayout);
        createNewRental.setScene(scene);
        createNewRental.showAndWait();
    }
}
