package bean;

import java.io.Serializable;
import java.util.Map;

public class TestListSubject implements Serializable {

	private int entYear;
	private String studentNo;
	private String studentName;
	private String classNum;
	private Map points;


	public int getEntYear(){
		return entYear;
	}
	public String getStudentNo(){
		return studentNo;
	}
	public String getStudentName(){
		return studentName;
	}
	public String getClassNum(){
		return classNum;
	}
	public Map getPoints(){
		return points;
	}

	public void setEntYear(int entYear){
		this.entYear = entYear;
	}
	public void setStudentNo(String studentNo){
		this.studentNo = studentNo;
	}
	public void setStudentName(String studentName){
		this.studentName = studentName;
	}
	public void setClassNum(String classNum){
		this.classNum = classNum;
	}
	public void setPoints(Map points){
		this.points = points;
	}

}
