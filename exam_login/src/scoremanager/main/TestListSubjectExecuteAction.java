package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

		public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher) session.getAttribute("user");

			School school = teacher.getSchool();

			String entYearStr = "";
			String classNum = "";
			String subjectCd = "";
			int entYear = 0;

			List<TestListSubject> tls = null;

			SubjectDao sbDao = new SubjectDao();
			TestListSubjectDao tlsDao = new TestListSubjectDao();
	        LocalDate todayDate = LocalDate.now();
	        int year = todayDate.getYear();

	        ClassNumDao cNumDao = new ClassNumDao();
	        SubjectDao subjectDao = new SubjectDao();


	        List<Integer> entYearSet = new ArrayList<>();
	        for (int i = year - 10; i <= year; i++) {
	            entYearSet.add(i);
	        }


	        List<String> classNumSet = cNumDao.filter(teacher.getSchool());



	        List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());
			entYearStr = req.getParameter("f1");
			classNum = req.getParameter("f2");
			subjectCd = req.getParameter("f3");


	        Subject subject = null;
	        subject = sbDao.get(subjectCd, school);


			if (entYearStr != null && classNum != null && subjectCd != null) {
				entYear = Integer.parseInt(entYearStr);
				tls = tlsDao.filter(entYear, classNum, subject, school);

			}
			String subjectName = sbDao.get(subjectCd, school).getName();

			req.setAttribute("f1", entYearStr);
			req.setAttribute("f2", classNum);
			req.setAttribute("f3", subjectCd);
			req.setAttribute("subjectName", subjectName);
			req.setAttribute("tls", tls);

	        req.setAttribute("ent_year_set", entYearSet);
	        req.setAttribute("class_num_set", classNumSet);
	        req.setAttribute("subject_set", subjectSet);

			req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
		}
	}
