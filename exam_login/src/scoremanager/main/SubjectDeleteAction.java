package scoremanager.main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import dao.SchoolDao;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String cd = req.getParameter("cd");
		String school_cd = req.getParameter("school_cd");
		SubjectDao subjectDao = new SubjectDao();
		SchoolDao schoolDao = new SchoolDao();
		School school = schoolDao.get(school_cd);
		Subject subject = subjectDao.get(cd, school);

		req.setAttribute("subject", subject);
		req.setAttribute("school_cd", school_cd);


		req.getRequestDispatcher("subject_delete.jsp").forward(req, res);

	}
}