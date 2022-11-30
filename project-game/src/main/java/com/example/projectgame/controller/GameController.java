package com.example.projectgame.controller;

import com.example.projectgame.model.Game;
import com.example.projectgame.model.User;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
    private Label timerLabel;
    private Timer time;
    private long totalTime;

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
        area.setEditable(false);
        AnimateText(area, gameSentence);
        area.setLayoutX(103);
        area.setLayoutY(116);
        area.setPrefSize(400, 150);
        area.setOnKeyReleased((this::keyReleased));
        area.setFocusTraversable(true);
        area.requestFocus();
        area.displaceCaret(0);
        // to cancel character-removing keys
        area.addEventFilter(KeyEvent.KEY_PRESSED, Event::consume);
        // to cancel character keys
        area.addEventFilter(KeyEvent.KEY_TYPED, Event::consume);


        time = new Timer();
        stage.setOnCloseRequest(event -> time.cancel());

        anchor.getChildren().add(area);
    }

    public void starting() {
        totalTime = 180;

        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    long min = 0;
                    long sec = 0;
                    ConvertTime(totalTime);
                    totalTime--;
                    if (totalTime < 0) {
                        time.cancel();
                        timerLabel.setText("TIME OVER !");
                        isTheGameOverQuestionMark();

                    }
                });
            }
        };
        time.scheduleAtFixedRate(timertask, 0, 1000);

    }

    public void ConvertTime(long time) {
        long min = TimeUnit.SECONDS.toMinutes(time);
        long sec = time - (min * 60);
        timerLabel.setText("TIME LEFT: " + affiche(min) + ":" + affiche(sec));
    }

    public String affiche(long val) {
        if (val < 10) {
            return 0 + "" + val;
        } else
            return val + "";
    }

    @FXML
    protected void keyReleased(KeyEvent event) {
        if(event.getText().equals("\u0020") && (""+gameSentence.charAt(currentLetter)).equals("␣")){
            currentLetter++;
            game.aWordHasBeenValidate(badLetterCounter, currentLetter);
            badLetterCounter = 0;
            currentLetter = 0;
            isTheGameOverQuestionMark();
            updateVisualsBecauseWordValidate();
        }else if(event.getText().equals(""+gameSentence.charAt(currentLetter))){
            currentLetter++;
            badLetterCounter= 0;
            updateVisualsBecauseIsRight();
            isTheGameOverQuestionMark();
        }else if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE){
            if(badLetterCounter > 0){
                badLetterCounter--;
            }
            updateVisualsBecauseIsWrongOrBackspace();
        }else{
            badLetterCounter++;
            updateVisualsBecauseIsWrongOrBackspace();
        }
    }

    private void updateVisualsBecauseWordValidate(){
        gameSentence = game.getSentence();
        area.replaceText(gameSentence);
        area.setStyle(0, 0, gameSentence.length(), "-fx-fill: #000000");
        area.displaceCaret(0);
    }

    private void updateVisualsBecauseIsRight() {
        area.setStyle(0, 0, currentLetter, "-fx-fill: #00FF00");
        if(currentLetter < gameSentence.length()) {
            area.setStyle(0, currentLetter, gameSentence.length() - 1, "-fx-fill: #000000");
        }
        area.displaceCaret(currentLetter);
    }

    private void updateVisualsBecauseIsWrongOrBackspace() {
        area.setStyle(0, currentLetter, currentLetter + badLetterCounter, "-fx-fill: #FF0000");
        area.setStyle(0, currentLetter + badLetterCounter, gameSentence.length(), "-fx-fill: #000000");
        area.displaceCaret(currentLetter+badLetterCounter);
    }


    private void isTheGameOverQuestionMark(){
        if(currentLetter == gameSentence.length() || game.getLives() <= 0 || timerLabel.getText().equals("TIME OVER !")){
            ReplayController replayController = fxWeaver.loadController(ReplayController.class);
            //endController.setUser(user);
            replayController.show();
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
        animation.setOnFinished(e -> area.setEditable(true));
        animation.play();
    }

    public void setUser(User u){
        user = u;
    }

    public void show() {
        stage.show();
        starting();
    }


}
