package com.example.projectgame.controller;


import com.example.projectgame.model.Opponent;
import com.example.projectgame.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    @FXML
    private Label userName;

    @FXML
    private Label opponent;

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
    protected void replay(ActionEvent event) {
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

    @FXML
    protected void stat(){
        StatsController statsController = fxWeaver.loadController(StatsController.class);
        statsController.setUser(user);
        statsController.show();
        stage.close();
    }


    public void setUser(User user) {
        this.user = user;
        userName.setText(user.getName());
    }

    public void show() {
        stage.show();
    }

    public void setLastGameWasOnline(String o, boolean opponentLost) {
        if(opponentLost) {
            opponent.setText("You won against : " + o);
        }else{
            opponent.setText("You lost against : " + o);
        }
    }
}
