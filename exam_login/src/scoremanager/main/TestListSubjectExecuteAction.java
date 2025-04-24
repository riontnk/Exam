package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
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
			int idx = 0;
			List<Student> students = null;
			HashMap<Integer, Integer> points1 = new HashMap<>();
			HashMap<Integer, Integer> points2 = new HashMap<>();
			StudentDao sDao = new StudentDao();
			TestDao tDao = new TestDao();
			SubjectDao sbDao = new SubjectDao();
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
			if (entYearStr != null) {
				entYear = Integer.parseInt(entYearStr);
			}
			if (entYearStr != null && classNum != null && subjectCd != null) {
				students = sDao.filter(school, entYear, classNum, false);
				req.setAttribute("students", students);
				for (Student s : students) {
					Test test1 = null;
					Test test2 = null;
					test1 = tDao.get(s, sbDao.get(subjectCd, school), school, 1);
					test2 = tDao.get(s, sbDao.get(subjectCd, school), school, 2);
					if (test1 != null) {
						points1.put(idx, test1.getPoint());
					}
					if (test2 != null) {
						points2.put(idx, test2.getPoint());
					}
					idx++;
				}
			} else {

			}
			String subjectName = sbDao.get(subjectCd, school).getName();

			req.setAttribute("f1", entYearStr);
			req.setAttribute("f2", classNum);
			req.setAttribute("f3", subjectCd);
			req.setAttribute("subjectName", subjectName);
			req.setAttribute("points1", points1);
			req.setAttribute("points2", points2);
	        req.setAttribute("ent_year_set", entYearSet);
	        req.setAttribute("class_num_set", classNumSet);
	        req.setAttribute("subject_set", subjectSet);

			req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
		}
	}
