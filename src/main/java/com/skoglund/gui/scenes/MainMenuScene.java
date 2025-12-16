package com.skoglund.gui.scenes;

import com.skoglund.gui.SceneHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainMenuScene {
    private SceneHandler sceneHandler;

    public MainMenuScene(){

    }
    public MainMenuScene(SceneHandler sceneHandler){
        this.sceneHandler = sceneHandler;

    }

    public Scene showMainMenu(){
        Label titleLabel = new Label("Välkommen till medlemsklubben för sportfiskare!");
        titleLabel.setStyle("-fx-font-size:33; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS';" +
                "-fx-font-weight: bold;");
        HBox windowTitelHBox = new HBox(titleLabel);
        windowTitelHBox.setAlignment(Pos.CENTER);

        Label subTitelLabel = new Label("Du har nu kommit till huvudmenyn");
        subTitelLabel.setStyle("-fx-font-size:31; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS';");

        Label menuDescriptionLabel = new Label("Här har du 3 val:");
        menuDescriptionLabel.setStyle("-fx-font-size:25; -fx-text-fill: white; -fx-font-family: 'Comic Sans MS';");

        VBox menuDescriptionVBox = new VBox(subTitelLabel, menuDescriptionLabel);

        VBox titelAndMenuDescriptionVBox = new VBox(windowTitelHBox, menuDescriptionVBox);
        titelAndMenuDescriptionVBox.setSpacing(30);

        menuDescriptionVBox.setSpacing(5);
        menuDescriptionVBox.setAlignment(Pos.CENTER);

        Label memberRegistryButtonLabel = new Label("För att hantera medlemskap och " +
                "visa medlemsregistret");
        memberRegistryButtonLabel.setStyle("-fx-font-size:18; -fx-text-fill: white;" +
                " -fx-font-family: 'Comic Sans MS';");
        Button showMemberRegistrySceneButton = new Button("KLICKA HÄR");
        showMemberRegistrySceneButton.setStyle("-fx-font-size:15; -fx-font-weight:bold;");
        VBox choiceMemberRegistryScene = new VBox(memberRegistryButtonLabel, showMemberRegistrySceneButton);
        choiceMemberRegistryScene.setSpacing(5);
        showMemberRegistrySceneButton.setOnAction(e ->{
            sceneHandler.switchToMemberRegistryScene();
        });

        Label rentalButtonLabel = new Label("För att hantera uthyrningar och visa " +
                "klubbens aktiva uthyrningar");
        rentalButtonLabel.setStyle("-fx-font-size:18; -fx-text-fill: white; " +
                "-fx-font-family: 'Comic Sans MS';");
        Button showRentalSceneButton = new Button("KLICKA HÄR");
        showRentalSceneButton.setStyle("-fx-font-size:15; -fx-font-weight:bold;");
        VBox choiceRentalScene = new VBox(rentalButtonLabel, showRentalSceneButton);
        choiceRentalScene.setAlignment(Pos.BOTTOM_CENTER);
        choiceRentalScene.setSpacing(5);
        showRentalSceneButton.setOnAction(e ->{
            sceneHandler.switchToRentalScene();
        });

        Label itemButtonLabel = new Label("För att hantera klubbens utrustning och " +
                "visa aktuellt lager");
        itemButtonLabel.setStyle("-fx-font-size:18; -fx-text-fill: white; " +
                "-fx-font-family: 'Comic Sans MS';");
        Button showItemSceneButton = new Button("KLICKA HÄR");
        showItemSceneButton.setStyle("-fx-font-size:15; -fx-font-weight:bold;");
        VBox choiceItemScene = new VBox(itemButtonLabel, showItemSceneButton);
        showItemSceneButton.setOnAction(e -> {
            sceneHandler.switchToItemScene();
        });

        //Root layouten (det yttersta skalet)
        BorderPane rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: #2C3E50; -fx-padding: 20;");
        rootLayout.setTop(titelAndMenuDescriptionVBox);
        rootLayout.setCenter(choiceRentalScene);
        rootLayout.setLeft(choiceMemberRegistryScene);
        rootLayout.setRight(choiceItemScene);

        //Scene
        Scene scene = new Scene(rootLayout);

        return scene;
    }
}
