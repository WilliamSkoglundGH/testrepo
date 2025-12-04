package com.skoglund.guiMethods.memberRegistryGUIMethods;

import com.skoglund.entity.Member;
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

public class EditMemberWindow {

    public void editMember(Member member, TableView<Member> memberTableView) {
        Stage editMemberStage = new Stage();
        editMemberStage.initModality(Modality.APPLICATION_MODAL);
        editMemberStage.setMinWidth(700);
        editMemberStage.setMinHeight(500);
        editMemberStage.setTitle("Ändra medlemsinformation");

        Label titel = new Label("Ändra medlemsuppgifter");
        titel.setStyle("-fx-font-size:22; -fx-padding: 7px;" +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");
        VBox windowTitel = new VBox(titel);
        windowTitel.setAlignment(Pos.CENTER);

        TextField newNameTextFiled = new TextField();
        newNameTextFiled.setPromptText("Nytt namn:");

        ChoiceBox<String> newAgeGroupChoiceBox = new ChoiceBox<>();
        newAgeGroupChoiceBox.setPrefWidth(90);
        newAgeGroupChoiceBox.getItems().addAll("Barn", "Ungdom", "Vuxen", "Pensionär");
        newAgeGroupChoiceBox.setValue("Vuxen");

        Label instruction = new Label("Ändra medlemsinfo:");
        instruction.setStyle("-fx-font-size:17; -fx-font-weight:bold;");

        HBox changesTextFields = new HBox(instruction,newNameTextFiled,newAgeGroupChoiceBox);
        changesTextFields.setPadding(new Insets(0,0,0,10));
        changesTextFields.setSpacing(15);

        Label nameLabel = new Label("Namn:");
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");

        Label idLabel = new Label("ID:");
        idLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");

        Label ageGroupLabel = new Label("Åldersgrupp:");
        ageGroupLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");

        Label memberName = new Label(member.getName());
        Label memberId = new Label(member.getId());
        Label memberAgeGroup = new Label(member.getAgeGroup());

        GridPane currentMemberInfoGridPane = new GridPane();
        currentMemberInfoGridPane.setPadding(new Insets(10, 10, 10, 10));
        currentMemberInfoGridPane.setVgap(6);
        currentMemberInfoGridPane.setHgap(10);

        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(memberName, 1, 0);

        GridPane.setConstraints(idLabel, 0, 1);
        GridPane.setConstraints(memberId, 1, 1);

        GridPane.setConstraints(ageGroupLabel, 0, 2);
        GridPane.setConstraints(memberAgeGroup, 1, 2);

        currentMemberInfoGridPane.getChildren().addAll(nameLabel, memberName, idLabel, memberId, ageGroupLabel,
                memberAgeGroup);

        Button saveButton = new Button("Spara ändringar");
        saveButton.setStyle("-fx-background-color: green;" +
                ";-fx-text-fill: white; -fx-font-weight: bold;");
        saveButton.setOnAction(e -> {
            String newName = newNameTextFiled.getText();
            String newAgeGroup = newAgeGroupChoiceBox.getValue();

            member.setName(newName);
            member.setAgeGroup(newAgeGroup);
            memberTableView.refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Klart!");
            alert.setHeaderText(null);
            alert.setContentText("Ändringar av medlem sparades!");
            alert.showAndWait();

            editMemberStage.close();
        });

        Button cancelButton = new Button("Avbryt");
        cancelButton.setStyle("-fx-background-color: red;" +
                ";-fx-text-fill: white; -fx-font-weight: bold;");
        cancelButton.setOnAction(e -> editMemberStage.close());

        HBox buttons = new HBox(saveButton,cancelButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(0,0,15,0));
        buttons.setSpacing(180);

        VBox buttonsAndTextfields = new VBox(changesTextFields,buttons);
        buttonsAndTextfields.setSpacing(50);

        Label gridPaneTitel = new Label("Nuvarande uppgifter:");
        gridPaneTitel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        gridPaneTitel.setPadding(new Insets(0,0,0,10));

        VBox showCurrentMemberInfo = new VBox(gridPaneTitel, currentMemberInfoGridPane);
        showCurrentMemberInfo.setSpacing(7);
        showCurrentMemberInfo.setPadding(new Insets(10));


        BorderPane rootLayout = new BorderPane();
        rootLayout.setTop(windowTitel);
        rootLayout.setBottom(buttonsAndTextfields);
        rootLayout.setCenter(showCurrentMemberInfo);

        Scene scene = new Scene(rootLayout);
        editMemberStage.setScene(scene);
        editMemberStage.showAndWait();
    }
}
