package com.example.projectgame.controller;

import com.example.projectgame.model.Game;
import com.example.projectgame.model.Opponent;
import com.example.projectgame.model.TextColors;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.InlineCssTextArea;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
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
    private boolean online = false;
    private boolean offline = false;



    @FXML
    private AnchorPane anchor;
    @FXML
    private Label timerLabel;
    @FXML
    private Label lives;
    private Timer time;
    private boolean timerCanceled = false;
    private long totalTime;
    private int howLongIsMyGameSupposedToLast;
    private InlineCssTextArea area;
    private String gameSentence;

    //Counters
    private int currentLetter;
    private int badLetterCounter = 0;
    private int wrongLetterBeforeSpace = 0;
    private int validatedWords = 0;
    private int speed;
    private Opponent opponent;

    //stats
    private int rightCharacterCounter = 0;
    private float keyPressedCounter = 0;
    @Value("${url}")
    private String url;




    public GameController(FxWeaver fxWeaver, RestTemplate restTemplate) {
        this.fxWeaver = fxWeaver;
        this.restTemplate = restTemplate;
    }

    @FXML
    public void initialize() {
        currentLetter = 0;
        this.stage = new Stage();
        stage.setScene(new Scene(anchor));
    }

    public void starting() {
        totalTime = user.getSettings().getTime();
        howLongIsMyGameSupposedToLast = (int) totalTime;
        speed = user.getSettings().getSpeed();
        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    //update time
                    ConvertTime(totalTime);
                    totalTime--;

                    //adding every malus needed
                    if(online){
                        user = restTemplate.getForObject(
                                url + "/refreshUser?name={name}", User.class, user.getName());
                        assert user != null;
                        if(user.isOpponentLost()) {
                            gameOver();
                        }
                        for(int i = 0; i < Objects.requireNonNull(user).getMalus(); i++){
                            game.addAWordWithNoColor();
                            plusOneWord();
                        }
                    }


                    //adding a word
                    if(totalTime%speed == 0){
                        TextColors textColors = game.addAWord(online);
                        plusOneWord();
                        if(textColors != null){
                            if(textColors.getColors().equals("blue")) {
                                area.setStyle(0, textColors.getX(), textColors.getY(), "-fx-fill: #0000FF");
                            }else if(textColors.getColors().equals("pink")){
                                area.setStyle(0, textColors.getX(), textColors.getY(), "-fx-fill: #FFC0CB");
                            }
                        }
                        area.displaceCaret(currentLetter+ badLetterCounter);
                    }


                    lives.setText(String.valueOf(game.getLives()));


                    //timer over
                    if (totalTime < 0 && !timerCanceled) {
                        time.cancel();
                        timerCanceled = true;
                        timerLabel.setText("TIME OVER !");
                        isTheGameOverQuestionMark();

                    }
                });
            }
        };
        time.scheduleAtFixedRate(timertask, 0, 1000);

    }

    private void plusOneWord() {
        isTheLineFull();
        gameSentence += game.getLastWord();
        area.replaceText(gameSentence);
        area.setStyle(0, currentLetter + badLetterCounter, gameSentence.length(), "-fx-fill: #000000");
        updateAllColors(0);
        area.setStyle(0, 0, currentLetter, "-fx-fill: #00FF00");
        area.setStyle(0, currentLetter, currentLetter + badLetterCounter, "-fx-fill: #FF0000");
    }

    public void isTheLineFull(){
        if(game.howManyWords() > user.getSettings().getNumberOfWords()){
            for(int i = currentLetter + badLetterCounter; i < gameSentence.length(); i++){
                if(!(""+gameSentence.charAt(i)).equals("␣")){
                    badLetterCounter++;
                }
            }
            validateAWord();
        }
    }

    private void validateAWord() {
        currentLetter++;
        game.aWordHasBeenValidate(badLetterCounter, currentLetter);
        if(game.getLives() <= 0){
            gameOver();
        }

        validatedWords++;
        if(validatedWords == 100){
            speed--;
            validatedWords=0;
        }
        if(currentLetter - badLetterCounter > 0){
            rightCharacterCounter = currentLetter - badLetterCounter;
        }
        int numberOfLetterThatAreBeingErased = currentLetter + badLetterCounter;
        wasTheWordValidateAHealOrMalus(badLetterCounter);
        badLetterCounter = 0;
        currentLetter = 0;
        wrongLetterBeforeSpace = 0;
        isTheGameOverQuestionMark();
        updateVisualsBecauseWordValidate(numberOfLetterThatAreBeingErased);
    }

    public void ConvertTime(long time) {
        long min = TimeUnit.SECONDS.toMinutes(time);
        long sec = time - (min * 60);
        timerLabel.setText("TIME LEFT: " + stringTime(min) + ":" + stringTime(sec));
    }

    public String stringTime(long val) {
        if (val < 10) {
            return 0 + "" + val;
        } else
            return val + "";
    }

    @FXML
    protected void keyReleased(KeyEvent event) {
        keyPressedCounter++;
        if(event.getText().equals("\u0020") && (""+gameSentence.charAt(currentLetter + badLetterCounter)).equals("␣")){
            validateAWord();
        }else if(event.getText().equals(""+gameSentence.charAt(currentLetter))){
            currentLetter++;
            badLetterCounter= 0;
            updateVisualsBecauseIsRight();
            isTheGameOverQuestionMark();
        }else if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE){
            if(badLetterCounter > 0){
                badLetterCounter--;
            }
            if(wrongLetterBeforeSpace > 0) {
                gameSentence = gameSentence.substring(0, currentLetter + badLetterCounter)+ gameSentence.substring(currentLetter + badLetterCounter + 1);
                wrongLetterBeforeSpace--;
            }
            updateVisualsBecauseIsWrongOrBackspace();
        }else{
            if (("" + gameSentence.charAt(currentLetter + badLetterCounter)).equals("␣")) {
                gameSentence = gameSentence.substring(0, currentLetter + badLetterCounter) + event.getText() + gameSentence.substring(currentLetter + badLetterCounter);
                wrongLetterBeforeSpace++;
            }
            badLetterCounter++;
            updateVisualsBecauseIsWrongOrBackspace();
        }

        lives.setText(String.valueOf(game.getLives()));
    }

    public void wasTheWordValidateAHealOrMalus(int badLetterCounter){
        if(!game.getColorsList().isEmpty()) {
            if (game.getColorsList().get(0).getX() == 0) {
                String color = game.getColorsList().get(0).getColors();
                game.getColorsList().remove(0);
                if (color.equals("pink") && badLetterCounter == 0) {
                    RequestEntity<Opponent> request = RequestEntity.post(url + "/addAMalusToOpponent?userName={userName}", this.user.getName()).body(this.opponent);
                    restTemplate.exchange(request, Void.class);
                }
                if (color.equals("blue")) {
                    game.addLives(game.getColorsList().get(0).getY());
                }
            }
        }
    }

    private void updateVisualsBecauseWordValidate(int numberOfLetterThatAreBeingErased){
        gameSentence = game.getSentence();
        area.replaceText(gameSentence);
        area.setStyle(0, 0, gameSentence.length(), "-fx-fill: #000000");
        updateAllColors(numberOfLetterThatAreBeingErased);
        area.displaceCaret(0);
    }

    public void updateAllColors(int numberOfLetterThatAreBeingErased){
       for(TextColors t : game.getColorsList()){
            if(t.getColors().equals("blue")){
                t.setX(t.getX() - numberOfLetterThatAreBeingErased);
                t.setY(t.getY() - numberOfLetterThatAreBeingErased);
                area.setStyle(0, t.getX(), t.getY(), "-fx-fill: #0000FF");
            }else if(t.getColors().equals("pink")){
                t.setX(t.getX() - numberOfLetterThatAreBeingErased);
                t.setY(t.getY() - numberOfLetterThatAreBeingErased);
                area.setStyle(0, t.getX(), t.getY(), "-fx-fill: #FFC0CB");
            }
       }
    }


    private void updateVisualsBecauseIsRight() {
        area.setStyle(0, 0, currentLetter, "-fx-fill: #00FF00");
        if(currentLetter < gameSentence.length()) {
            area.setStyle(0, currentLetter, gameSentence.length() - 1, "-fx-fill: #000000");
        }
        updateAllColors(0);
        area.displaceCaret(currentLetter);
    }

    private void updateVisualsBecauseIsWrongOrBackspace() {
        area.replaceText(gameSentence);
        area.setStyle(0, 0, currentLetter, "-fx-fill: #00FF00");
        area.setStyle(0, currentLetter, currentLetter + badLetterCounter, "-fx-fill: #FF0000");
        area.setStyle(0, currentLetter + badLetterCounter, gameSentence.length(), "-fx-fill: #000000");
        updateAllColors(-wrongLetterBeforeSpace);
        area.displaceCaret(currentLetter+badLetterCounter);
    }


    private void isTheGameOverQuestionMark(){
        if(game.getLives() <= 0 || timerLabel.getText().equals("TIME OVER !")){
            gameOver();
        }
    }

    public void gameOver(){
        updateStats();

        ReplayController replayController = fxWeaver.loadController(ReplayController.class);
        replayController.setUser(user);
        if(online){
            replayController.setLastGameWasOnline(opponent.getName(), user.isOpponentLost());
            HttpEntity<String> request =
                    new HttpEntity<>(opponent.getName(), null);
            restTemplate.postForObject(url + "/userLost", request, String.class);
        }

        user.setOpponentLost(false);
        //push Stats
        if(!offline) {
            HttpEntity<User> request =
                    new HttpEntity<>(user, null);
            restTemplate.postForObject(url + "/addScore", request, User.class);
        }

        replayController.show();

        timerCanceled = true;
        time.cancel();

        stage.close();
    }

    public void updateStats(){
        float l = howLongIsMyGameSupposedToLast - totalTime;
        float WPM = (rightCharacterCounter / l) / 5;
        float precision = rightCharacterCounter / keyPressedCounter * 100;
        int regularity = 0;

        user.setStat(WPM, precision, regularity);
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
        animation.setOnFinished(e -> area.setDisable(false));
        animation.play();
    }

    public void setUser(User u){
        user = u;
        initGame();
    }

    private void initGame() {
        game = new Game(user.getSettings().getNumberOfWords(), user.getSettings().getLanguage());
        gameSentence = game.getSentence();
        initArea();

        time = new Timer();
        stage.setOnCloseRequest(event -> time.cancel());

        lives.setText(String.valueOf(game.getLives()));

        VirtualizedScrollPane<InlineCssTextArea> v = new VirtualizedScrollPane<>(area);
        v.setLayoutX(121);
        v.setLayoutY(120);
        v.setPrefSize(600, 350);
        area.setWrapText(true);
        anchor.getChildren().add(v);
    }

    private void initArea(){
        area = new InlineCssTextArea();
        area.replaceText(gameSentence);
        area.setDisable(true);
        AnimateText(area, gameSentence);
        area.setOnKeyReleased((this::keyReleased));
        area.setFocusTraversable(true);
        area.requestFocus();

        // to cancel character-removing keys
        area.addEventFilter(KeyEvent.KEY_PRESSED, Event::consume);
        // to cancel character keys
        area.addEventFilter(KeyEvent.KEY_TYPED, Event::consume);
        //to cancel clicks
        area.addEventFilter(MouseEvent.ANY, Event::consume);
    }

    public void show() {
        stage.show();
        starting();
    }

    public void setOnline(Opponent opponent) {
        online = true;
        this.opponent = opponent;
    }

    public void setOffline(boolean b) {
        offline = true;
    }
}
