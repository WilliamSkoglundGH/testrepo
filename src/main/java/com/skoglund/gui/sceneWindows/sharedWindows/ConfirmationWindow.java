package com.skoglund.gui.sceneWindows.sharedWindows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmationWindow {

    public void showConfirmationWindow(String stageTitel, String titelLabel, String labelText) {
        Stage confirmationWindowStage = new Stage();
        confirmationWindowStage.initModality(Modality.APPLICATION_MODAL);
        confirmationWindowStage.setMinWidth(600);
        confirmationWindowStage.setMinHeight(400);
        confirmationWindowStage.setTitle(stageTitel);

        Label titleLabel = new Label(titelLabel);
        titleLabel.setStyle("-fx-font-size:22; -fx-padding: 7px;" +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");
        VBox windowTitelVBox = new VBox(titleLabel);
        windowTitelVBox.setAlignment(Pos.CENTER);

        Label infoLabel = new Label(labelText);
        infoLabel.setStyle("-fx-font-size:19; -fx-font-weight: bold;");

        Button exitWindowButton = new Button("Stäng fönster");
        exitWindowButton.setStyle("-fx-background-color: red;" +
                ";-fx-text-fill: white; -fx-font-weight: bold;");
        exitWindowButton.setOnAction(e -> confirmationWindowStage.close());
        VBox exitButtonVBox = new VBox(exitWindowButton);
        exitButtonVBox.setAlignment(Pos.CENTER);
        exitButtonVBox.setPadding(new Insets(0, 0, 20, 0));

        BorderPane rootLayout = new BorderPane();
        rootLayout.setTop(windowTitelVBox);
        rootLayout.setBottom(exitButtonVBox);
        rootLayout.setCenter(infoLabel);

        Scene scene = new Scene(rootLayout);
        confirmationWindowStage.setScene(scene);
        confirmationWindowStage.showAndWait();
    }
}
