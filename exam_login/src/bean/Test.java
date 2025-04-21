package bean;

import java.io.Serializable;

public class Test implements Serializable {

	private String studentNo;
	private String subjectCd;
	private String schoolCd;
	private int no;
	private int point;
	private String classNum;

	public String getStudentNo(){
		return studentNo;
	}
	public String getsubjectCd(){
		return subjectCd;
	}
	public String getschoolCd(){
		return schoolCd;
	}
	public int getno(){
		return no;
	}
	public int getpoint(){
		return point;
	}
	public String getclassNum(){
		return classNum;
	}

	public void setStudentNo(String studentNo){
		this.studentNo = studentNo;
	}
	public void setsubjectCd(String subjectCd){
		this.subjectCd = subjectCd;
	}
	public void setschoolCd(String schoolCd){
		this.schoolCd = schoolCd;
	}
	public void setno(int no){
		this.no = no;
	}
	public void setpoint(int point){
		this.point = point;
	}
	public void setclassNum(String classNum){
		this.classNum = classNum;
	}

}
