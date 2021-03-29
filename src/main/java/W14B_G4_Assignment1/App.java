package W14B_G4_Assignment1;

import W14B_G4_Assignment1.view.UIWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{

    @Override
    public void start (Stage primaryStage) throws Exception{
        UIWindow window = new UIWindow(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
