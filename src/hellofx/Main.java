package hellofx;

import Room.Room;
import Room.Builder.DoubleRoom;
import Room.Builder.SingleRoom;
import Room.Builder.TripleRoom;
import Worker.Reciptionist;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SuppressWarnings("unused")
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 850, 543));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}