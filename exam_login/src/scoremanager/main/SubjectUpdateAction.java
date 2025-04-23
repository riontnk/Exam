package scoremanager.main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SchoolDao;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String cd = req.getParameter("cd");
		String school_cd = req.getParameter("school_cd");
		SubjectDao subjectDao = new SubjectDao();
		SchoolDao schoolDao = new SchoolDao();
		Subject subject = subjectDao.get(cd, schoolDao.get(school_cd));
		req.setAttribute("subject", subject);

		req.getRequestDispatcher("subject_update.jsp").forward(req, res);

	}
}