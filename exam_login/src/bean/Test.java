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
	public String getSubjectCd(){
		return subjectCd;
	}
	public String getSchoolCd(){
		return schoolCd;
	}
	public int getNo(){
		return no;
	}
	public int getPoint(){
		return point;
	}
	public String getClassNum(){
		return classNum;
	}

	public void setStudentNo(String studentNo){
		this.studentNo = studentNo;
	}
	public void setSubjectCd(String subjectCd){
		this.subjectCd = subjectCd;
	}
	public void setSchoolCd(String schoolCd){
		this.schoolCd = schoolCd;
	}
	public void setNo(int no){
		this.no = no;
	}
	public void setPoint(int point){
		this.point = point;
	}
	public void setClassNum(String classNum){
		this.classNum = classNum;
	}

}
