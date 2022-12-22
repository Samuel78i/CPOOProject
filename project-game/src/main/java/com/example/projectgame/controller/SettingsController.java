package com.example.projectgame.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
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
    private final RestTemplate restTemplate;
    private Stage stage;
    @FXML
    private AnchorPane anchor;

    @Autowired
    public SettingsController(FxWeaver fxWeaver,
                              RestTemplate restTemplate) {
        this.fxWeaver = fxWeaver;
        this.restTemplate = restTemplate;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(anchor));
    }
}
