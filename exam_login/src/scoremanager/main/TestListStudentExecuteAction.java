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
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

		public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher) session.getAttribute("user");

			School school = teacher.getSchool();

			String studentNo = "";
			int entYear = 0;
			List<TestListStudent> tls = null;
			Student student = null;
			TestListStudentDao tlsDao = new TestListStudentDao();
			SubjectDao sbDao = new SubjectDao();
	        LocalDate todayDate = LocalDate.now();
	        int year = todayDate.getYear();
	        StudentDao sDao = new StudentDao();
	        ClassNumDao cNumDao = new ClassNumDao();
	        SubjectDao subjectDao = new SubjectDao();
			studentNo = req.getParameter("f4");

	        student = sDao.get(studentNo);
	        if (student != null) {
		        tls = tlsDao.filter(student);
	        }

	        List<Integer> entYearSet = new ArrayList<>();
	        for (int i = year - 10; i <= year; i++) {
	            entYearSet.add(i);
	        }
	        List<String> classNumSet = cNumDao.filter(teacher.getSchool());


	        List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());

			req.setAttribute("f4", studentNo);
	        req.setAttribute("ent_year_set", entYearSet);
	        req.setAttribute("class_num_set", classNumSet);
	        req.setAttribute("tls", tls);
	        req.setAttribute("subject_set", subjectSet);

			req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
		}
	}