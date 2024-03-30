package student;

public class Student {
private  String stdid;
private  String Fname;
private  String lname;
private  int age;
private  String gender;

public Student(String stdid,String fname,String lname,String gender,int age) {
	this.setStdid(stdid);
	this.setFname(fname);
	this.setLname(lname);
	this.setGender(gender);
	this.setAge(age);
}




/**
 * @return the fname
 */
public String getFname() {
	return Fname;
}


/**
 * @param fname the fname to set
 */
public void setFname(String fname) {
	Fname = fname;
}


/**
 * @return the lname
 */
public String getLname() {
	return lname;
}


/**
 * @param lname the lname to set
 */
public void setLname(String lname) {
	this.lname = lname;
}


/**
 * @return the age
 */
public int getAge() {
	return age;
}


/**
 * @param age the age to set
 */
public void setAge(int age) {
	this.age = age;
}


/**
 * @return the gender
 */
public String getGender() {
	return gender;
}


/**
 * @param gender the gender to set
 */
public void setGender(String gender) {
	this.gender = gender;
}




/**
 * @return the stdid
 */
public String getStdid() {
	return stdid;
}




/**
 * @param stdid the stdid to set
 */
public void setStdid(String stdid) {
	this.stdid = stdid;
}
}
