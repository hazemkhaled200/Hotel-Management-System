package hellofx.managerfx;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import hellofx.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class IncomeController {

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
    private Button generatebtn;
    @FXML
    private TextField Incometxt;
    @FXML
    private ToggleGroup report;
    @FXML 
    private RadioButton wrb;
    @FXML 
    private RadioButton mrb;
    @FXML 
    private RadioButton arb;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableColumn datecol;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableColumn incomecol;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableView table;

    @SuppressWarnings("unchecked")
    public void generatebtnOnAction(ActionEvent event) throws SQLException{

        String selectedRadio = "";
        String sql = "";
        double overallPrice = 0;

        for (Toggle toggle : report.getToggles()) {
            if (toggle.isSelected()) {
                RadioButton radioButton = (RadioButton) toggle;
                selectedRadio = radioButton.getText();
            }
        }

        switch (selectedRadio) {
            case "Weekly Report":
                sql = "SELECT * FROM revenue WHERE event_date >= CURDATE() - INTERVAL 7 day";
                break;
            case "Monthly Report":
                sql = "SELECT * FROM revenue WHERE event_date >= CURDATE() - INTERVAL 30 day";
                break;
            case "Annual Report":
                sql = "SELECT * FROM revenue WHERE event_date >= CURDATE() - INTERVAL 365 day";
                break;
            default:
                break;
        }

        Statement stmt = connectDB.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        ObservableList<dummyIncome> data = FXCollections.observableArrayList();
        
        while (rs.next()) {
            String date = rs.getString("event_date");
            double price = rs.getDouble("income");
            
            dummyIncome inc = new dummyIncome();
            
            inc.setDate(date);
            inc.setPrice(price);

            overallPrice += inc.price;
            
            data.add(inc);
        }
        
        table.setItems(data);
        
        datecol.setCellValueFactory(new PropertyValueFactory<>("date"));
        incomecol.setCellValueFactory(new PropertyValueFactory<>("price"));

        Incometxt.setText(Double.toString(overallPrice));

    }

    
}
