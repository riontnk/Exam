package bean;

import java.io.Serializable;

public class TestListSubject implements Serializable {

	private int entYear;
	private String studentNo;
	private String studentName;
	private String classNum;
	private Map points;


	public int getentYear(){
		return entYear;
	}
	public String getstudentNo(){
		return studentNo;
	}
	public String getstudentName(){
		return studentName;
	}
	public String getclassNum(){
		return classNum;
	}
	public Map getpoints(){
		return points;
	}

	public void setentYear(int entYear){
		this.entYear = entYear;
	}
	public void setstudentNo(String studentNo){
		this.studentNo = studentNo;
	}
	public void setstudentName(String studentName){
		this.studentName = studentName;
	}
	public void setclassNum(String classNum){
		this.classNum = classNum;
	}
	public void setpoints(Map points){
		this.points = points;
	}

}
