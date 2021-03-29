package W14B_G4_Assignment1.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    static String username;
    private UIWindow window;
    private boolean isAdmin = false;

    @FXML
    private Button btlogin;

    @FXML
    private Button btclose;

    @FXML
    private TextField userField;

    @FXML
    private TextField passField;

    @FXML
    private CheckBox adminCheckBox;

    @Override
    public void initialize(URL url, ResourceBundle resources){

    }

    public LoginController(UIWindow window){
        this.window = window;
    }

    @FXML
    public void exitButton(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void loginButton(ActionEvent event){
        try {
            String username = userField.getText();
            String password = userField.getText();
            Boolean isAdmin = adminCheckBox.isSelected();
            window.setUsername(username);
            window.changeScene(event, isAdmin);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static String getUsername(){
        return username;
    }


}
