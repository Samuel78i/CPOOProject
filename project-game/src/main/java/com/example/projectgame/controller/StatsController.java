package com.example.projectgame.controller;

import com.example.projectgame.model.Stat;
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

import java.util.List;

@Component
@FxmlView
public class StatsController {
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private AnchorPane anchor;
    private User user;
    private List<Stat> allStatsOfTheUser;
    private Stat statOfTheLastGame;

    @FXML
    private Label lastGamePrecision;
    @FXML
    private Label lastGameRegularity;
    @FXML
    private Label lastGameWPM;
    @FXML
    private Label overallPrecision;
    @FXML
    private Label overallRegularity;
    @FXML
    private Label overallWPM;



    @Autowired
    public StatsController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(anchor));
    }

    public void setUser(User u) {
        user = u;
        allStatsOfTheUser = user.getStats();
        statOfTheLastGame = user.getLastStat();
        setAllLabels();
    }

    private void setAllLabels() {
        lastGamePrecision.setText("" + statOfTheLastGame.getPrecision());
        lastGameRegularity.setText("" + statOfTheLastGame.getRegularity());
        lastGameWPM.setText("" + statOfTheLastGame.getWordPerMinutes());

        float op = 0;
        float or = 0;
        float ow = 0;
        for(Stat s : allStatsOfTheUser){
            op += s.getPrecision();
            or += s.getRegularity();
            ow += s.getWordPerMinutes();
        }

        op = op/allStatsOfTheUser.size();
        or = or/allStatsOfTheUser.size();
        ow = ow/allStatsOfTheUser.size();

        overallPrecision.setText("" + op);
        overallRegularity.setText("" + or);
        overallWPM.setText("" + ow);
    }

    @FXML
    protected void back(ActionEvent event) {
        MenuController menuController = fxWeaver.loadController(MenuController.class);
        menuController.setUser(user);
        menuController.show();
        stage.close();
    }

    public void show() {
        stage.show();
    }
}
