package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		School school = teacher.getSchool();

		String cd = req.getParameter("cd");
		String name = req.getParameter("name");

		Map<String, String> errors = new HashMap<>();

		// cd(科目コード)が重複している場合
		if (cd != null && !cd.trim().isEmpty()) {
			SubjectDao subjectDao = new SubjectDao();
			Subject existingSubject = subjectDao.get(cd, school);
			if (existingSubject != null) {
				errors.put("cd", "科目コードが重複しています");
			}
		}
		if (cd.length() < 3) {
			errors.put("cd", "科目コードは３文字で入力してください");
		}

		// エラーがある場合、元のフォームに戻る
		if (!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			// エラーがあるため、入力フォームに戻る
			req.getRequestDispatcher("subject_create.jsp").forward(req, res);
			return;
		}

		// エラーがない場合は登録処理を実行
		Subject subject = new Subject();
		SubjectDao subjectDao = new SubjectDao();
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(school);
		subjectDao.save(subject);

		// 成功した場合は完了画面へ
		req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
	}
}