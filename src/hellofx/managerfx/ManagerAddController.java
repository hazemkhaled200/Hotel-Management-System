package hellofx.managerfx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hellofx.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ManagerAddController {

    DatabaseConnection connectNow = DatabaseConnection.getObject();
    Connection connectDB = connectNow.getConnection();
    
    /****************************************************************** */
    /* ************************* Main Panel *************************** */
    /****************************************************************** */

    /* ************************* Main Logout *************************** */
    @FXML
    private Button logoutbtn;
    @FXML
    private Button cancelbtn;
    public void logoutbtnOnAction(ActionEvent event) throws IOException{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../login.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    /* ************************* Main Cancel *************************** */
    public void cancelbButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        stage.close();
    }

    /* ************************* Main Add *************************** */
    @FXML
    private Button addbtn;
    public void addbtnOnAction(ActionEvent event) throws IOException{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ManagerAdd.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    
    /* ************************* Main Edit *************************** */
    @FXML
    private Button editbtn;
    public void editbtnOnAction(ActionEvent event) throws IOException{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ManagerEdit.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    
    /* ************************* Main Display Emp *************************** */
    @FXML
    private Button displayempbtn;
    public void displayempbtnOnAction(ActionEvent event) throws IOException{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ViewEmps.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    /* ************************* Main Display Room *************************** */
    @FXML
    private Button displayroombtn;
    public void displayroombtnOnAction(ActionEvent event) throws IOException{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ViewRooms.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    /* ************************* Main Display Resident *************************** */
    @FXML
    private Button displayresbtn;
    public void displayresbtnOnAction(ActionEvent event) throws IOException{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ViewRes.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    /* ************************* Main Income *************************** */
    @FXML
    private Button revenuebtn;
    public void revenuebtnOnAction(ActionEvent event) throws IOException{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Income.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    /************************************************************************** */
    /* ****************** Main Funciontionalities ***************************** */
    /************************************************************************** */

    @FXML
    private Button clearbtn;
    @FXML
    private TextField nametxt;
    @FXML
    private TextField usernametxt;
    @FXML
    private TextField passwordtxt;
    @FXML
    private TextField contacttxt;
    @FXML
    private TextField salarytxt;
    @FXML
    private Button newbtn;
    @FXML
    private ToggleGroup title;
    @FXML
    private Label labelmsg;

    public void newbtnOnAction(ActionEvent event){
        
            String sql = "INSERT INTO workers (w_username, w_password, w_name, w_contact_info, job_title, salary) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pst = connectDB.prepareStatement(sql)) {
                
                pst.setString(1, usernametxt.getText());
                pst.setString(2, passwordtxt.getText());
                pst.setString(3, nametxt.getText());
                pst.setString(4, contacttxt.getText());
                String selectedRadio = "";
                for (Toggle toggle : title.getToggles()) {
                    if (toggle.isSelected()) {
                        RadioButton radioButton = (RadioButton) toggle;
                        selectedRadio = radioButton.getText();
                    }
                }
                pst.setString(5, selectedRadio);
                pst.setInt(6, Integer.parseInt(salarytxt.getText()));
                
                if(pst.executeUpdate() == 1){

                    PreparedStatement pst2 = connectDB.prepareStatement("SELECT w_id FROM workers WHERE w_username = ?");
                    pst2.setString(1, usernametxt.getText());

                    ResultSet rs = pst2.executeQuery();

                    if(rs.next()){
                        labelmsg.setText("Data inserted successfully, ID = " + rs.getString("w_id"));
                        labelmsg.setTextFill(Color.GREEN);
                    } 
                }
                else{
                    labelmsg.setText("Something went wrong!");
                    labelmsg.setTextFill(Color.RED);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }

    }

    public void clearbtnOnAction(ActionEvent event){

        nametxt.setText("");
        usernametxt.setText("");
        passwordtxt.setText("");
        contacttxt.setText("");
        salarytxt.setText("");

    }
    
}
