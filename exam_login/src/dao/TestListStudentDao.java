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

public class TestListStudentDao extends Dao {
    // 基本となる SQL クエリ（学生番号を条件にデータを取得）
    private String baseSql = "select * from test where student_no=?";

    /**
     * ResultSet から `TestListStudent` オブジェクトのリストを生成するメソッド
     *
     * @param rSet ResultSet データベースクエリの結果セット
     * @return List<TestListStudent> `TestListStudent` オブジェクトのリスト
     * @throws Exception データベース操作中の例外
     */
    private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
        // 結果を格納するリストを初期化
        List<TestListStudent> list = new ArrayList<>();

        try {
            // 結果セットの各行を反復処理
            while (rSet.next()) {
                // 1行分のデータを `TestListStudent` オブジェクトにマッピング
                TestListStudent student = new TestListStudent();

                // 科目情報と学校情報を取得するためのオブジェクト
                Subject subject;
                SubjectDao subjectDao = new SubjectDao();
                School school;
                SchoolDao schoolDao = new SchoolDao();

                // 学校情報を取得
                school = schoolDao.get(rSet.getString("school_cd"));

                // 科目情報を取得（学校情報を引数として渡す）
                subject = subjectDao.get(rSet.getString("subject_cd"), school);

                // `TestListStudent` オブジェクトにデータをセット
                student.setSubjectName(subject.getName()); // 科目名をセット
                student.setSubjectCd(rSet.getString("subject_cd")); // 科目コードをセット
                student.setNum(rSet.getInt("no")); // 数量をセット
                student.setPoint(rSet.getInt("point")); // 点数をセット

                // リストに追加
                list.add(student);
            }
        } catch (SQLException e) {
            // SQL 実行時の例外をキャッチしてログ出力
            e.printStackTrace();
            throw e; // 再スローして呼び出し元に通知
        }

        // 最終的なリストを返却
        return list;
    }

    /**
     * `Student` オブジェクトを基に `TestListStudent` のリストを取得するメソッド
     *
     * @param student 条件に使用する `Student` オブジェクト
     * @return List<TestListStudent> 条件に一致する `TestListStudent` のリスト
     * @throws Exception データベース操作中の例外
     */
    public List<TestListStudent> filter(Student student) throws Exception {
        // 結果を格納するリストを初期化
        List<TestListStudent> testListStudent = new ArrayList<>();
        Connection connection = getConnection(); // データベース接続を取得
        PreparedStatement statement = null;
        ResultSet rSet = null;

        // データを並び替えるための SQL の追加条件
        String order = " order by subject_cd asc";

        try {
            // SQL ステートメントを準備
            statement = connection.prepareStatement(baseSql + order);

            // プレースホルダーに学生番号をセット
            statement.setString(1, student.getNo());

            // クエリを実行
            rSet = statement.executeQuery();

            // 結果セットを `postFilter` メソッドで処理
            testListStudent = postFilter(rSet);
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
        return testListStudent;
    }
}