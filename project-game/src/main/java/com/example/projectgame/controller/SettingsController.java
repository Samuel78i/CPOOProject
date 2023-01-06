package com.example.projectgame.controller;

import com.example.projectgame.model.Settings;
import com.example.projectgame.model.User;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
public class SettingsController {
    private final FxWeaver fxWeaver;
    private Stage stage;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Label language;
    @FXML
    private Label time;
    @FXML
    private Label now;
    @FXML
    private Label speed;
    @FXML
    private Button back;
    private User user;
    private Settings settings;
    private boolean offline = false;

    @Autowired
    public SettingsController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(anchor));
    }

    @FXML
    public void fr() {
        settings.setLanguage("fr");
        language.setText("French");
    }

    @FXML
    public void eng() {
        settings.setLanguage("en");
        language.setText("English");
    }

    @FXML
    public void ger() {
        settings.setLanguage("de");
        language.setText("German");
    }

    @FXML
    public void esp() {
        settings.setLanguage("es");
        language.setText("Spanish");
    }

    @FXML
    public void TimerOne() {
        settings.setTime(60);
        time.setText("1 min");
    }

    @FXML
    public void TimerTwo() {
        settings.setTime(120);
        time.setText("2 min");
    }

    @FXML
    public void TimerThree() {
        settings.setTime(180);
        time.setText("3 min");
    }

    @FXML
    public void TimerFour() {
        settings.setTime(240);
        time.setText("4 min");
    }

    @FXML
    public void TimerFive() {
        settings.setTime(300);
        time.setText("5 min");
    }

    @FXML
    public void TimerSix() {
        settings.setTime(360);
        time.setText("6 min");
    }

    @FXML
    public void SpeedSix(){
        settings.setSpeed(6);
        speed.setText("6");
    }
    @FXML
    public void SpeedEight(){
        settings.setSpeed(8);
        speed.setText("8");
    }
    @FXML
    public void SpeedNine(){
        settings.setSpeed(9);
        speed.setText("9");
    }

    @FXML
    public void nowTwenty(){
        settings.setNumberOfWords(20);
        now.setText("20");
    }
    @FXML
    public void nowTwentyFive(){
        settings.setNumberOfWords(25);
        now.setText("25");
    }
    @FXML
    public void nowThirty(){
        settings.setNumberOfWords(30);
        now.setText("30");
    }


    @FXML
    public void validate(){
        user.setSettings(settings);

        GameController gameController = fxWeaver.loadController(GameController.class);
        gameController.setUser(user);
        if(offline){
            gameController.setOffline(true);
        }
        gameController.show();
        stage.close();
    }

    @FXML
    public void back(){
        MenuController menuController = fxWeaver.loadController(MenuController.class);
        user.resetSetting();
        menuController.setUser(user);
        if(offline){
            menuController.setOffline(true);
        }
        menuController.show();

        stage.close();
    }
    public void setUser(User u){
        user = u;
        settings = user.getSettings();
    }

    public void show() {
        stage.show();
    }
}
