package com.skoglund.gui.sceneWindows.memberRegistrySceneWindows;

import com.skoglund.entity.Member;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class MemberRentalHistoryWindow {

    public void showRentalHistoryWindow(Member member){
        Stage rentalHistoryStage = new Stage();
        rentalHistoryStage.initModality(Modality.APPLICATION_MODAL);
        rentalHistoryStage.setMinWidth(700);
        rentalHistoryStage.setMinHeight(500);
        rentalHistoryStage.setTitle("Medlemshistorik");

        Label titleLabel = new Label("Uthyrningshistorik för medlem:" + member.getName());
        titleLabel.setStyle("-fx-font-size:22; -fx-padding: 7px;" +
                "-fx-font-family:'Comic Sans MS'; -fx-font-weight: bold;");
        HBox windowTitelHBox = new HBox(titleLabel);
        windowTitelHBox.setAlignment(Pos.CENTER);

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

        Button closeWindowButton = new Button("Stäng fönstret");
        closeWindowButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        closeWindowButton.setOnAction(e -> rentalHistoryStage.close());

        HBox exitButtonHBox = new HBox(closeWindowButton);
        exitButtonHBox.setAlignment(Pos.CENTER);

        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(20));
        rootLayout.setTop(windowTitelHBox);
        rootLayout.setCenter(rentalHistoryVBox);
        rootLayout.setBottom(exitButtonHBox);

        Scene scene = new Scene(rootLayout);
        rentalHistoryStage.setScene(scene);
        rentalHistoryStage.showAndWait();

    }
}
