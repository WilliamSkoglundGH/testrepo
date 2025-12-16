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
        Stage confirmationWindow = new Stage();
        confirmationWindow.initModality(Modality.APPLICATION_MODAL);
        confirmationWindow.setMinWidth(600);
        confirmationWindow.setMinHeight(400);
        confirmationWindow.setTitle("Bekräftelse av registrering");

        Label titel = new Label("Registrering av medlem lyckad!");
        titel.setStyle("-fx-font-size:20; -fx-padding: 7px; " +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");

        HBox windowTitel = new HBox(titel);
        windowTitel.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Namn:");
        nameLabel.setStyle("-fx-font-weight: bold;");

        Label idLabel = new Label("ID:");
        idLabel.setStyle("-fx-font-weight: bold;");

        Label ageGroupLabel = new Label("Åldersgrupp:");
        ageGroupLabel.setStyle("-fx-font-weight: bold;");

        Label currentMemberName = new Label(member.getName());
        Label currentMemberId = new Label(member.getId());
        Label currentMemberAgeGroup = new Label(member.getAgeGroup());

        GridPane currentMemberInfoGridPane = new GridPane();
        currentMemberInfoGridPane.setPadding(new Insets(10, 10, 10, 10));
        currentMemberInfoGridPane.setVgap(6);
        currentMemberInfoGridPane.setHgap(10);

        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(currentMemberName, 1, 0);

        GridPane.setConstraints(idLabel, 0, 1);
        GridPane.setConstraints(currentMemberId, 1, 1);

        GridPane.setConstraints(ageGroupLabel, 0, 2);
        GridPane.setConstraints(currentMemberAgeGroup, 1, 2);

        currentMemberInfoGridPane.getChildren().addAll(nameLabel, currentMemberName, idLabel, currentMemberId,
                ageGroupLabel, currentMemberAgeGroup);

        Button exitWindow = new Button("Stäng fönster");
        exitWindow.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        exitWindow.setOnAction(e -> confirmationWindow.close());

        HBox exitButton = new HBox(exitWindow);
        exitButton.setAlignment(Pos.CENTER);

        VBox rootLayout = new VBox(windowTitel, currentMemberInfoGridPane, exitButton);
        rootLayout.setSpacing(20);
        rootLayout.setPadding(new Insets(0, 0, 0, 7));
        Scene scene = new Scene(rootLayout);
        confirmationWindow.setScene(scene);
        confirmationWindow.showAndWait();
    }
}
