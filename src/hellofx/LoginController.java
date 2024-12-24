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

    public void loginbtnOnAction(ActionEvent event) throws IOException{
        if((usernametxt.getText().isBlank() == false) && (passwordtxt.getText().isBlank() == false)){

            int res = validateLogin();

            // Getting new scene for the manager or the reciptionist.
            if(res == 1){
                String getTitle = "SELECT job_title FROM workers WHERE w_username = '" + usernametxt.getText() + "' AND w_password = '" + passwordtxt.getText() + "'";
                try (PreparedStatement pst = connectDB.prepareStatement(getTitle)) {
                    
                    ResultSet rs = pst.executeQuery();
                    if(rs.next() == true){

                        if((rs.getString("job_title")).equals("Manager")){
                            Node source = (Node) event.getSource();
                            Stage stage = (Stage) source.getScene().getWindow();

                            // Now you can set the new scene on the stage
                            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("managerfx/ManagerScene.fxml")));
                            stage.setScene(scene);
                            stage.show();
                        }
                        else if((rs.getString("job_title")).equals("Reciptioninst")){
                            Node source = (Node) event.getSource();
                            Stage stage = (Stage) source.getScene().getWindow();

                            // Now you can set the new scene on the stage
                            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("reciptionfx/ReciptionistScene.fxml")));
                            stage.setScene(scene);
                            stage.show();
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    e.getCause();
                }
            }
        }
        else{
            loginmsg.setText("Please enter username and password");
        }
    }

    public void cancelbButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    public int validateLogin(){

        String verifyLogin = "SELECT count(1) FROM workers WHERE w_username = '" + usernametxt.getText() + "' AND w_password = '" + passwordtxt.getText() + "'";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {

                if(queryResult.getInt(1) == 1){
                    loginmsg.setText("Congratulations!");
                    loginmsg.setTextFill(Color.GREEN);

                    return 1;
                }
                else{
                    loginmsg.setText("");
                    loginmsg.setText("Invalid login, Please try again");
                    loginmsg.setTextFill(Color.RED);
                    usernametxt.setText("");
                    passwordtxt.setText("");
                    return 0;
                }
                
            }
            
        } catch (Exception e) {
            e.getStackTrace();
            e.getCause();
        }
        return 0;

    }

}