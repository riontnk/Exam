package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

// 遠藤優斗
// SUBJECTのデーターを操作するクラス

public class SubjectDao extends Dao {

	// 科目コードとスクールデータから対象の科目情報を取得し、Subjectオブジェクトとして返す関数
	public Subject get(String cd, School school) throws Exception {
		Subject subject = null; // 結果を格納するSubjectオブジェクトを初期化

		// データベース接続を取得
		Connection connection = getConnection();
		// SQL文を準備
		PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM subject WHERE SCHOOL_CD = ? AND CD = ?");
		// プレースホルダに値をセット
		statement.setString(1, school.getCd());
		statement.setString(2, cd);

		// クエリを実行して結果を取得
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			// 結果が存在する場合、Subjectオブジェクトを生成しデータをセット
			subject = new Subject();
			subject.setCd(resultSet.getString("CD"));
			subject.setName(resultSet.getString("NAME"));
			subject.setSchool(school);
		}

		// リソースをクローズ
		statement.close();
		connection.close();

		// 結果を返却
		return subject;
	}

	// 指定されたスクールに関連するすべての科目を取得する関数
	public List<Subject> filter(School school) throws Exception {
		List<Subject> list = new ArrayList<>(); // 結果を格納するリストを初期化

		// データベース接続を取得
		Connection connection = getConnection();
		// SQL文を準備
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM subject WHERE SCHOOL_CD = ?");
		// プレースホルダに値をセット
		statement.setString(1, school.getCd());
		// クエリを実行して結果を取得
		ResultSet resultSet = statement.executeQuery();

		// 結果セットをループしてデータをリストに追加
		while (resultSet.next()) {
			Subject subject = new Subject(); // 新しいSubjectオブジェクトを生成
			subject.setCd(resultSet.getString("CD"));
			subject.setName(resultSet.getString("NAME"));
			subject.setSchool(school);
			list.add(subject);
		}

		// リソースをクローズ
		statement.close();
		connection.close();

		// 結果リストを返却
		return list;
	}

	// 科目をデータベースに保存する関数
	// 既存の科目の場合はデータを更新し、存在しない場合は新規作成する
	public boolean save(Subject subject) throws Exception {
	    // データベース接続を取得
	    Connection connection = getConnection();
	    PreparedStatement statement = null; // SQL文を実行するためのPreparedStatement
	    int count = 0; // 実行結果（影響を受けた行数）を格納する変数
	    School school = subject.getSchool(); // 関連するスクール情報を取得

	    try {
	        // 対象の科目が既存かどうかを確認
	        Subject old = get(subject.getCd(), school);
	        if (old == null) {
	            // 科目が存在しない場合、新規作成用のINSERT文を準備
	            statement = connection.prepareStatement(
	                "INSERT INTO subject(SCHOOL_CD, CD, NAME) VALUES(?, ?, ?)");
	            statement.setString(1, school.getCd());
	            statement.setString(2, subject.getCd());
	            statement.setString(3, subject.getName());
	        } else {
	            // 科目が存在する場合、既存データの更新用UPDATE文を準備
	            statement = connection.prepareStatement(
	                "UPDATE subject SET NAME=? WHERE CD=? AND SCHOOL_CD=?");
	            statement.setString(1, subject.getName());
	            statement.setString(2, subject.getCd());
	            statement.setString(3, school.getCd());
	        }

	        // クエリを実行し、影響を受けた行数を取得
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
	        // Connectionをクローズ
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }

	    // 実行結果を基に成功/失敗を返却
	    return count > 0;
	}

	// 指定された科目をデータベースから削除する関数
	public boolean delete(Subject subject) throws Exception {
	    // データベース接続を取得
	    Connection connection = getConnection();
	    PreparedStatement statement = null; // SQL文を実行するためのPreparedStatement
	    int count = 0; // 実行結果（影響を受けた行数）を格納する変数

	    try {
	        // 削除対象の科目コードとスクールコードを取得
	        String cd = subject.getCd();
	        String school_cd = subject.getSchool().getCd();
	        // 削除用SQL文を準備
	        statement = connection.prepareStatement("DELETE FROM subject WHERE CD=? AND SCHOOL_CD=?");
	        statement.setString(1, cd);
	        statement.setString(2, school_cd);
	        // クエリを実行し、影響を受けた行数を取得
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
	        // Connectionをクローズ
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }

	    // 実行結果を基に成功/失敗を返却
	    return count > 0;
	}
}