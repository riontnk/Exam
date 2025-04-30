package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    private String baseSql = "SELECT * FROM test WHERE subject_cd=?";

    // ResultSet から TestListSubject リストを生成
    private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
        List<TestListSubject> list = new ArrayList<>(); // 結果を格納するリストを初期化
        StudentDao sDao = new StudentDao();

        while (rSet.next()) {
            String studentNo = rSet.getString("student_no");
            boolean exists = false;

            for (TestListSubject tls : list) {
                if (tls.getStudentNo().equals(studentNo)) { // 学生番号が一致する場合
                    tls.putPoint(Integer.parseInt(rSet.getString("no")), rSet.getInt("point")); // putPoint を使用
                    exists = true;
                    break;
                }
            }

            if (!exists) { // 初めての学生番号の場合
                TestListSubject testlistsubject = new TestListSubject();
                testlistsubject.setStudentNo(studentNo); // 学生番号
                testlistsubject.setStudentName(sDao.get(studentNo).getName()); // 学生名
                testlistsubject.setClassNum(rSet.getString("class_num")); // クラス番号
                testlistsubject.setEntYear(sDao.get(studentNo).getEntYear()); // 入学年度
                testlistsubject.putPoint(Integer.parseInt(rSet.getString("no")), rSet.getInt("point")); // 初回の得点を追加
                list.add(testlistsubject); // リストに追加
            }
        }

        return list; // 結果リストを返却
    }

    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
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