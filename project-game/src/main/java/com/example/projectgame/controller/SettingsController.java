package com.example.projectgame.controller;

import com.example.projectgame.model.Settings;
import com.example.projectgame.model.User;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Pattern;

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
    private TextField timeTF;
    @FXML
    private TextField nowTF;
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
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

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
    public void timeValidate(){
        if(isNumeric(timeTF.getText())) {
            settings.setTime(Long.parseLong(timeTF.getText()));
            time.setText("Time is set at : " + timeTF.getText() + " min");
            timeTF.setText("");
        }else{
            time.setText("ERROR");
        }
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }


    @FXML
    public void SpeedSix(){
        settings.setSpeed(8);
        speed.setText("6");
    }
    @FXML
    public void SpeedEight(){
        settings.setSpeed(6);
        speed.setText("8");
    }
    @FXML
    public void SpeedNine(){
        settings.setSpeed(5);
        speed.setText("9");
    }

    @FXML
    public void nowValidate(){
        if(isNumeric(nowTF.getText())) {
            settings.setTime(Long.parseLong(nowTF.getText()));
            now.setText("Number of words is set at : " + nowTF.getText());
            nowTF.setText("");
        }else{
            now.setText("ERROR");
        }
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

    public void setOffline() {
        offline = true;
    }
}
