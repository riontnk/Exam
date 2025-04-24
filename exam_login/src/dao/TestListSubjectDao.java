package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    private String baseSql = "SELECT * FROM test WHERE subject_cd=?";

    // ResultSet から TestListSubject リストを生成
    private List<TestListSubject> postFilter(ResultSet rSet) throws SQLException {
        List<TestListSubject> list = new ArrayList<>(); // 結果を格納するリストを初期化

        while (rSet.next()) {
            TestListSubject testlistsubject = new TestListSubject();
            Subject subject = new Subject();
            SubjectDao subjectDao = new SubjectDao();
            School school = new School();
            SchoolDao schoolDao = new SchoolDao();

            // TestListSubject にデータをセット
            testlistsubject.setStudentNo(rSet.getString("student_no")); // 学生番号
            testlistsubject.setStudentName(rSet.getString("student_name")); // 学生名
            testlistsubject.setClassNum(rSet.getString("class_num")); // クラス番号
            testlistsubject.setEntYear(rSet.getInt("ent_year")); // 入学年度

            // Test の結果を Map としてセット
            Map<String, Integer> points = new HashMap<>();
            points.put("point", rSet.getInt("point")); // 点数を "point" キーに保存
            testlistsubject.setPoints(points);

            // リストに追加
            list.add(testlistsubject);
        }

        return list; // 結果リストを返却
    }
    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school)throws Exception{
    	// 結果を格納するリストを初期化
        List<TestListSubject> testListSubject = new ArrayList<>();
        Connection connection = getConnection(); // データベース接続を取得
        PreparedStatement statement = null;
        ResultSet rSet = null;

        String order = " order by subject_cd asc";

        try {
            // SQL ステートメントを準備
            statement = connection.prepareStatement(baseSql + order);

            statement.setString(1, subject.getCd());
            rSet = statement.executeQuery();

            // 結果セットを `postFilter` メソッドで処理
            testListSubject = postFilter(rSet);
        } catch (Exception e) {
            // 例外をスロー
            throw e;
        } finally {
            // PreparedStatement をクローズ
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            // Connection をクローズ
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        // 結果リストを返却
        return testListSubject;
    }
}
