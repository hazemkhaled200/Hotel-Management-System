package hellofx.managerfx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Room.Room;
import hellofx.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ManagerRoomsController {

    DatabaseConnection connectNow = DatabaseConnection.getObject();
    Connection connectDB = connectNow.getConnection();
    
    /* **************************************************************** */
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
    private Button dispbtnroom;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableView roomstable;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableColumn idcolrooms;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableColumn empcol;
    @FXML
    @SuppressWarnings("rawtypes")
    private TableColumn rescol;
    @FXML
    @SuppressWarnings("rawtypes")
    private TableColumn typecol;
    @FXML
    @SuppressWarnings("rawtypes")
    private TableColumn avlblcol;
    @FXML
    @SuppressWarnings("rawtypes")
    private TableColumn pricecol;
    @FXML
    @SuppressWarnings("rawtypes")
    private TableColumn boardingcol;

    @FXML
    private Label l;

    @SuppressWarnings("unchecked")
    public void dispbtnroomOnAction(ActionEvent event) throws SQLException {

        Statement stmt = connectDB.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM rooms"); // Renamed for clarity

        ObservableList<dummyRoom> data = FXCollections.observableArrayList();

        while (rs.next()) {
            int id = rs.getInt("room_id");
            int emp_id = rs.getInt("worker_id");
            int res_id = rs.getInt("res_id");
            String room_type = rs.getString("room_type");
            String is_available = rs.getString("is_available");
            Double price = rs.getDouble("price");
            String boarding = rs.getString("boarding");
            
            dummyRoom room = new dummyRoom();
            
            room.setId(id);
            room.setRoomType(room_type);
            room.setIsAvailable(is_available);
            room.setPrice(price);
            room.setBoardingOptions(boarding);
            room.setEmpId(emp_id);
            room.setResId(res_id);
            data.add(room);
        }
        
        roomstable.setItems(data);
        
        
        Callback<TableColumn<Room, String>, TableCell<Room, String>> nullHandlingCellFactory = new Callback<TableColumn<Room, String>, TableCell<Room, String>>() {
            @Override
            public TableCell<Room, String> call(TableColumn<Room, String> param) {
                return new TableCell<Room, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText("N/A");
                            // Or any other desired default value
                        } else {
                            setText(item);
                        }
                    }
                };
            }
        };
        
        idcolrooms.setCellValueFactory(new PropertyValueFactory<>("id"));
        typecol.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        avlblcol.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));
        pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
        boardingcol.setCellFactory(nullHandlingCellFactory);
        boardingcol.setCellValueFactory(new PropertyValueFactory<>("boardingOptions"));
        empcol.setCellValueFactory(new PropertyValueFactory<>("empId"));
        rescol.setCellValueFactory(new PropertyValueFactory<>("resId"));
        l.setText("room.isAvailable");

    }
    
}
