package scoremanager.main;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		List<Subject> subjects = null;
		SubjectDao subjectDao = new SubjectDao();
		subjects = subjectDao.filter(teacher.getSchool());

		req.setAttribute("subjects", subjects);

		req.getRequestDispatcher("subject_list.jsp").forward(req, res);

	}
}