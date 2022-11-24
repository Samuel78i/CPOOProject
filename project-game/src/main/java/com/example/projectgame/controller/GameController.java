package com.example.projectgame.controller;

import com.example.projectgame.model.Game;
import com.example.projectgame.model.User;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@FxmlView
public class GameController {

    private static Game game;
    private final FxWeaver fxWeaver;
    private final RestTemplate restTemplate;
    private Stage stage;
    private User user;

    @FXML
    private AnchorPane anchor;
    @FXML
    private Label sentence;

    private String gameSentence;
    private int currentLetter;


    public GameController(FxWeaver fxWeaver, RestTemplate restTemplate) {
        this.fxWeaver = fxWeaver;
        this.restTemplate = restTemplate;
    }

    @FXML
    public void initialize() {
        currentLetter = 0;
        this.stage = new Stage();
        stage.setScene(new Scene(anchor));
        game = new Game(1);
        gameSentence = game.getSentence();
        sentence.setText(gameSentence);
    }

    @FXML
    protected void keyTyped(KeyEvent event) {
        if(event.getText().equals(""+gameSentence.charAt(currentLetter))){
            currentLetter++;
            updateVisualsBecauseIsRight();
            isTheGameOverQuestionMark();
        }else {
            updateVisualsBecauseIsWrong();
        }
    }

    private void isTheGameOverQuestionMark(){
        if(currentLetter == gameSentence.length()){
            EndController endController = fxWeaver.loadController(EndController.class);
            //endController.setUser(user);
            endController.show();
            stage.close();
        }
    }

    private void updateVisualsBecauseIsRight() {
    }

    private void updateVisualsBecauseIsWrong() {
    }


    public void setUser(User u){
        user = u;
    }

    public void show() {
        stage.show();
    }


}
