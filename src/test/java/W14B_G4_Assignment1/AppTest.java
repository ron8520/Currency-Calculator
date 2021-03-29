package W14B_G4_Assignment1;

import W14B_G4_Assignment1.view.UIWindow;
import W14B_G4_Assignment1.App;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Application;
import javafx.stage.Stage;

public class AppTest {
  Stage primaryStage;
  App app;

  @BeforeEach
  public void AppTestSetUp(){
    app = new App();
  }

  @Test
  public void startTest(){
    try{
      app.start(primaryStage);
    }catch(Exception e){
      System.out.println("Exception error");
    }
  }
}
