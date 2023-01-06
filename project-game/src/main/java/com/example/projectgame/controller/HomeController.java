package com.example.projectgame.controller;

import com.example.projectgame.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@FxmlView
public class HomeController {
    private final FxWeaver fxWeaver;
    private final RestTemplate restTemplate;

    @FXML
    private AnchorPane anchor;


    @Autowired
    public HomeController(FxWeaver fxWeaver,
                          RestTemplate restTemplate) {
        this.fxWeaver = fxWeaver;
        this.restTemplate = restTemplate;
    }


    @FXML
    protected void offline() {
        MenuController menuController = fxWeaver.loadController(MenuController.class);
        User user = new User("user", "");
        menuController.setUser(user);
        menuController.setOffline(true);
        menuController.show();

        Stage s = (Stage) anchor.getScene().getWindow();
        s.close();
    }

    @FXML
    protected void connection() {
        fxWeaver.loadController(UserConnectionController.class).show();

        Stage s = (Stage) anchor.getScene().getWindow();
        s.close();
    }

    @FXML
    protected void creation() {
        fxWeaver.loadController(UserCreationController.class).show();

        Stage s = (Stage) anchor.getScene().getWindow();
        s.close();
    }
}