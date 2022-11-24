package com.example.projectgame.controller;

import com.example.projectgame.model.User;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Arc;
import javafx.stage.Stage;
import javafx.util.Duration;
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

    private User user;

    @FXML
    private AnchorPane anchor;


    @Autowired
    public HomeController(FxWeaver fxWeaver,
                          RestTemplate restTemplate) {
        this.fxWeaver = fxWeaver;
        this.restTemplate = restTemplate;
    }


    @FXML
    protected void play(ActionEvent actionEvent){
        fxWeaver.loadController(GameController.class).show();
        Stage s = (Stage) anchor.getScene().getWindow();
        s.close();
    }

//    @FXML
//    protected void connection(ActionEvent actionEvent) {
//        fxWeaver.loadController(UserConnectionController.class).show();
//
//        Stage s = (Stage) anchor.getScene().getWindow();
//        s.close();
//    }
//
//    @FXML
//    protected void creation(ActionEvent actionEvent) {
//        fxWeaver.loadController(NewUserController.class).show();
//
//        Stage s = (Stage) anchor.getScene().getWindow();
//        s.close();
//    }
}