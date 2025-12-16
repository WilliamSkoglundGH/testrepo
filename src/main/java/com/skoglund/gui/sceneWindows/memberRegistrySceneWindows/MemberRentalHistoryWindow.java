package com.skoglund.gui.sceneWindows.memberRegistrySceneWindows;

import com.skoglund.entity.Member;
import com.skoglund.entity.Rental;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class MemberRentalHistoryWindow {

    public void showRentalHistoryWindow(Member member){
        Stage rentalHistory = new Stage();
        rentalHistory.initModality(Modality.APPLICATION_MODAL);
        rentalHistory.setMinWidth(700);
        rentalHistory.setMinHeight(500);
        rentalHistory.setTitle("Medlemshistorik");

        Label titel = new Label("Uthyrningshistorik för medlem:" + member.getName());
        titel.setStyle("-fx-font-size:22; -fx-padding: 7px;" +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");
        HBox windowTitel = new HBox(titel);
        windowTitel.setAlignment(Pos.CENTER);

        VBox rentalHistoryVBox = new VBox();
        rentalHistoryVBox.setSpacing(5);
        rentalHistoryVBox.setPadding(new Insets(10));
        List<String> memberRentalHistory = member.getRentalHistory();
        if(memberRentalHistory.isEmpty()){
            Label noRentals = new Label("Medlem har inga uthyrningar");
            rentalHistoryVBox.getChildren().add(noRentals);
        }
        else{
            for(String history : memberRentalHistory){
                Label rentalInfo = new Label(history);
                rentalHistoryVBox.getChildren().add(rentalInfo);
            }
        }

        Button closeWindow = new Button("Stäng fönstret");
        closeWindow.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        closeWindow.setOnAction(e -> rentalHistory.close());

        HBox exitButton = new HBox(closeWindow);
        exitButton.setAlignment(Pos.CENTER);

        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(20));
        rootLayout.setTop(windowTitel);
        rootLayout.setCenter(rentalHistoryVBox);
        rootLayout.setBottom(exitButton);

        Scene scene = new Scene(rootLayout);
        rentalHistory.setScene(scene);
        rentalHistory.showAndWait();

    }
}
