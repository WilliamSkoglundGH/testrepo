package com.skoglund;

import com.skoglund.entity.Member;
import com.skoglund.guiMethods.memberRegistryGUIMethods.EditMemberWindow;
import com.skoglund.guiMethods.memberRegistryGUIMethods.MemberRegisterAlert;
import com.skoglund.guiMethods.memberRegistryGUIMethods.MemberRegisterConfirmation;
import com.skoglund.repository.MemberRegistry;
import com.skoglund.service.MembershipService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public Main() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    MemberRegistry memberRegistry = new MemberRegistry();
    MembershipService membershipService = new MembershipService(memberRegistry);

    TableView<Member> memberTableView;

    //GUIClasses with methods
    MemberRegisterConfirmation memberConfirmationWindow = new MemberRegisterConfirmation();
    MemberRegisterAlert memberRegistryAlert = new MemberRegisterAlert();
    EditMemberWindow editMemberWindow = new EditMemberWindow();


    @Override
    public void start(Stage stage) throws Exception {

        //BorderPane top
        Label titel = new Label("Välkommen till medlemsklubben för sportfiskare!");
        titel.setStyle("-fx-font-size:25; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS';");
        HBox welcomeBorder = new HBox(titel);
        welcomeBorder.setAlignment(Pos.CENTER);

        //Tableview columns
        //(name column)
        TableColumn<Member, String> nameColumn = new TableColumn<>("Namn");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //(id column)
        TableColumn<Member, String> idColumn = new TableColumn<>("Id");
        idColumn.setMinWidth(90);
        idColumn.setMaxWidth(90);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        //(age group column)
        TableColumn<Member, String> ageGroupColumn = new TableColumn<>("Åldersgrupp");
        ageGroupColumn.setPrefWidth(120);
        ageGroupColumn.setCellValueFactory(new PropertyValueFactory<>("ageGroup"));

        //BorderPane left
        Label manageMemberLabel = new Label("Medlemsregister");
        manageMemberLabel.setStyle("-fx-font-size:22; -fx-text-fill: white; -fx-padding: 7px; " +
                "-fx-font-family:'Comic Sans MS';");

        TextField nameTextField = new TextField();
        nameTextField.setPrefWidth(170);
        nameTextField.setPromptText("Namn:");

        ChoiceBox<String> ageGroupChoiceBox = new ChoiceBox<>();
        ageGroupChoiceBox.setPrefWidth(90);
        ageGroupChoiceBox.getItems().addAll("Barn", "Ungdom", "Vuxen", "Pensionär");
        ageGroupChoiceBox.setValue("Vuxen");

        Button saveMemberButton = new Button("Lägg till medlem");
        saveMemberButton.setOnAction(e -> {
            String name = nameTextField.getText();
            if (name.isEmpty()) {
                memberRegistryAlert.showMemberRegisterAlert("Registrering misslyckad",
                        "Registrering misslyckad, namn för medlem saknas",
                        "För att registrera en medlem måste NAMNFÄLTET" +
                                " fyllas i");
            } else {
                String ageGroup = ageGroupChoiceBox.getValue();
                Member newMember = new Member(name, ageGroup);
                membershipService.addNewMember(newMember);
                memberConfirmationWindow.showRegisterConfirmation(newMember);
            }
        });

        HBox saveNewMember = new HBox(nameTextField, ageGroupChoiceBox, saveMemberButton);

        Button updateListButton = new Button("Uppdatera medlemslista");
        updateListButton.setOnAction(e -> {
            try {
                memberRegistry.convertListToObservable();
                memberTableView.setItems(memberRegistry.getMemberList());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button saveDataToFileButton = new Button("Spara ändringar");
        saveDataToFileButton.setOnAction(e -> {
            try {
                membershipService.saveChangesToFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox toFromFile = new VBox(saveDataToFileButton, updateListButton);

        VBox handleRegistry = new VBox(saveNewMember, toFromFile);

        TextField searchMember = new TextField();
        searchMember.setPromptText("Ange medlems ID");

        memberTableView = new TableView<>();
        memberTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        memberTableView.setItems(memberRegistry.getMemberList());
        memberTableView.getColumns().addAll(nameColumn, idColumn, ageGroupColumn);


        Button searchButton = new Button("Sök");
        searchButton.setOnAction(e -> {
            String id = searchMember.getText();
            Member foundMember = membershipService.searchAndGetMember(id);
            if (foundMember == null) {
                memberRegistryAlert.showMemberRegisterAlert("Ingen match för ID", "Felaktigt ID, saknar" +
                        " match", "ID du angav: " + id + " , matchar ingen registrerad medlem");
            } else {
                int index = memberRegistry.getMemberList().indexOf(foundMember);
                memberTableView.getSelectionModel().select(index);
            }
        });

        Button editMemberButton = new Button("Redigera medlem");
        editMemberButton.setOnAction(e -> {
            Member hightlitedMember = memberTableView.getSelectionModel().getSelectedItem();
            if (hightlitedMember == null) {
                memberRegistryAlert.showMemberRegisterAlert("Vald medlem saknas", "Ingen medlem vald",
                        "Du måste markera (klicka på) en medlem i tabellen innan du klickar på knappen" +
                                "(Redigera medlem)");
            } else {
                editMemberWindow.editMember(hightlitedMember, memberTableView);
            }

        });

        HBox searchTool = new HBox(searchMember, searchButton);
        VBox manageMemberTitel = new VBox(manageMemberLabel, searchTool);
        handleRegistry.getChildren().add(editMemberButton);


        BorderPane manageMembers = new BorderPane();
        manageMembers.setPrefSize(415, 500);
        manageMembers.setStyle("-fx-padding:50px;");
        manageMembers.setBottom(handleRegistry);
        manageMembers.setCenter(memberTableView);
        manageMembers.setTop(manageMemberTitel);


        //Root layouten (det yttersta skalet)
        BorderPane rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: #2C3E50;");
        rootLayout.setTop(welcomeBorder);
        rootLayout.setLeft(manageMembers);

        //Scene
        Scene scene = new Scene(rootLayout);

        //Stage
        stage.setScene(scene);
        stage.setTitle("SportFishingMembersClub");
        stage.setMaximized(true);
        stage.show();
    }
}

