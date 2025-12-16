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
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    //Repository klasser
    MemberRegistry memberRegistry = new MemberRegistry();
    RentalRegistry rentalRegistry = new RentalRegistry();
    Inventory inventory = new Inventory();

    //Service klasser
    InventoryService inventoryService = new InventoryService(inventory);
    MembershipService membershipService = new MembershipService(memberRegistry);
    RentalService rentalService = new RentalService(rentalRegistry, inventoryService, membershipService);

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
        stage.show();


    }
}
