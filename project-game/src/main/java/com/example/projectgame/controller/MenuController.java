package com.example.projectgame.controller;

import com.example.projectgame.model.Stat;
import com.example.projectgame.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class MenuController {
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private AnchorPane anchor;
    @FXML
    private Button online;
    private User user;
    private boolean offline = false;

    @Autowired
    public MenuController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(anchor));
    }

    @FXML
    protected void buttonSOLO() {
        SettingsController settingsController = fxWeaver.loadController(SettingsController.class);
        settingsController.setUser(user);
        if(offline) {
            settingsController.setOffline();
        }
        settingsController.show();
        stage.close();
    }

    @FXML
    protected void buttonOnline() {
        ChooseYourOpponentsController chooseYourOpponentsController = fxWeaver.loadController(ChooseYourOpponentsController.class);
        chooseYourOpponentsController.setUser(user);
        chooseYourOpponentsController.show();
        stage.close();
    }

    @FXML
    protected void buttonStat() {
        StatsController statsController = fxWeaver.loadController(StatsController.class);
        statsController.setUser(user);
        statsController.show();
        stage.close();
    }

    public void setUser(User u) {
        user = u;
    }
    public void show() {
        stage.show();
    }

    public void setOffline(boolean b) {
        offline =true;
        online.setDisable(true);
        online.setOpacity(0);
    }
}
