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
import bean.Test;

public class TestDao extends Dao {

	private String baseSql = "SELECT * FROM test WHERE school_cd=?";

	// ResultSet から Test リストを生成
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
		List<Test> list = new ArrayList<>();
		while (rSet.next()) {
			Test test = new Test();
			Student student = new Student();
			Subject subject = new Subject();
			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();
			student = studentDao.get(rSet.getString("student_no"));
			subject = subjectDao.get(rSet.getString("subject_cd"), school);
			test.setStudent(student);
			test.setClassNum(rSet.getString("class_num"));
			test.setSubject(subject);
			test.setSchool(school);
			test.setNo(rSet.getInt("no"));
			test.setPoint(rSet.getInt("point"));
			list.add(test);
		}
		return list;
	}

	// 条件付きで Test の一覧を取得
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
		List<Test> list = new ArrayList<>();
		StringBuilder condition = new StringBuilder(" AND ent_year=? AND class_num=?");
		if (subject != null) {
			condition.append(" AND subject_cd=?");
		}
		if (num > 0) {
			condition.append(" AND no=?");
		}
		String sql = baseSql + condition + " ORDER BY no ASC";

		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			int idx = 1;
			statement.setString(idx++, school.getCd());
			statement.setInt(idx++, entYear);
			statement.setString(idx++, classNum);
			if (subject != null) {
				statement.setString(idx++, subject.getCd());
			}
			if (num > 0) {
				statement.setInt(idx++, num);
			}
			try (ResultSet rSet = statement.executeQuery()) {
				list = postFilter(rSet, school);
			}
		} catch (SQLException sqle) {
			throw sqle;
		}
		return list;
	}

	// 単一の Test レコードを取得
	public Test get(Student student, Subject subject, School school, int no) throws Exception {
		Test test = null;
		String sql = "SELECT * FROM test WHERE student_no=? AND subject_cd=? AND school_cd=? AND no=?";

		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);

			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();
			SchoolDao schoolDao = new SchoolDao();

			try (ResultSet rSet = statement.executeQuery()) {
				if (rSet.next()) {
					test = new Test();
					test.setStudent(studentDao.get(rSet.getString("student_no")));
					test.setSubject(subjectDao.get(rSet.getString("subject_cd"), school));
					test.setSchool(schoolDao.get(rSet.getString("school_cd")));
					test.setClassNum(rSet.getString("class_num"));
					test.setNo(rSet.getInt("no"));
					test.setPoint(rSet.getInt("point"));
				}
			}
		} catch (SQLException sqle) {
			throw sqle;
		}
		return test;
	}

	private boolean save(Test test, Connection connection) throws Exception {
		PreparedStatement statement = null;
		int count = 0;
		if (connection == null) {
			System.out.println("print");
		}

		try {
			Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());
			if (old == null) {
				statement = connection.prepareStatement("insert into test values(?, ?, ?, ?, ?, ?)");
				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());
			} else {
				statement = connection.prepareStatement(
						"UPDATE test SET point=? WHERE student_no=? AND subject_cd=? AND school_cd=? AND no=?");
				statement.setInt(1, test.getPoint());
				statement.setString(2, test.getStudent().getNo());
				statement.setString(3, test.getSubject().getCd());
				statement.setString(4, test.getSchool().getCd());
				statement.setInt(5, test.getNo());

			}
			count = statement.executeUpdate();
		} catch (Exception e) {
			// 例外が発生した場合は再スロー
			throw e;
		} finally {
			// PreparedStatementをクローズ
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}

	}

	// 単一 Test レコードを保存
	public boolean save(List<Test> list) throws Exception {

		try (Connection connection = getConnection();) {
			for (Test t : list) {
				save(t, connection);
			}
			// Connectionをクローズ
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			return true;
		} catch (SQLException sqle) {
			throw sqle;
		}
	}
}
