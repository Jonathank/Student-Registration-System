package student;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.DatabaseConnection;
import database.DatabaseOps;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
;

public class StudentController implements Initializable{
	
	@FXML
	private TextField txtid,txtfname,txtlname,txtage;
	@FXML
	private Button btnsave,btnupdate,btnexit,btndelete,btnclear;
	@FXML
	private ComboBox<String>combogender;
	@FXML
	private TableView<Student>studenttable;
	@FXML
	private TableColumn<Student,String>CID;
	@FXML
	private TableColumn<Student,String>CFN;
	@FXML
	private TableColumn<Student,String>CLN;
	@FXML
	private TableColumn<Student,String>CG;
	@FXML
	private TableColumn<Student,String>CAGE;
	int myindex;
	Stage stage;
	//creates connection
	Connection conn = DatabaseConnection.getConnection();
	
	ObservableList<Student> students = FXCollections.observableArrayList();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	combogender.setItems(FXCollections.observableArrayList("MALE","FEMALE"));
	
	table();
	}
	
	private static void showalert(AlertType alerttype,Window owner,String title,String message) {
		Alert alert = new Alert(alerttype);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}
	
	
	//save method
	public void save(ActionEvent event) {
		if(txtid.getText().isEmpty()) {
			showalert(AlertType.ERROR,((Stage)txtid.getScene().getWindow()),"ERROR","PLEASE ENTER STUDENT ID NUMBER!");
			return;
		}
		
		if(txtfname.getText().isEmpty()) {
			showalert(AlertType.ERROR,((Stage)txtfname.getScene().getWindow()),"ERROR","PLEASE ENTER STUDENT FIRST NAME!");
			return;
		}
		if(txtlname.getText().isEmpty()) {
			showalert(AlertType.ERROR,((Stage)txtlname.getScene().getWindow()),"ERROR","PLEASE ENTER STUDENT LAST NAME!");
			return;
		}
		if(txtage.getText().isEmpty()) {
			showalert(AlertType.ERROR,((Stage)txtage.getScene().getWindow()),"ERROR","PLEASE ENTER STUDENT AGE!");
			return;
		}
		if(combogender.getValue() == null) {
			showalert(AlertType.ERROR,((Stage)txtid.getScene().getWindow()),"ERROR","PLEASE ENTER STUDENT GENDER!");
			return;
		}
		//cast text value to integer
		int age = Integer.parseInt(txtage.getText().toString());
		
		Student std = new Student(txtid.getText(),txtfname.getText(),txtlname.getText(),combogender.getValue().toString(),age);
		
		//test the id if it exists
		if(DatabaseOps.ifIDexist(txtid.getText())) {
			/**
			 * if id exists it shows alert message of error, duplicate id
			 */
			showalert(AlertType.INFORMATION,((Stage)txtid.getScene().getWindow()),"CHECK","DUPLICATE ID");
		}
		//add object to addStudent method from the database class
		DatabaseOps.addStudent(std);
		table();
		//loadstudentToTable(); //data loads in the table
		
	}//end save 
	
	//exit method
	
	public void exit(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("YOU ARE ABOUT TO EXIT");
		alert.setTitle("EXIT");
		alert.setContentText("DO YOU WANT TO  EXIT BEFORE SAVING?: ");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			stage = (Stage)btnexit.getScene().getWindow();
			stage.close();
		}
	}
	//clear method
	 @FXML
	public void clear(ActionEvent event) {
		txtid.setText("");
		txtfname.setText("");
		txtlname.setText("");
		txtage.setText("");
		combogender.setValue(null);
	}
	
	 @FXML
	    public void Delete(ActionEvent event) throws SQLException  {
		 if(txtid.getText().equals("")) {
			 Alert alert = new Alert(AlertType.ERROR);
	          alert.setTitle("ERROR:");
	          alert.setHeaderText("No selection was made.");
	          alert.setContentText("You have not selected an item to delete. Please try again.");
	          alert.showAndWait();
	          return;
		 }
		 
	      int  selectedIndex  = studenttable.getSelectionModel().getSelectedIndex();
	      String selectedItem = studenttable.getItems().get(selectedIndex).getStdid();
	    DatabaseOps.deleteStudent(selectedItem);
	    table();
	    
	      
	    }
	 //UPDATE BUTTON
	 public void updatebutton() {
		 try {
		 DatabaseOps.update(txtid.getText(), txtfname.getText(), txtlname.getText(), combogender.getValue().toString(), Integer.parseInt(txtage.getText()));
		 table();
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	 }

//loads table content	
	 public void table(){
      try 
      {
    	  studenttable.getItems().clear(); 
         PreparedStatement pstmt = conn.prepareStatement("select std_id,firstname,lastname,gender,age from students");  
          ResultSet rs = pstmt.executeQuery();
     
       while (rs.next())
       {
    	Student st = new Student(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5));
    	   
           students.add(st);
      
               studenttable.setItems(students);
            CID.setCellValueFactory(new PropertyValueFactory<>("stdid"));
       		CFN.setCellValueFactory(new PropertyValueFactory<>("fname"));
       		CLN.setCellValueFactory(new PropertyValueFactory<>("lname"));
       		CG.setCellValueFactory(new PropertyValueFactory<>("gender"));
       		CAGE.setCellValueFactory(new PropertyValueFactory<>("age"));
               
       }

      }
      
      catch (SQLException e) 
      {
          e.printStackTrace();
      }

               studenttable.setRowFactory( tv -> {
            TableRow<Student> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event -> {
               if (event.getClickCount() == 1 && (!myRow.isEmpty()))
               {
                   myindex =  studenttable.getSelectionModel().getSelectedIndex();
        
				txtid.setText(studenttable.getItems().get(myindex).getStdid());
				txtfname.setText(studenttable.getItems().get(myindex).getFname());
				txtlname.setText(studenttable.getItems().get(myindex).getLname());
				combogender.setValue(studenttable.getItems().get(myindex).getGender());
                txtage.setText(String.valueOf(studenttable.getItems().get(myindex).getAge()));  
                          
               }
            });
               return myRow;
                  });
  
     }

}
