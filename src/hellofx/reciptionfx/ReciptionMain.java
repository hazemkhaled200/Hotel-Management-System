package hellofx.reciptionfx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Room.Room;
import Room.Builder.DoubleRoom;
import Room.Builder.SingleRoom;
import Room.Builder.TripleRoom;
import Worker.Reciptionist;
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

public class ReciptionMain {

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
    private Label msg5;

    public void getbtnOnAction(ActionEvent event){
        
        if((resIdtxt.getText().isBlank() == false)){
            
            String getTitle = "SELECT * FROM residents WHERE r_id = '" + resIdtxt.getText() + "'";
            Room room = ReturnMyRoom(getTitle);                  
                    
            String sql = "UPDATE rooms SET res_id = ?, is_available = ?, price = ?, boarding = ? WHERE room_type = ? AND is_available = ? LIMIT 1";
            updateMyRoom(sql, room);

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
                    //msg5.setText("Invalid ID");
                    msg5.setTextFill(Color.RED);
                } 
            }
            catch (SQLException e) {
                e.printStackTrace();
                e.getCause();
            }


        }
        else{
            msg5.setText("Please enter the ID");
            msg5.setTextFill(Color.RED);
        }

    }

    // Room Builder and Decorator.
    public Room ReturnMyRoom(String sql){
        try (PreparedStatement pst = connectDB.prepareStatement(sql)) {
                
            ResultSet rs = pst.executeQuery();
            
            if(rs.next() == true){

                String roomType = rs.getString("room_type");
                String boarding = rs.getString("boarding");
                Reciptionist rec = new Reciptionist();

                switch (roomType) {
                    case "Single":
                        rec.setBuilder(new SingleRoom());
                    break;
                    case "Double":
                        rec.setBuilder(new DoubleRoom());
                    break;
                    case "Triple":
                        rec.setBuilder(new TripleRoom());
                    break;
                    default:
                        break;
                }

                switch (boarding) {
                    case "Full Board":
                        rec.decorateRoom(boarding);
                    break;
                    case "Half Board":
                        rec.decorateRoom(boarding);
                    break;
                    case "Bed And Breakfast":
                        rec.decorateRoom(boarding);
                    break;
                    default:
                    break;
                }

                rec.buildRoom();
                Room room = new Room();
                room = rec.roomKey();
                return room;
            }
            else{
                msg5.setText("Invalid ID");
                msg5.setTextFill(Color.RED);
                return null;
            } 
        }
        catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return null;

    }

    // Update The database.
    public void updateMyRoom(String sql, Room room){

        try (PreparedStatement stmt = connectDB.prepareStatement(sql)){
            stmt.setInt(1, Integer.parseInt(resIdtxt.getText()));
            stmt.setString(2, Boolean.toString(room.isAvailable));
            stmt.setDouble(3, room.price);
            stmt.setString(4, room.boardingOption);
            stmt.setString(5, room.roomType);
            stmt.setString(6, "true");
            
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                msg5.setText("Welcome!");
                //msg5.setText(room.roomType);
                msg5.setTextFill(Color.GREEN);
            } 
            else {
                msg5.setText("Sorry, There are no available " + room.roomType + " room.");
                msg5.setTextFill(Color.RED);
            }
            
        } 
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    @FXML
    private Button clearbtn;
    public void clearbtnOnAction(ActionEvent event){
        resIdtxt.setText("");
        roomIdtxt.setText("");
        roomTypetxt.setText("");
        boardingtxt.setText("");
        pricetxt.setText("");
        msg5.setText("");
    }
}
