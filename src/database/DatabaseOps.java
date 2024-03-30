package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import student.Student;

public class DatabaseOps {

	static Connection conn = DatabaseConnection.getConnection();
	static PreparedStatement pstmt = null;
	static Statement stmt = null;
	
	
	public static boolean ifIDexist(String stdid) {
		try {
			
			String checkstmt = "SELECT COUNT(*) FROM students WHERE std_id = ?";
			pstmt = conn.prepareStatement(checkstmt);
			pstmt.setString(1, stdid);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int count = rs.getInt(1);
				return (count > 0);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//add student
	public static void addStudent(Student std) {
		try {
			String SQL = "INSERT INTO STUDENTS(STD_ID,FIRSTNAME,LASTNAME,GENDER,AGE) VALUES(?,?,?,?,?)";
			pstmt = conn.prepareStatement(SQL);
			
			pstmt.setString(1,std.getStdid());
			pstmt.setString(2, std.getFname());
			pstmt.setString(3, std.getLname());
			pstmt.setString(4,std.getGender());
			pstmt.setInt(5, std.getAge());
			
			pstmt.execute();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("SAVING DATA");
			alert.setHeaderText(null);
			alert.setContentText("DATA SUCCESSFULLY SAVED");
			alert.show();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

public static void deleteStudent(String stdid) {

    try 
    {
    	String QSL ="DELETE FROM STUDENTS WHERE STD_ID = ?";
       pstmt = conn.prepareStatement(QSL);
       
       pstmt.setString(1, stdid);
       
       pstmt.executeUpdate();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Student Registationn");
        alert.setHeaderText("Student Registation");
    	alert.setContentText("Record Deleted!");

    	alert.showAndWait();
              
    } 
    catch (SQLException ex)
    {
        ex.printStackTrace();
    }
}
	
	
	public static void update(String stdid,String fname,String lname,String gender,int age) {
		try {
		String SQL = "UPDATE students SET FIRSTNAME=?,LASTNAME =? ,GENDER =?,AGE =? WHERE STD_ID =?";
		pstmt = conn.prepareStatement(SQL);
		pstmt.setString(1, fname);
		pstmt.setString(2, lname);
		pstmt.setString(3,gender);
		pstmt.setInt(4, age);
		pstmt.setString(5,stdid);
		
		pstmt.executeUpdate();
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("UPDATING DATA");
		alert.setHeaderText(null);
		alert.setContentText("DATA SUCCESSFULLY UPDATED");
		alert.show();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
