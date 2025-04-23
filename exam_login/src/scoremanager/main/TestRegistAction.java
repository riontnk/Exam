package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import tool.Action;

public class TestRegistAction extends Action{

	public void execute (HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		School  school = teacher.getSchool();

		String entYearStr="";
		String classNum="";
		String subjectCd="";
		String noStr="";
		int entYear = 0;
		int no = 0;
		List<Student> students = null;
		List<Subject> subjects = null;
		List<String> classNums = null;
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		StudentDao sDao = new StudentDao();
		ClassNumDao cNumDao = new ClassNumDao();
		SubjectDao sbDao = new SubjectDao();
		classNums = cNumDao.filter(school);

		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectCd = req.getParameter("f3");
		noStr = req.getParameter("f4");
		subjects = sbDao.filter(school);
		if (noStr != null) {
			no = Integer.parseInt(noStr);
		}
		if (entYearStr != null) {
			entYear = Integer.parseInt(entYearStr);
		}
		if (entYearStr != null && classNum != null && subjectCd != null && noStr != null) {
			students = sDao.filter(school, entYear, classNum, false);
			req.setAttribute("students", students);
			req.setAttribute("subject", sbDao.get(subjectCd, school).getName());
			req.setAttribute("no", no);
			req.setAttribute("subjectCd", subjectCd);
		}
		List<Integer> entYearSet = new ArrayList<>();
		for (int i=year - 10; i<year + 1; i++){
			entYearSet.add(i);
		}

		req.setAttribute("f1", entYearStr);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);
		req.setAttribute("f4", noStr);

		req.setAttribute("subjects", subjects);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNums);
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}
