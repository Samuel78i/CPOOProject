package com.example.projectgame.controller;


import com.example.projectgame.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@FxmlView
public class ReplayController {
    private final FxWeaver fxWeaver;
    private User user;
    private Stage stage;

    @FXML
    private AnchorPane anchor;

    @Autowired
    public ReplayController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(anchor));
    }

    @FXML
    protected void ButtonYes(ActionEvent event) {
        GameController gameController = fxWeaver.loadController(GameController.class);
        gameController.setUser(user);
        gameController.show();
        stage.close();
    }

    @FXML
    protected void menu(){
        MenuController menuController = fxWeaver.loadController(MenuController.class);
        user.resetSetting();
        menuController.setUser(user);
        menuController.show();
        stage.close();
    }


    public void setUser(User user) {
        this.user = user;
    }
    public void show() {
        stage.show();
    }
}
