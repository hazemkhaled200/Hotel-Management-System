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

public class ManagerEmpsController {

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
    private Button dispbtn;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableView table;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableColumn idcol;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableColumn namecol;
    @FXML
    @SuppressWarnings("rawtypes")
    private TableColumn usernamecol;
    @FXML
    @SuppressWarnings("rawtypes")
    private TableColumn passwordcol;
    @FXML
    @SuppressWarnings("rawtypes")
    private TableColumn contactcol;
    @FXML
    @SuppressWarnings("rawtypes")
    private TableColumn salarycol;

    @SuppressWarnings("unchecked")
    public void dispbtnOnAction(ActionEvent event) throws SQLException{

        Statement stmt = connectDB.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM workers");

        ObservableList<Worker> data = FXCollections.observableArrayList();

        // Iterate through all results in the ResultSet
        while (rs.next()) {
            int iid = rs.getInt("w_id");
            String name = rs.getString("w_name");
            String username = rs.getString("w_username");
            String title = rs.getString("job_title");
            String contact = rs.getString("w_contact_info");
            double s = rs.getDouble("salary");
            
            Worker workerData = new Worker();
            
            workerData.setId(iid);
            workerData.setName(name);
            workerData.setUsername(username);
            workerData.setJobTitle(title);
            workerData.setContactInfo(contact);
            workerData.setSalary(s);
            
            data.add(workerData);
        }
        
        table.setItems(data);
        
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernamecol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordcol.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        contactcol.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        salarycol.setCellValueFactory(new PropertyValueFactory<>("salary"));

    }
    
}
