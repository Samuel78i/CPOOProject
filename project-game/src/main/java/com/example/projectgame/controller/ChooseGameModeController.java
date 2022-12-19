package com.example.projectgame.controller;

import com.example.projectgame.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class ChooseGameModeController {
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private AnchorPane anchor;
    private User user;

    public ChooseGameModeController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
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

    public void setUser(User u) {
        user = u;
    }
    public void show() {
        stage.show();
    }
}
