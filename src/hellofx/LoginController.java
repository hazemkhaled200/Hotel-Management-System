package hellofx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.Thread.State;
import java.sql.Connection;

import javafx.event.ActionEvent;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("unused")
public class LoginController {

    @FXML
    private Button cancelbtn;

    @FXML
    private Label loginmsg;

    @FXML
    private Button loginbtn;

    @FXML
    private TextField usernametxt;

    @FXML
    private PasswordField passwordtxt;

    DatabaseConnection connectNow = DatabaseConnection.getObject();
    Connection connectDB = connectNow.getConnection();

    private final ILoginValidator loginValidator = new LoginValidatorProxy(new RealLoginValidator(connectDB));

    public void loginbtnOnAction(ActionEvent event) throws IOException {
        if ((usernametxt.getText().isBlank() == false) && (passwordtxt.getText().isBlank() == false)) {

            // int res = validateLogin();
            int res = loginValidator.validateLogin(usernametxt.getText(), passwordtxt.getText());
            String title = loginValidator.getTitle();
            System.out.println(title);

            // Getting new scene for the manager or the reciptionist.
            if (res == 1) {
                if (title == "Manager") {
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    Scene scene = new Scene(
                            FXMLLoader.load(getClass().getResource("managerfx/ManagerScene.fxml")));
                    stage.setScene(scene);
                    stage.show();
                } else {
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    Scene scene = new Scene(
                            FXMLLoader.load(getClass().getResource("reciptionfx/ReciptionistScene.fxml")));
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                loginmsg.setText("");
                loginmsg.setText("Invalid login, Please try again");
                loginmsg.setTextFill(Color.RED);
                usernametxt.setText("");
                passwordtxt.setText("");
            }
        } else {
            loginmsg.setText("Please enter username and password");
        }
    }

    public void cancelbButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

}