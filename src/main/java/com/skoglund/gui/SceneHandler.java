package com.skoglund.gui;

import com.skoglund.Main;
import com.skoglund.gui.scenes.ItemScene;
import com.skoglund.gui.scenes.MainMenuScene;
import com.skoglund.gui.scenes.MemberRegistryScene;
import com.skoglund.gui.scenes.RentalScene;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneHandler {
    private ItemScene itemScene;
    private MainMenuScene mainMenuScene;
    private MemberRegistryScene memberRegistryScene;
    private RentalScene rentalScene;
    private Stage stage;

    public SceneHandler(){

    }

    public void setItemScene(ItemScene itemScene) {
        this.itemScene = itemScene;
    }

    public void setMainMenuScene(MainMenuScene mainMenuScene) {
        this.mainMenuScene = mainMenuScene;
    }

    public void setMemberRegistryScene(MemberRegistryScene memberRegistryScene) {
        this.memberRegistryScene = memberRegistryScene;
    }

    public void setRentalScene(RentalScene rentalScene) {
        this.rentalScene = rentalScene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void switchToMainMenu(){
        stage.setScene(mainMenuScene.showMainMenu());
    }

    public void switchToItemScene(){
        stage.setScene(itemScene.showItemScene());
    }

    public void switchToMemberRegistryScene(){
        stage.setScene(memberRegistryScene.showMemberRegistryScene());
    }

    public void switchToRentalScene(){
        stage.setScene(rentalScene.showRentalScene());
    }
}
