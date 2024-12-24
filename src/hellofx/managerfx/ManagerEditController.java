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
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ManagerEditController {

        DatabaseConnection connectNow = DatabaseConnection.getObject();
    Connection connectDB = connectNow.getConnection();
    
    /****************************************************************** */
    /* ************************* Main Panel *************************** */
    /****************************************************************** */

    /* ************************* Main Logout *************************** */
    @FXML
    private Button logoutbtn;
    public void logoutbtnOnAction(ActionEvent event) throws IOException{
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../login.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    
    /* ************************* Main Cancel *************************** */
    @FXML
    private Button cancelbtn;
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
    private Button searchbtn;
    @FXML
    private TextField idtxt;
    @FXML
    private Label label;
    @FXML
    private TextField nametxtedit;
    @FXML
    private TextField usernametxtedit;
    @FXML
    private TextField passwordtxtedit;
    @FXML
    private TextField contacttxtedit;
    @FXML
    private TextField jobtxtedit;
    @FXML
    private TextField salarytxtedit;
    public void searchbtnOnAction(ActionEvent event) throws IOException{
        label.setText("");
        if((idtxt.getText().isBlank() == false)){

            String getTitle = "SELECT * FROM workers WHERE w_id = '" + idtxt.getText() + "'";
            try (PreparedStatement pst = connectDB.prepareStatement(getTitle)) {
                
                ResultSet rs = pst.executeQuery();

                if(rs.next() == true){

                    nametxtedit.setText(rs.getString("w_name"));
                    usernametxtedit.setText(rs.getString("w_username"));
                    passwordtxtedit.setText(rs.getString("w_password"));
                    contacttxtedit.setText(rs.getString("w_contact_info"));
                    jobtxtedit.setText(rs.getString("job_title"));
                    salarytxtedit.setText(Integer.toString(rs.getInt("salary")));

                }
                else{
                    label.setText("Invalid ID");
                    label.setTextFill(Color.RED);
                }

            } 
            catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }
        }
        else{
            label.setText("Please enter the ID");
            label.setTextFill(Color.RED);
        }
    }

    @FXML
    private Button clearbtnedit;
    public void clearbtneditOnAction(ActionEvent event){
        label.setText("");
        idtxt.setText("");
        nametxtedit.setText("");
        usernametxtedit.setText("");
        passwordtxtedit.setText("");
        contacttxtedit.setText("");
        jobtxtedit.setText("");
        salarytxtedit.setText("");

    }

    @FXML
    private Button deletebtnedit;
    public void deletebtneditOnAction(ActionEvent event){

        String sql = "DELETE FROM workers WHERE w_id = ?";

        try (PreparedStatement pst = connectDB.prepareStatement(sql)) {

            pst.setInt(1, Integer.parseInt(idtxt.getText()));

            int rowsDeleted = pst.executeUpdate();

            if (rowsDeleted > 0) {
                label.setText("Account deleted successfully");
                label.setTextFill(Color.GREEN);

                idtxt.setText("");
                nametxtedit.setText("");
                usernametxtedit.setText("");
                passwordtxtedit.setText("");
                contacttxtedit.setText("");
                jobtxtedit.setText("");
                salarytxtedit.setText("");
            } 
            else {
                label.setText("Invalid ID");
                label.setTextFill(Color.RED);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    @FXML
    private Button editbtnedit;
    public void editbtneditOnAction(ActionEvent event){
        String sql = "UPDATE workers SET w_username = ?, w_password = ?, w_name = ?, w_contact_info = ?, job_title = ?, salary = ? WHERE w_id = ?";

        try (PreparedStatement pst = connectDB.prepareStatement(sql)) {
            pst.setString(1, usernametxtedit.getText());
            pst.setString(2, passwordtxtedit.getText());
            pst.setString(3, nametxtedit.getText());
            pst.setString(4, contacttxtedit.getText());
            pst.setString(5, jobtxtedit.getText());
            pst.setInt(6, Integer.parseInt(salarytxtedit.getText()));
            pst.setInt(7, Integer.parseInt(idtxt.getText())); // Set the ID for WHERE clause

            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated > 0) {
                label.setText("Account edit done successfully");
                label.setTextFill(Color.GREEN);
            } 
            else {
                label.setText("Something went wrong");
                label.setTextFill(Color.RED);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    
}
