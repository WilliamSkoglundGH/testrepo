package com.skoglund.gui.scenes;


import com.skoglund.entity.Member;
import com.skoglund.gui.SceneHandler;
import com.skoglund.gui.sceneWindows.memberRegistrySceneWindows.EditMemberWindow;
import com.skoglund.gui.sceneWindows.memberRegistrySceneWindows.MemberRegisterConfirmation;
import com.skoglund.gui.sceneWindows.memberRegistrySceneWindows.MemberRentalHistoryWindow;
import com.skoglund.gui.sceneWindows.sharedWindows.ConfirmationWindow;
import com.skoglund.repository.MemberRegistry;
import com.skoglund.service.MembershipService;
import com.skoglund.util.ValidationMethods;
import com.skoglund.util.exceptions.InvalidInputException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MemberRegistryScene {
    private MemberRegistry memberRegistry;
    private MembershipService membershipService;
    private ConfirmationWindow confirmationWindow;

    //Scenhanterare som sköter byten av scener
    private SceneHandler sceneHandler;

    //Tableview
    private TableView<Member> memberTableView;

    public MemberRegistryScene() {

    }

    public MemberRegistryScene(MemberRegistry memberRegistry, MembershipService membershipService,
                               ConfirmationWindow confirmationWindow, SceneHandler sceneHandler) {
        this.memberRegistry = memberRegistry;
        this.membershipService = membershipService;
        this.confirmationWindow = confirmationWindow;
        this.sceneHandler = sceneHandler;

    }

    public Scene showMemberRegistryScene(){
        //Windows
        EditMemberWindow editMemberWindow = new EditMemberWindow();
        MemberRegisterConfirmation memberRegisterConfirmation = new MemberRegisterConfirmation();
        MemberRentalHistoryWindow memberRentalHistoryWindow = new MemberRentalHistoryWindow();

        //Labels
        Label titelLabel;
        Label tableViewTitelLabel;

        //Buttons
        Button saveMemberButton;
        Button updateListButton;
        Button saveChangesButton;
        Button searchButton;
        Button editMemberButton;
        Button showRentalHistoryButton;
        Button closeWindowButton;

        //TextFields
        TextField nameTextField;
        TextField searchMemberTextField;

        //Layouts
        HBox titelHBox;
        HBox saveNewMemberHBox;
        HBox tableViewTitelHBox;
        HBox searchToolHBox;
        HBox tableViewButtonsHBox;
        HBox closeWindowHBox;
        BorderPane rootLayout;


        titelLabel = new Label("Välkommen till sidan för hantering av medlemskap!");
        titelLabel.setStyle("-fx-font-size:25; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS';");
        titelHBox = new HBox(titelLabel);
        titelHBox.setAlignment(Pos.CENTER);

        //Tableview columns
        TableColumn<Member, String> nameColumn = new TableColumn<>("Namn");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //(id column)
        TableColumn<Member, String> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        //(age group column)
        TableColumn<Member, String> ageGroupColumn = new TableColumn<>("Åldersgrupp");
        ageGroupColumn.setCellValueFactory(new PropertyValueFactory<>("ageGroup"));

        memberTableView = new TableView<>();
        memberTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        memberTableView.setItems(memberRegistry.getMemberList());
        memberTableView.getColumns().addAll(nameColumn, idColumn, ageGroupColumn);

        tableViewTitelLabel = new Label("Medlemsregister");
        tableViewTitelLabel.setStyle("-fx-font-size:22; -fx-text-fill: white; -fx-padding: 7px; " +
                "-fx-font-family:'Comic Sans MS';");
        tableViewTitelHBox = new HBox(tableViewTitelLabel);
        tableViewTitelHBox.setAlignment(Pos.TOP_LEFT);

        searchMemberTextField = new TextField();
        searchMemberTextField.setPromptText("Ange medlems ID");

        searchButton = new Button("Sök");
        searchButton.setOnAction(e -> {
            String id = searchMemberTextField.getText();
            Member foundMember = membershipService.searchAndGetMember(id);
            if (foundMember == null) {
                confirmationWindow.showConfirmationWindow("Ingen match för ID",
                        "Felaktigt ID, saknar" +
                                " match", "ID du angav: " + id + " , matchar ingen registrerad medlem");
            } else {
                int index = memberRegistry.getMemberList().indexOf(foundMember);
                memberTableView.getSelectionModel().select(index);
            }
        });

        searchToolHBox = new HBox(searchMemberTextField, searchButton);

        nameTextField = new TextField();
        nameTextField.setPrefWidth(170);
        nameTextField.setPromptText("Ange namn:");

        ChoiceBox<String> ageGroupChoiceBox = new ChoiceBox<>();
        ageGroupChoiceBox.setPrefWidth(90);
        ageGroupChoiceBox.getItems().addAll("Barn", "Ungdom", "Vuxen", "Pensionär");
        ageGroupChoiceBox.setValue("Vuxen");

        saveMemberButton = new Button("Lägg till medlem");
        saveMemberButton.setOnAction(e -> {
            try{
                String name = nameTextField.getText();
                ValidationMethods.validateMemberName(name);
                String ageGroup = ageGroupChoiceBox.getValue();
                Member newMember = new Member(name, ageGroup);
                membershipService.addNewMember(newMember);
                memberRegisterConfirmation.showRegisterConfirmation(newMember);
            }catch(InvalidInputException exception){
                confirmationWindow.showConfirmationWindow("Felaktigt namn",
                        "Angett namn godkänns ej",
                        exception.getMessage());
            }

        });

        saveNewMemberHBox = new HBox(nameTextField, ageGroupChoiceBox, saveMemberButton);

        updateListButton = new Button("Uppdatera medlemslista");
        updateListButton.setOnAction(e -> {
            memberRegistry.loadMemberListFromFile();
        });

        saveChangesButton = new Button("Spara ändringar");
        saveChangesButton.setOnAction(e -> {
            membershipService.saveMemberListToFile();
        });

        editMemberButton = new Button("Redigera medlem");
        editMemberButton.setOnAction(e -> {
            Member hightlitedMember = memberTableView.getSelectionModel().getSelectedItem();
            if (hightlitedMember == null) {
                confirmationWindow.showConfirmationWindow("Vald medlem saknas",
                        "Ingen medlem vald",
                        "Du måste markera (klicka på) en medlem i tabellen innan du klickar på knappen" +
                                "(Redigera medlem)");
            } else {
                editMemberWindow.editMember(hightlitedMember, memberTableView, membershipService);
            }

        });

        showRentalHistoryButton = new Button("Visa uthyrningshistorik");
        showRentalHistoryButton.setOnAction(e -> {
            Member highlitedMember = memberTableView.getSelectionModel().getSelectedItem();
            if(highlitedMember == null){
                confirmationWindow.showConfirmationWindow("Vald medlem saknas" ,
                        "Ingen medlem vald",
                        "Du måste markera (klicka på) en medlem i tabellen innan du klickar på knappen" +
                                "(Visa uthyrningshistorik");
            }
            else{
                memberRentalHistoryWindow.showRentalHistoryWindow(highlitedMember);
            }
        });

        tableViewButtonsHBox = new HBox(saveNewMemberHBox, saveChangesButton, updateListButton,
                editMemberButton, showRentalHistoryButton);
        tableViewButtonsHBox.setSpacing(70);

        closeWindowButton = new Button("Återgå till huvudmenyn");
        closeWindowButton.setStyle("-fx-background-color: red;" +
                ";-fx-text-fill: white; -fx-font-weight: bold;");
        closeWindowButton.setOnAction(e -> {
            sceneHandler.switchToMainMenu();
        });
        closeWindowHBox = new HBox(closeWindowButton);
        closeWindowHBox.setAlignment(Pos.BOTTOM_RIGHT);
        closeWindowHBox.setPadding(new Insets(10));

        VBox memberRegistry = new VBox(tableViewTitelHBox, searchToolHBox,memberTableView, tableViewButtonsHBox);

        //Root layouten (det yttersta skalet)
        rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: #2C3E50;");
        rootLayout.setPadding(new Insets(30));
        rootLayout.setTop(titelHBox);
        rootLayout.setCenter(memberRegistry);
        rootLayout.setBottom(closeWindowHBox);

        //Scene
        Scene scene = new Scene(rootLayout);
        return scene;

    }
}
