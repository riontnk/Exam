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
    private List<Test> postFilter(ResultSet rSet, School school) throws SQLException {
        List<Test> list = new ArrayList<>();
        while (rSet.next()) {
            Test test = new Test();
            test.setStudentNo(rSet.getString("student_no"));
            test.setSubjectCd(rSet.getString("subject_cd"));
            test.setSchoolCd(school.getCd());
            test.setClassNum(rSet.getString("class_num"));
            test.setNo(rSet.getInt("no"));
            test.setPoint(rSet.getInt("point"));
            list.add(test);
        }
        return list;
    }

    // 条件付きで Test の一覧を取得
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws SQLException {
        List<Test> list = new ArrayList<>();
        StringBuilder condition = new StringBuilder(" AND ent_year=? AND class_num=?");
        if (subject != null) {
            condition.append(" AND subject_cd=?");
        }
        if (num > 0) {
            condition.append(" AND no=?");
        }
        String sql = baseSql + condition + " ORDER BY no ASC";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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
    public Test get(Student student, Subject subject, School school, int no) throws SQLException {
        Test test = null;
        String sql = "SELECT * FROM test WHERE student_no=? AND subject_cd=? AND school_cd=? AND no=?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getNo());
            statement.setString(2, subject.getCd());
            statement.setString(3, school.getCd());
            statement.setInt(4, no);

            try (ResultSet rSet = statement.executeQuery()) {
                if (rSet.next()) {
                    test = new Test();
                    test.setStudentNo(rSet.getString("student_no"));
                    test.setSubjectCd(rSet.getString("subject_cd"));
                    test.setSchoolCd(rSet.getString("school_cd"));
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

    // 単一 Test レコードを保存
    public boolean save(Test test) throws SQLException {
        String sql = "INSERT INTO test (student_no, subject_cd, school_cd, class_num, no, point) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, test.getStudentNo());
            statement.setString(2, test.getSubjectCd());
            statement.setString(3, test.getSchoolCd());
            statement.setString(4, test.getClassNum());
            statement.setInt(5, test.getNo());
            statement.setInt(6, test.getPoint());

            return statement.executeUpdate() == 1;
        } catch (SQLException sqle) {
            throw sqle;
        }
    }
}
