package com.example.projectgame.controller;

import com.example.projectgame.model.User;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@FxmlView
public class UserCreationController {
    private final FxWeaver fxWeaver;
    private final RestTemplate restTemplate;
    private Stage stage;
    @FXML
    private Tooltip tooltip;
    @FXML
    private Pane eye;
    @FXML
    private Label wrong;
    @FXML
    private AnchorPane anchor;
    @FXML
    private PasswordField userPassword;
    @FXML
    private ProgressBar progressBar = new ProgressBar(0);
    @FXML
    private TextField username;
    private boolean showPassword;
    @Value("${url}")
    private String url;

    @Autowired
    public UserCreationController(FxWeaver fxWeaver, RestTemplate restTemplate) {
        this.fxWeaver = fxWeaver;
        this.restTemplate = restTemplate;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(anchor));

        tooltip.setAutoHide(false);
        tooltip.setMinWidth(50);
        username.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().equals(" ")) {
                change.setText("");
            }
            return change;
        }));

        userPassword.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().equals(" ")) {
                change.setText("");
            }
            return change;
        }));
    }

    @FXML
    protected void passwordDifficult() {
        tooltip.setText(userPassword.getText());
        progressBar.setOpacity(1);
        if (userPassword.getLength() == 0) {
            progressBar.setProgress(0);
            progressBar.setStyle("-fx-accent: #ffffff;");
        }
        if (userPassword.getLength() < 3 && userPassword.getLength() > 0) {
            progressBar.setProgress(0.25);
            progressBar.setStyle("-fx-accent: #FF0000;");
        }
        if (userPassword.getLength() > 3) {
            progressBar.setProgress(0.5);
            progressBar.setStyle("-fx-accent: #FFA500;");
        }
        if (userPassword.getLength() > 5) {
            progressBar.setProgress(0.75);
            progressBar.setStyle("-fx-accent: #FFFF00;");
        }
        if (userPassword.getLength() > 7) {
            progressBar.setProgress(1);
            progressBar.setStyle("-fx-accent: #008000;");
        }
    }

    @FXML
    protected void validate() {
        //verif que le User n'existe pas deja
        if (!username.getText().isBlank() && !userPassword.getText().isBlank()) {
            User user = restTemplate.getForObject(
                    url + "/newUser?name={name}&password={password}", User.class, username.getText(), userPassword.getText());

            if (user == null) {
                wrong.setOpacity(1);
                username.setText("");
                userPassword.setText("");
            } else {
                MenuController menuController = fxWeaver.loadController(MenuController.class);
                menuController.setUser(user);
                menuController.show();

                stage = (Stage) username.getScene().getWindow();
                stage.close();
            }
        } else {
            wrong.setOpacity(1);
            username.setText("");
            userPassword.setText("");
        }
    }



    @FXML
    protected void changeEye() {
        if (showPassword) {
            showPassword = false;
            eye.setStyle("-fx-background-image: url('png/eyeClosed.png')");
            tooltip.hide();
        } else {
            showPassword = true;
            eye.setStyle("-fx-background-image: url('png/eyeOpen.png')");
            Point2D p = userPassword.localToScene(userPassword.getBoundsInLocal().getMaxX(), userPassword.getBoundsInLocal().getMaxY());
            tooltip.setText(userPassword.getText());
            tooltip.show(userPassword,
                    p.getX() + userPassword.getScene().getX() + userPassword.getScene().getWindow().getX(),
                    p.getY() + userPassword.getScene().getY() + userPassword.getScene().getWindow().getY());
        }
    }

    public void existingAccount() {
        fxWeaver.loadController(UserConnectionController.class).show();
        stage.close();
    }

    public void show() {
        stage.show();
    }
}
