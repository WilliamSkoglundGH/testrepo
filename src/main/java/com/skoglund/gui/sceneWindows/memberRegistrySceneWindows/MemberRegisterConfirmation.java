package com.skoglund.gui.sceneWindows.memberRegistrySceneWindows;

import com.skoglund.entity.Member;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MemberRegisterConfirmation {

    public void showRegisterConfirmation(Member member) {
        Stage confirmationWindowStage = new Stage();
        confirmationWindowStage.initModality(Modality.APPLICATION_MODAL);
        confirmationWindowStage.setMinWidth(600);
        confirmationWindowStage.setMinHeight(400);
        confirmationWindowStage.setTitle("Bekräftelse av registrering");

        Label titleLabel = new Label("Registrering av medlem lyckad!");
        titleLabel.setStyle("-fx-font-size:20; -fx-padding: 7px; " +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");

        HBox windowTitelHBox = new HBox(titleLabel);
        windowTitelHBox.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Namn:");
        nameLabel.setStyle("-fx-font-weight: bold;");

        Label idLabel = new Label("ID:");
        idLabel.setStyle("-fx-font-weight: bold;");

        Label ageGroupLabel = new Label("Åldersgrupp:");
        ageGroupLabel.setStyle("-fx-font-weight: bold;");

        Label currentMemberNameLabel = new Label(member.getName());
        Label currentMemberIdLabel = new Label(member.getId());
        Label currentMemberAgeGroupLabel = new Label(member.getAgeGroup());

        GridPane currentMemberInfoGridPane = new GridPane();
        currentMemberInfoGridPane.setPadding(new Insets(10, 10, 10, 10));
        currentMemberInfoGridPane.setVgap(6);
        currentMemberInfoGridPane.setHgap(10);

        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(currentMemberNameLabel, 1, 0);

        GridPane.setConstraints(idLabel, 0, 1);
        GridPane.setConstraints(currentMemberIdLabel, 1, 1);

        GridPane.setConstraints(ageGroupLabel, 0, 2);
        GridPane.setConstraints(currentMemberAgeGroupLabel, 1, 2);

        currentMemberInfoGridPane.getChildren().addAll(nameLabel, currentMemberNameLabel, idLabel, currentMemberIdLabel,
                ageGroupLabel, currentMemberAgeGroupLabel);

        Button exitWindowButton = new Button("Stäng fönster");
        exitWindowButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        exitWindowButton.setOnAction(e -> confirmationWindowStage.close());

        HBox exitButtonHBox = new HBox(exitWindowButton);
        exitButtonHBox.setAlignment(Pos.CENTER);

        VBox rootLayout = new VBox(windowTitelHBox, currentMemberInfoGridPane, exitButtonHBox);
        rootLayout.setSpacing(20);
        rootLayout.setPadding(new Insets(0, 0, 0, 7));
        Scene scene = new Scene(rootLayout);
        confirmationWindowStage.setScene(scene);
        confirmationWindowStage.showAndWait();
    }
}
