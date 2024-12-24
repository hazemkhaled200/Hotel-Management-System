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

public class ReciptionEditController {

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
    private TextField editResIdtxt;
    @FXML
    private TextField editResNametxt;
    @FXML
    private TextField editResConttxt;
    @FXML
    private TextField editResDurtxt;
    @FXML
    private TextField editResSertxt;
    @FXML 
    private Button editResSearchBtn;
    @FXML 
    private Button editResEditBtn;
    @FXML 
    private Button editResDeleteBtn;
    @FXML 
    private Button editResClearBtn;
    @FXML
    private Label msg2;
    @FXML
    private ToggleGroup roomType;
    @FXML
    private ToggleGroup boarding;
    @FXML
    private RadioButton srb;
    @FXML
    private RadioButton drb;
    @FXML
    private RadioButton trb;
    @FXML
    private RadioButton fbrb;
    @FXML
    private RadioButton hbrb;
    @FXML
    private RadioButton bbrb;
    @FXML
    private Label lll;

    public void editResClearBtnOnAction(ActionEvent event){
        editResIdtxt.setText("");
        editResNametxt.setText("");
        editResConttxt.setText("");
        editResDurtxt.setText("");
        editResSertxt.setText("");
        msg2.setText("");
    }

    public void editResSearchBtnOnAction(ActionEvent event){
        msg2.setText("");
        if((editResIdtxt.getText().isBlank() == false)){

            String getTitle = "SELECT * FROM residents WHERE r_id = '" + editResIdtxt.getText() + "'";
            try (PreparedStatement pst = connectDB.prepareStatement(getTitle)) {
                
                ResultSet rs = pst.executeQuery();

                if(rs.next() == true){

                    editResNametxt.setText(rs.getString("r_name"));
                    editResConttxt.setText(rs.getString("r_contact"));
                    editResDurtxt.setText(rs.getString("duration"));
                    editResSertxt.setText(rs.getString("service"));
                    String type = rs.getString("room_type");
                    String board = rs.getString("boarding");

                    switch (type) {
                        case "Single":
                            roomType.selectToggle(srb);
                            break;
                        case "Double":
                            roomType.selectToggle(drb);
                            break;
                        case "Triple":
                            roomType.selectToggle(trb);
                            break;
                    }

                    switch (board) {
                        case "Full Board":
                            boarding.selectToggle(fbrb);
                            break;
                        case "Half Board":
                            boarding.selectToggle(hbrb);
                            break;
                        case "Bed And Breakfast":
                            boarding.selectToggle(bbrb);
                            break;
                    }

                }
                else{
                    msg2.setText("Invalid ID");
                    msg2.setTextFill(Color.RED);
                }

            } 
            catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }
        }
        else{
            msg2.setText("Please enter the ID");
            msg2.setTextFill(Color.RED);
        }
    }

    public void editResDeleteBtnOnAction(ActionEvent event){

        String sql = "DELETE FROM residents WHERE r_id = ?";

        try (PreparedStatement pst = connectDB.prepareStatement(sql)) {

            pst.setInt(1, Integer.parseInt(editResIdtxt.getText()));

            int rowsDeleted = pst.executeUpdate();

            if (rowsDeleted > 0) {
                msg2.setText("Account deleted successfully");
                msg2.setTextFill(Color.GREEN);

                editResIdtxt.setText("");
                editResNametxt.setText("");
                editResConttxt.setText("");
                editResDurtxt.setText("");
                editResSertxt.setText("");
            } 
            else {
                msg2.setText("Invalid ID");
                msg2.setTextFill(Color.RED);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, e.g., display an error message to the user
        }
    }

    public void editResEditBtnOnAction(ActionEvent event){

        //String sql = "INSERT INTO residents (r_name, r_contact, duration, service, room_type, boarding) VALUES (?, ?, ?, ?, ?, ?) WHERE r_id = ?";
        String sql = "UPDATE residents SET r_name = ?, r_contact = ?, duration = ?, service = ?, room_type = ?, boarding = ? WHERE r_id = ?";

        try (PreparedStatement pst = connectDB.prepareStatement(sql)) {

            
            pst.setString(1, editResNametxt.getText());
            pst.setString(2, editResConttxt.getText());
            pst.setString(3, editResDurtxt.getText());
            pst.setString(4, editResSertxt.getText());

            String selectedRadio = "";
            for (Toggle toggle : roomType.getToggles()) {
                if (toggle.isSelected()) {
                    RadioButton radioButton = (RadioButton) toggle;
                    selectedRadio = radioButton.getText();
                }
            }
            pst.setString(5, selectedRadio);

            String selectedRadio2 = "";
            for (Toggle toggle : boarding.getToggles()) {
                if (toggle.isSelected()) {
                    RadioButton radioButton = (RadioButton) toggle;
                    selectedRadio2 = radioButton.getText();
                }
            }
            pst.setString(6, selectedRadio2);

            pst.setInt(7, Integer.parseInt(editResIdtxt.getText())); // Set the ID for WHERE clause

            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated > 0) {
                msg2.setText("Account edit done successfully");
                msg2.setTextFill(Color.GREEN);
            } 
            else {
                msg2.setText("Something went wrong");
                msg2.setTextFill(Color.RED);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception, e.g., display an error message to the user
        }
    }
    
}
