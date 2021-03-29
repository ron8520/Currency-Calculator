package W14B_G4_Assignment1.view;

import W14B_G4_Assignment1.model.Currency;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class UIWindow {

    private String date = "";
    private double x, y;
    private Stage primaryStage;
    private HomeController home;
    private LoginController login;
    private AdminController admin;
    private Scene loginScene;
    private Scene homeScene;
    private Scene adminScene;


    public UIWindow(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Currency_Converter");
        this.primaryStage.initStyle(StageStyle.UNDECORATED);

        URL loginUrl = new File("src/main/resource/login.fxml").toURL();
        URL homeUrl = new File("src/main/resource/home.fxml").toURL();
        URL adminUrl = new File("src/main/resource/admin.fxml").toURL();

        login = new LoginController(this);
        home = new HomeController(this);
        admin = new AdminController(this);

        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(loginUrl);
        loginLoader.setController(login);
        Parent root = loginLoader.load();
        this.loginScene = new Scene(root);
        this.primaryStage.setScene(loginScene);
        this.primaryStage.show();

        FXMLLoader homeLoader = new FXMLLoader();
        homeLoader.setLocation(homeUrl);
        homeLoader.setController(home);
        this.homeScene = new Scene(homeLoader.load());

        FXMLLoader adminLoader = new FXMLLoader();
        adminLoader.setLocation(adminUrl);
        adminLoader.setController(admin);
        this.adminScene = new Scene(adminLoader.load());

        this.date = "2020-10-07";
        Currency.setupFromJSON("src/main/java/W14B_G4_Assignment1/resources/ExchangeRates.json", date);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getSceneX() - x);
            primaryStage.setY(event.getSceneY() - y);
        });
    }

    public boolean changeScene(ActionEvent event, boolean isAdmin) throws IOException {
        primaryStage.close();
        if(isAdmin){
            primaryStage.setScene(adminScene);
            admin.updateTable("src/main/java/W14B_G4_Assignment1/resources/ExchangeRates.json", date);
        }else {
            primaryStage.setScene(homeScene);
            home.updateTable("src/main/java/W14B_G4_Assignment1/resources/ExchangeRates.json", date);
        }

        primaryStage.show();
        return true;
    }

    public boolean setUsername(String name){
        this.home.setUsername(name);
        return true;
    }

}
