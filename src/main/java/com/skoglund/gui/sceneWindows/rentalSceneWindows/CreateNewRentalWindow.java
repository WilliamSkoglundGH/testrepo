package com.skoglund.gui.sceneWindows.rentalSceneWindows;

import com.skoglund.entity.Member;
import com.skoglund.entity.item.Item;
import com.skoglund.gui.sceneWindows.sharedWindows.ConfirmationWindow;
import com.skoglund.price.PricePolicy;
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
        Stage createNewRentalStage = new Stage();
        createNewRentalStage.initModality(Modality.APPLICATION_MODAL);
        createNewRentalStage.setMinWidth(900);
        createNewRentalStage.setMinHeight(700);
        createNewRentalStage.setTitle("Skapa ny uthyrning");

        Label titleLabel = new Label("Boka ny uthyrning");
        titleLabel.setStyle("-fx-font-size:22; -fx-padding: 7px;" +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");
        HBox windowTitleHBox = new HBox(titleLabel);
        windowTitleHBox.setAlignment(Pos.CENTER);

        Label subTitleLabel = new Label("För att genomföra en bokning markerar du en medlem i medlemstabellen,\n" +
                "du markera en utrustning i utrustningstabellen och sen klickar du på knappen: \n" +
                "(Genomför uthyrning)");
        HBox windowSubTitleHBox = new HBox(subTitleLabel);
        windowSubTitleHBox.setAlignment(Pos.CENTER);

        VBox windowTitlesVBox = new VBox(windowTitleHBox, windowSubTitleHBox);
        windowTitlesVBox.setSpacing(10);

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

        VBox availableItemsTableVBox = new VBox(tableDescription, availableItemsTableView, showMoreInfo);
        availableItemsTableVBox.setSpacing(10);
        availableItemsTableVBox.setPadding(new Insets(20));

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

        VBox membersVBox = new VBox(memberTableViewLabel, memberTableView);
        membersVBox.setSpacing(10);
        membersVBox.setPadding(new Insets(20));

        ComboBox<Integer> rentalPeriodComboBox = new ComboBox<>();
        rentalPeriodComboBox.getItems().addAll(1,2,4,7);
        rentalPeriodComboBox.setPromptText("Välj uthyrningsperiod(antal dagar)");

        Button bookRentalButton = new Button("Genomför uthyrning");
        bookRentalButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        bookRentalButton.setOnAction(e -> {
            if(memberTableView.getSelectionModel().getSelectedItem() == null ||
                    availableItemsTableView.getSelectionModel().getSelectedItem() == null ||
                    rentalPeriodComboBox.getValue() == null){
                confirmationWindow.showConfirmationWindow("Uthyrning misslyckad",
                        "Uthyrning misslyckad",
                        "Du måste markera en utrustning i tabellen, en medlem i tabellen \noch" +
                                " välja uthyrningsperiod för att genomföra en uthyrning");
            }
            else{
                Member highlitedMember = memberTableView.getSelectionModel().getSelectedItem();
                Item highlitedItem = availableItemsTableView.getSelectionModel().getSelectedItem();
                int chosenRentalPeriod = rentalPeriodComboBox.getValue();
                PricePolicy pricePolicyForChosenMember = rentalService.getMemberPricePolicy(highlitedMember);
                double rentalPrice = rentalService.calculateRentalPrice(chosenRentalPeriod,
                        pricePolicyForChosenMember);
                rentalService.createNewRental(highlitedMember, highlitedItem, chosenRentalPeriod, rentalPrice);
                confirmationWindow.showConfirmationWindow("Uthyrning genomförd",
                        "Uthyrningen lyckades!",
                        highlitedMember.getName() + " hyr: " + highlitedItem.getItemType() + " i " +
                        chosenRentalPeriod + " dagar") ;
                createNewRentalStage.close();
            }

        });
        Button closeWindowButton = new Button("Stäng fönster");
        closeWindowButton.setStyle("-fx-background-color: red; -fx-text-fill: white; " +
                "-fx-font-weight: bold;");
        closeWindowButton.setOnAction(e -> createNewRentalStage.close());

        HBox buttonsHBox = new HBox(bookRentalButton, closeWindowButton);
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.setSpacing(100);
        buttonsHBox.setPadding(new Insets(0,0,10,0));


        BorderPane rootLayout = new BorderPane();
        rootLayout.setTop(windowTitlesVBox);
        rootLayout.setLeft(availableItemsTableVBox);
        rootLayout.setRight(membersVBox);
        rootLayout.setBottom(buttonsHBox);
        rootLayout.setCenter(rentalPeriodComboBox);

        Scene scene = new Scene(rootLayout);
        createNewRentalStage.setScene(scene);
        createNewRentalStage.showAndWait();
    }
}
