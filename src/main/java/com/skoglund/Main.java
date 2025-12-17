package com.skoglund;

import com.skoglund.gui.SceneHandler;
import com.skoglund.gui.sceneWindows.sharedWindows.ConfirmationWindow;
import com.skoglund.gui.scenes.ItemScene;
import com.skoglund.gui.scenes.MainMenuScene;
import com.skoglund.gui.scenes.MemberRegistryScene;
import com.skoglund.gui.scenes.RentalScene;
import com.skoglund.repository.Inventory;
import com.skoglund.repository.MemberRegistry;
import com.skoglund.repository.RentalRegistry;
import com.skoglund.service.InventoryService;
import com.skoglund.service.MembershipService;
import com.skoglund.service.RentalService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    //Repository klasser
    MemberRegistry memberRegistry;
    RentalRegistry rentalRegistry;
    Inventory inventory;

    //Service klasser
    InventoryService inventoryService;
    MembershipService membershipService;
    RentalService rentalService;

    //GUI bekräftelse fönster (delat av alla scener)
    ConfirmationWindow confirmationWindow = new ConfirmationWindow();

    Stage stage;

    //Scener
    ItemScene itemScene;
    MemberRegistryScene memberRegistryScene;
    RentalScene rentalScene;
    MainMenuScene mainMenuScene;

    //Scen hanteraren
    SceneHandler sceneHandler;


    @Override
    public void start(Stage stage) throws Exception {
        try{
            memberRegistry = new MemberRegistry();
            rentalRegistry = new RentalRegistry();
            inventory = new Inventory();
        }catch(IOException e){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Fel i applikationen");
            errorAlert.setContentText("Applikationens data är korrupt, kontakta administration\n" +
                    "Applikationen stängs ner!");
            System.out.println("FEL: Inläsning av fil misslyckad: " + e.getMessage());
            errorAlert.showAndWait();
            Platform.exit();
        }
        inventoryService = new InventoryService(inventory);
        membershipService = new MembershipService(memberRegistry);
        rentalService = new RentalService(rentalRegistry,inventoryService,membershipService);

        sceneHandler = new SceneHandler();

        itemScene = new ItemScene(inventory, inventoryService, confirmationWindow, sceneHandler);
        memberRegistryScene = new MemberRegistryScene(memberRegistry, membershipService, confirmationWindow,
                sceneHandler);
        rentalScene = new RentalScene(rentalService, confirmationWindow, inventoryService,
                memberRegistry, sceneHandler, inventory);
        mainMenuScene = new MainMenuScene(sceneHandler);

        sceneHandler.setItemScene(itemScene);
        sceneHandler.setMemberRegistryScene(memberRegistryScene);
        sceneHandler.setRentalScene(rentalScene);
        sceneHandler.setMainMenuScene(mainMenuScene);
        sceneHandler.setStage(stage);

        stage.setScene(mainMenuScene.showMainMenu());
        stage.setMaximized(true);
        stage.show();


    }
}
