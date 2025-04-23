package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import dao.SchoolDao;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String cd = req.getParameter("cd");
		String school_cd = req.getParameter("school_cd");
		SubjectDao subjectDao = new SubjectDao();
		SchoolDao schoolDao = new SchoolDao();
		School school = schoolDao.get(school_cd);
		Subject subject = subjectDao.get(cd, school);

		subjectDao.delete(subject);

		req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
	}
}
