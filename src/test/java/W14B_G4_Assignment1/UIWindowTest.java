package W14B_G4_Assignment1;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import W14B_G4_Assignment1.view.*;

import W14B_G4_Assignment1.model.Currency;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class UIWindowTest{

    @Test
    public void testWindowShow1() throws InterruptedException{
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try{
                            App a = new App();
                            a.start(new Stage()); // Create and initialize your app.

                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        thread.start();


        thread.sleep(30000);
    }

    @Test
    public void testWindowShow2() throws InterruptedException{
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try{
                            App a = new App();
                            a.start(new Stage()); // Create and initialize your app.

                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        thread.start();


        thread.sleep(30000);
    }
}
