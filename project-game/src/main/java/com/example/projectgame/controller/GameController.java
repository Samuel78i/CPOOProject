package com.example.projectgame.controller;

import com.example.projectgame.model.Game;
import com.example.projectgame.model.User;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.fxmisc.richtext.InlineCssTextArea;
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

    private InlineCssTextArea area;

    private String gameSentence;
    private int currentLetter;
    private int badLetterCounter = 0;


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
        area = new InlineCssTextArea();
        AnimateText(area, gameSentence);
        area.setLayoutX(103);
        area.setLayoutY(116);
        area.setPrefSize(400, 150);
        area.setOnKeyTyped((this::keyPressed));
        area.setOnKeyPressed((this::keyPressed));
        area.setOnKeyReleased((this::keyReleased));
        area.setFocusTraversable(true);
        area.requestFocus();
        area.displaceCaret(0);
        // to cancel character-removing keys
        area.addEventFilter(KeyEvent.KEY_PRESSED, Event::consume);
        anchor.getChildren().add(area);
    }

    protected void keyPressed(KeyEvent event){
        if(!(event.getCharacter().equals("\u0008") || event.getCharacter().equals("\u007F"))) {
            area.displaceCaret(gameSentence.length());
            area.deleteNextChar();
        }
    }

    @FXML
    protected void keyReleased(KeyEvent event) {
        if(event.getText().equals(""+gameSentence.charAt(currentLetter)) || (event.getText().equals("\u0020") && (""+gameSentence.charAt(currentLetter)).equals("␣"))){
            currentLetter++;
            badLetterCounter= 0;
            updateVisualsBecauseIsRight();
            isTheGameOverQuestionMark();
        }else if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE){
            updateVisualsForBackspace();
        }else{
            badLetterCounter++;
            updateVisualsBecauseIsWrong();
        }
    }


    private void updateVisualsForBackspace() {
        //TODO
    }
    private void updateVisualsBecauseIsRight() {
        area.setStyle(0, 0, currentLetter, "-fx-fill: #00FF00");
        if(currentLetter < gameSentence.length()) {
            area.setStyle(0, currentLetter, gameSentence.length() - 1, "-fx-fill: #000000");
        }
        area.displaceCaret(currentLetter);

    }

    private void updateVisualsBecauseIsWrong() {
        area.setStyle(0, currentLetter, currentLetter + badLetterCounter, "-fx-fill: #FF0000");
        area.displaceCaret(currentLetter+badLetterCounter);
    }


    private void isTheGameOverQuestionMark(){
        if(currentLetter == gameSentence.length()){
            EndController endController = fxWeaver.loadController(EndController.class);
            //endController.setUser(user);
            endController.show();
            stage.close();
        }
    }


    public void AnimateText(InlineCssTextArea area, String descImp) {
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
            }
            protected void interpolate(double frac) {
                final int length = descImp.length();
                final int n = Math.round(length * (float) frac);
                area.replaceText(descImp.substring(0, n));
            }
        };
        animation.play();
    }

    public void setUser(User u){
        user = u;
    }

    public void show() {
        stage.show();
    }


}
