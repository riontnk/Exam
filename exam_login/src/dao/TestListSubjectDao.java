package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.TestListStudent;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao{
	private String baseSql = "select * from test where subject_cd=?";

	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
        List<TestListSubject> list = new ArrayList<>(); // 結果を格納するリストを初期化

        try{
        	while (rSet.next()) {
        		TestListSubject subject = new TestListSubject();
                Subject subjectBean = new Subject();
                SubjectDao subjectDao = new SubjectDao();
                School school = new School();
                SchoolDao schoolDao = new SchoolDao();

                school = schoolDao.get(rSet.getString("school_cd"));
                subjectBean = subjectDao.get(rSet.getString("subject_cd"), school);
                
                subject.setName(rSet.getString("name")); // 科目名をセット
                subject.setSubjectCd(subjectCd);                  // 科目コードをセット
                subject.setSchoolCd(schoolCd);                     // 学校コードをセット
                subject.setNum(rSet.getInt("no"));                 // 数量をセット
                subject.setPoint(rSet.getInt("point"));            // 点数をセット

                // リストに追加
                list.add(subject);
        	}

        }
}