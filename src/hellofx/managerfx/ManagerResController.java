package hellofx.managerfx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Worker.Worker;
import hellofx.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ManagerResController {

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

        @SuppressWarnings("rawtypes")
    @FXML
    private TableView restable;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableColumn idcolres;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableColumn namecolres;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableColumn durationcol;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableColumn servicecol;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableColumn typecolres;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableColumn boardingcolres;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableColumn contactcolres;
    @FXML
    private Button dispbtnres;

    @SuppressWarnings("unchecked")
    public void dispbtnresOnAction(ActionEvent event) throws SQLException{

        Statement stmt = connectDB.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM residents");
        ObservableList<dummyRes> data = FXCollections.observableArrayList();
        
        // Iterate through all results in the ResultSet
        while (rs.next()) {
            int id = rs.getInt("r_id");
            String name = rs.getString("r_name");
            int duration = rs.getInt("duration");
            String service = rs.getString("service");
            String contact = rs.getString("r_contact");
            String type = rs.getString("room_type");
            String board = rs.getString("boarding");
            
            dummyRes res = new dummyRes();
            Worker w = new Worker();

            w.setId(id);
            
            res.setId(id);
            res.setName(name);
            res.setDuration(duration);
            res.setService(service);
            res.setContactInfo(contact);
            res.setRoomType(type);
            res.setBoard(board);
            
            data.add(res);
        }
        
        restable.setItems(data);
        
        idcolres.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecolres.setCellValueFactory(new PropertyValueFactory<>("name"));
        durationcol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        servicecol.setCellValueFactory(new PropertyValueFactory<>("service"));
        typecolres.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        boardingcolres.setCellValueFactory(new PropertyValueFactory<>("board"));
        contactcolres.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));

    }
    
}
    

