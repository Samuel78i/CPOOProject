package com.example.projectgame.controller;

import com.example.projectgame.model.Opponent;
import com.example.projectgame.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@FxmlView
public class ChooseYourOpponentsController {
    private final FxWeaver fxWeaver;
    private final RestTemplate restTemplate;

    @FXML
    public AnchorPane anchor;
    @FXML
    public TextField opponents;
    private Stage stage;
    private User user;
    @FXML
    private ListView<Button> opponentsList;
    @FXML
    private Label wrong;
    @FXML
    private Button back;
    @Value("${url}")
    private String url;

    @Autowired
    public ChooseYourOpponentsController(FxWeaver fxWeaver,
                                         RestTemplate restTemplate) {
        this.fxWeaver = fxWeaver;
        this.restTemplate = restTemplate;
    }

    public void setUser(User user) {
        this.user = restTemplate.getForObject(
                url + "/userConnection?name={name}&password={password}", User.class, user.getName(), user.getPassword());
        loadListView();
    }

    private void loadListView() {
        for (Opponent o : user.getOpponents()) {
            Button b;
            if (!o.isTurn()) {
                b = new Button(o.getName());
                EventHandler<ActionEvent> event = e -> {
                    waitingForTheOpponent(o);
                    GameController gameController = fxWeaver.loadController(GameController.class);
                    gameController.setUser(user);
                    gameController.setOnline(o);
                    gameController.show();
                    stage.close();
                };
                b.setOnAction(event);
            } else {
                b = new Button("en attente de la partie de " + o.getName());
                b.setDisable(true);
            }
            opponentsList.getItems().add(b);
        }
    }

    public void disableEveryButton(){
        opponents.setDisable(true);
        opponentsList.setDisable(true);
        back.setDisable(true);
    }

    public void activateEveryButton(){
        opponents.setDisable(false);
        opponentsList.setDisable(false);
        back.setDisable(false);
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(anchor));
    }

    @FXML
    protected void validate() {
        boolean exist = Boolean.TRUE.equals(restTemplate.getForObject(
                url + "/opponentsExist?userName={userName}&opponentName={opponentName}", boolean.class, user.getName(), opponents.getText()));
        if (exist) {
            Opponent opponent = restTemplate.getForObject(url + "/addOpponent?userName={userName}&opponentName={opponentName}", Opponent.class, user.getName(), opponents.getText());
            user.addOpponents(opponent);

            waitingForTheOpponent(opponent);

            GameController gameController = fxWeaver.loadController(GameController.class);
            user.setWaitingOnOpponents(false);
            gameController.setUser(user);
            gameController.setOnline(opponent);
            gameController.show();
            stage.close();
        } else {
            wrong.setOpacity(1);
            opponents.setText("");
        }
    }

    public void waitingForTheOpponent(Opponent o){
        Boolean isMyOpponentHere = restTemplate.getForObject(
                url + "/isMyOpponentHere?name={name}&opponentName={opponentName}", Boolean.class , user.getName(), o.getName());
        while(Boolean.FALSE.equals(isMyOpponentHere)){
            disableEveryButton();
            isMyOpponentHere = restTemplate.getForObject(
                    url + "/isMyOpponentHere?name={name}&opponentName={opponentName}", Boolean.class , user.getName(), o.getName());
        }
        activateEveryButton();
    }

    @FXML
    protected void back() {
        try {
            MenuController chooseGameModeController = fxWeaver.loadController(MenuController.class);
            chooseGameModeController.setUser(user);
            chooseGameModeController.show();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show() {
        stage.show();
    }

}
