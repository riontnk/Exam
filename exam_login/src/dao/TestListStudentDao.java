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

public class TestListStudentDao extends Dao{
	private String baseSql = "select * from test where student_no=?";

    private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
        List<TestListStudent> list = new ArrayList<>(); // 結果を格納するリストを初期化

        try {
            while (rSet.next()) {
                // ResultSet の 1 行を TestListStudent オブジェクトにマッピング
                TestListStudent student = new TestListStudent();
                Subject subject = new Subject();
                SubjectDao subjectDao = new SubjectDao();
                School school = new School();
                SchoolDao schoolDao = new SchoolDao();
                school = schoolDao.get(rSet.getString("school_cd"));
                subject = subjectDao.get(rSet.getString("subject_cd"), school);
                student.setSubjectName(subject.getName()); // 科目名をセット
                student.setSubjectCd(rSet.getString("subject_cd"));     // 科目コードをセット
                student.setNum(rSet.getInt("no"));                   // 数量をセット
                student.setPoint(rSet.getInt("point"));               // 点数をセット

                // リストに追加
                list.add(student);
            }
        } catch (SQLException e) {
            // SQL 実行時の例外をキャッチしてログ出力
            e.printStackTrace();
            throw e; // 再スローして呼び出し元に通知
        }

        return list; // 最終的なリストを返却
    }

	public List<TestListStudent> filter(Student student) throws Exception {
		List<TestListStudent> testListStudent = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;
		String order = " order by subject_cd asc";
		try {
			statement = connection.prepareStatement(baseSql + order);
			statement.setString(1, student.getNo());
			rSet = statement.executeQuery();
			testListStudent = postFilter(rSet);
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return testListStudent;
	}
}
