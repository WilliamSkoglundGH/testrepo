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
        Stage noMatchFoundWindow = new Stage();
        noMatchFoundWindow.initModality(Modality.APPLICATION_MODAL);
        noMatchFoundWindow.setMinWidth(600);
        noMatchFoundWindow.setMinHeight(400);
        noMatchFoundWindow.setTitle(stageTitel);

        Label titel = new Label(titelLabel);
        titel.setStyle("-fx-font-size:22; -fx-padding: 7px;" +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");
        VBox windowTitel = new VBox(titel);
        windowTitel.setAlignment(Pos.CENTER);

        Label info = new Label(labelText);
        info.setStyle("-fx-font-size:19; -fx-font-weight: bold;");

        Button exitWindow = new Button("Stäng fönster");
        exitWindow.setStyle("-fx-background-color: red;" +
                ";-fx-text-fill: white; -fx-font-weight: bold;");
        exitWindow.setOnAction(e -> noMatchFoundWindow.close());
        VBox exitButton = new VBox(exitWindow);
        exitButton.setAlignment(Pos.CENTER);
        exitButton.setPadding(new Insets(0, 0, 20, 0));

        BorderPane rootLayout = new BorderPane();
        rootLayout.setTop(windowTitel);
        rootLayout.setBottom(exitButton);
        rootLayout.setCenter(info);

        Scene scene = new Scene(rootLayout);
        noMatchFoundWindow.setScene(scene);
        noMatchFoundWindow.showAndWait();
    }
}
