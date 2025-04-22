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

public class TestRegistExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		School school = teacher.getSchool();
		HashMap<String, String> errors = new HashMap<>();
		StudentDao studentDao = new StudentDao();
		SubjectDao subjectDao = new SubjectDao();
		TestDao testDao = new TestDao();
		List<Test> tests = new ArrayList<>();
		int i = 0;
		while (true) {
			Test test = new Test();
			if (req.getParameter("students[" + i + "].entYear") == null) {
				break;
			}
			System.out.println(req.getParameter("students[" + i + "].no"));
			System.out.println(req.getParameter("tests[" + i + "].subjectCd"));
			System.out.println(req.getParameter("tests[" + i + "].no"));
			Student student = studentDao.get(req.getParameter("students[" + i + "].no"));
			String classNum = req.getParameter("students[" + i + "].classNum");
			Subject subject = subjectDao.get(req.getParameter("tests[" + i + "].subjectCd"), school);
			System.out.println(req.getParameter("tests[" + i + "].no"));
			int no = Integer.parseInt(req.getParameter("tests[" + i + "].no"));
			String pointStr = req.getParameter("tests[" + i + "].point");
			test.setStudent(student);
			test.setClassNum(classNum);
			test.setSubject(subject);
			test.setSchool(school);
			test.setNo(no);
			if (pointStr != null) {
				int point = Integer.parseInt(pointStr);
				test.setPoint(point);
				tests.add(test);
			}
			i++;
		}
		testDao.save(tests);
		// エラーがある場合、元のフォームに戻る
		if (!errors.isEmpty()) {
			String entYearStr = "";
			String classNum = "";
			String subjectCd = "";
			String noStr = "";
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
			}
			List<Integer> entYearSet = new ArrayList<>();
			for (i = year - 10; i < year + 1; i++) {
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
			return;
		}

		// エラーがない場合は登録処理を実行
		Student student = new Student();

		// student.setNo(no);
		// student.setName(name);
		// student.setEntYear(entYear);
		// student.setClassNum(classNum);
		// student.setAttend(false);
		// student.setSchool(school);
		//
		// studentDao.save(student);

		// 成功した場合は完了画面へ
		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
	}
}
