package hellofx.reciptionfx;

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

public class ReciptionController {

    DatabaseConnection connectNow = DatabaseConnection.getObject();
    Connection connectDB = connectNow.getConnection();
    
    /****************************************************************** */
    /* ************************* Main Panel *************************** */
    /****************************************************************** */
    

    /* ************************* Main Logout *************************** */
    @FXML
    private Button mainlogout;
    public void mainlogoutOnAction(ActionEvent event) throws IOException{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../login.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    /* ************************* Main Cancel *************************** */
    @FXML
    private Button maincancel;
    public void maincancelOnAction(ActionEvent event){
        Stage stage = (Stage) maincancel.getScene().getWindow();
        stage.close();
    }

    /* **************************** Main Add *************************** */
    @FXML
    private Button mainAdd;
    public void mainAddOnOption(ActionEvent event) throws IOException{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ReciptionAdd.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    
    /* **************************** Main Edit *************************** */
    @FXML
    private Button mainEdit;
    public void mainEditOnAction(ActionEvent event) throws IOException{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ReciptionEdit.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    
    /* **************************** Main Room Info *************************** */
    @FXML
    private Button mainInfo;
    public void mainInfoOnAction(ActionEvent event) throws IOException{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ReciptionRoom.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    /* **************************** Main Checkout *************************** */
    @FXML
    private Button mainCheckout;
    public void mainCheckoutOnAction(ActionEvent event) throws IOException{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ReciptionCheckout.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    /****************************************************************** */
    /* ********************* Main Functionality *********************** */
    /****************************************************************** */

    @FXML
    private Button newResClearButton;
    @FXML
    private TextField newResConttxt;
    @FXML
    private TextField newResNametxt;
    @FXML
    private TextField newResstaytxt;
    @FXML
    private TextField newResServicetxt;
    @FXML
    private Label msg;

    public void newResClearButtonOnAction(ActionEvent event){
        newResNametxt.setText("");
        newResConttxt.setText("");
        newResstaytxt.setText("");
        newResServicetxt.setText("");
        msg.setText("");
    }

    @FXML
    private Button newResAddBtn;
    @FXML
    private ToggleGroup type;
    @FXML
    private ToggleGroup board;
    
    public void newResAddBtnOnAction(ActionEvent event) throws IOException{
        String sql = "INSERT INTO residents (r_name, r_contact, duration, service, room_type, boarding) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connectDB.prepareStatement(sql)) {
            
            pst.setString(1, newResNametxt.getText());
            pst.setString(2, newResConttxt.getText());
            pst.setString(3, newResstaytxt.getText());
            pst.setString(4, newResServicetxt.getText());
            
            String selectedRadio = "";
            for (Toggle toggle : type.getToggles()) {
                if (toggle.isSelected()) {
                    RadioButton radioButton = (RadioButton) toggle;
                    selectedRadio = radioButton.getText();
                }
            }
            pst.setString(5, selectedRadio);

            String selectedRadioboard = "";
            for (Toggle toggle : board.getToggles()) {
                if (toggle.isSelected()) {
                    RadioButton radioButton = (RadioButton) toggle;
                    selectedRadioboard = radioButton.getText();
                }
            }
            pst.setString(6, selectedRadioboard);
            
            if(pst.executeUpdate() == 1){

                PreparedStatement pst2 = connectDB.prepareStatement("SELECT r_id FROM residents WHERE r_name = ?");
                pst2.setString(1, newResNametxt.getText());
                ResultSet rs = pst2.executeQuery();
                if(rs.next()){
                    msg.setText("Data inserted successfully, ID = " + rs.getString("r_id"));
                    msg.setTextFill(Color.GREEN);
                } 
            }
            else{
                msg.setText("Something went wrong!");
                msg.setTextFill(Color.RED);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

    }


}
