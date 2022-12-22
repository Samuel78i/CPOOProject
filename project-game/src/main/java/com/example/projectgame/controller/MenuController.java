package com.example.projectgame.controller;

import com.example.projectgame.model.Stat;
import com.example.projectgame.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class MenuController {
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private AnchorPane anchor;
    private User user;

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
        GameController gameController = fxWeaver.loadController(GameController.class);
        gameController.setUser(user);
        gameController.show();
        stage.close();
    }

    @FXML
    protected void buttonOnline(ActionEvent event) {
        ChooseYourOpponentsController chooseYourOpponentsController = fxWeaver.loadController(ChooseYourOpponentsController.class);
        chooseYourOpponentsController.setUser(user);
        chooseYourOpponentsController.show();
        stage.close();
    }

    @FXML
    protected void buttonStat(ActionEvent event) {
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
}
