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
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ReciptionCheckoutController {

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
    private TextField resIdtxt;
    @FXML
    private TextField roomIdtxt;
    @FXML
    private TextField roomTypetxt;
    @FXML
    private TextField boardingtxt;
    @FXML
    private TextField pricetxt;
    @FXML
    private Button getbtn;
    @FXML
    private Button checkoutbtn;
    @FXML
    private Label checkoutmsg;
    @FXML
    private Button clearbtn;

    public void getbtnOnAction(ActionEvent event){
        checkoutmsg.setText("");
         if((resIdtxt.getText().isBlank() == false)){
            String setFields = "SELECT * FROM rooms WHERE res_id = '" + resIdtxt.getText() + "'";

            try (PreparedStatement pst = connectDB.prepareStatement(setFields)) {
                ResultSet rs = pst.executeQuery();
                if(rs.next() == true){
                    
                    roomIdtxt.setText(rs.getString("room_id"));
                    roomTypetxt.setText(rs.getString("room_type"));
                    boardingtxt.setText(rs.getString("boarding"));
                    pricetxt.setText(Double.toString(rs.getDouble("price")));
    
                }
                else{
                    checkoutmsg.setText("Invalid ID");
                    checkoutmsg.setTextFill(Color.RED);
                    clear();
                } 
            }
            catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }
        }
        else{
            checkoutmsg.setText("Please enter the ID");
            checkoutmsg.setTextFill(Color.RED);
            clear();
        }
    }

    
    public void clearbtnOnAction(ActionEvent event){
        clear();
        checkoutmsg.setText("");
    }

    public void checkoutbtnOnAction(ActionEvent event){
        checkoutmsg.setText("");
        if((resIdtxt.getText().isBlank() == false)){
            String setFields = "SELECT * FROM rooms WHERE res_id = '" + resIdtxt.getText() + "'";

            try (PreparedStatement pst = connectDB.prepareStatement(setFields)) {
                ResultSet rs = pst.executeQuery();
                if(rs.next() == true){
                    roomIdtxt.setText(rs.getString("room_id"));
                    roomTypetxt.setText(rs.getString("room_type"));
                    boardingtxt.setText(rs.getString("boarding"));
                    pricetxt.setText(Double.toString(rs.getDouble("price")));

                    String sql = "UPDATE rooms SET res_id = ?, is_available = ?, price = ?, boarding = ? WHERE res_id = ? LIMIT 1";
                    updateMyRoom(sql);
                    manageIncome();
                    clear();
                }
                else{
                    checkoutmsg.setText("Invalid ID");
                    checkoutmsg.setTextFill(Color.RED);
                    clear();
                } 
            }
            catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }
        }
        else{
            checkoutmsg.setText("Please enter the ID");
            checkoutmsg.setTextFill(Color.RED);
            clear();
        }
    }
    
    // Update The database.
    public void updateMyRoom(String sql){

        try (PreparedStatement stmt = connectDB.prepareStatement(sql)){
            stmt.setString(1, null);
            stmt.setString(2, "true");
            stmt.setDouble(3, 0);
            stmt.setString(4, null);
            stmt.setString(5, resIdtxt.getText());
            
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                checkoutmsg.setText("Room updated!");
                checkoutmsg.setTextFill(Color.GREEN);
            } 
            else {
                checkoutmsg.setText("There are no resident with this ID.");
                checkoutmsg.setTextFill(Color.RED);
            }
            
        } 
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    // update the revenue
    public void manageIncome(){

        String sql = "UPDATE revenue SET income = ? WHERE event_date = CURDATE()";
        double income = getPrice();
        checkoutmsg.setText(Double.toString(income));//=====================

        try (PreparedStatement stmt = connectDB.prepareStatement(sql)){
            stmt.setDouble(1, (Double.parseDouble(pricetxt.getText()) + income));
            
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                checkoutmsg.setText("See you soon :)");
                checkoutmsg.setTextFill(Color.GREEN);
            } 
            else {
                checkoutmsg.setText("Checkout done, error in updating income.");
                checkoutmsg.setTextFill(Color.RED);
            }
            
        } 
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void insertMyIncome(){

        String sql = "INSERT INTO revenue (event_date, income) VALUES (CURDATE(), 0)";
        try (PreparedStatement stmt = connectDB.prepareStatement(sql)){
            if(stmt.executeUpdate() != 1){
                checkoutmsg.setText("Checkout done, Error in inserting dates.");
                checkoutmsg.setTextFill(Color.RED);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public double getPrice(){

        String query = "SELECT income FROM revenue WHERE event_date = CURDATE()";
        double price = 0;
        try (PreparedStatement pst = connectDB.prepareStatement(query)) {
            ResultSet rs = pst.executeQuery();
            if(rs.next() == true){
                price = rs.getDouble("income");
            }
            else{
                insertMyIncome();
            } 
        }
        catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return price;
    }
    
    public void clear(){
        resIdtxt.setText("");
        roomIdtxt.setText("");
        roomTypetxt.setText("");
        boardingtxt.setText("");
        pricetxt.setText("");
    }
}
