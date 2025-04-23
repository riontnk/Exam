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

public class SubjectUpdateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		School school = teacher.getSchool();

		String cd = req.getParameter("cd");
		String name = req.getParameter("name");
		SubjectDao subjectDao = new SubjectDao();

		Map<String, String> errors = new HashMap<>();

		if (subjectDao.get(cd, school) == null) {
			errors.put("cd", "科目が存在していません");
		}

		// エラーがある場合、元のフォームに戻る
		if (!errors.isEmpty()) {
			Subject subject = new Subject();
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(school);
			req.setAttribute("errors", errors);
			req.setAttribute("subject", subject);

			req.getRequestDispatcher("subject_update.jsp").forward(req, res);
			return;
		}

		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(school);
		subjectDao.save(subject);

		// 成功した場合は完了画面へ
		req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
	}

}
