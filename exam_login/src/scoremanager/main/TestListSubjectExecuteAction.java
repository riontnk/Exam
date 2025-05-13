package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			String subjectName = "";
			int entYear = 0;

			List<TestListSubject> tls = null;

			SubjectDao sbDao = new SubjectDao();
			TestListSubjectDao tlsDao = new TestListSubjectDao();
	        LocalDate todayDate = LocalDate.now();
	        int year = todayDate.getYear();

	        ClassNumDao cNumDao = new ClassNumDao();
	        SubjectDao subjectDao = new SubjectDao();

	        Map<String, String> errors = new HashMap<>();

			entYearStr = req.getParameter("f1");
			classNum = req.getParameter("f2");
			subjectCd = req.getParameter("f3");

			// 入学年度、クラス番号、科目のいずれかが未選択の場合
			if (entYearStr.equals("0") || classNum.equals("0") || subjectCd.equals("0")) {
				errors.put("f1", "入学年度とクラス番号と科目を選択してください");
				// リクエストにエラーメッセージをセット
				req.setAttribute("errors", errors);
			} else {
				// 入学年度、クラス番号、科目のすべてが選択されている場合
				Subject subject = sbDao.get(subjectCd, school);
				entYear = Integer.parseInt(entYearStr);
				tls = tlsDao.filter(entYear, classNum, subject, school);
				subjectName = sbDao.get(subjectCd, school).getName();
			}

			//DBからデータ取得3
			List<String> classNumSet = cNumDao.filter(teacher.getSchool()); //クラス情報
			List<Subject> subjectSet = subjectDao.filter(teacher.getSchool()); //科目情報

			//ビジネスロジック4
			// リストを初期化
			List<Integer> entYearSet = new ArrayList<>();
			// 10年前から10年後まで年をリストに追加
			for (int i = year - 10; i < year + 11; i++) {
				entYearSet.add(i);
			}

			req.setAttribute("f1", entYearStr);
			req.setAttribute("f2", classNum);
			req.setAttribute("f3", subjectCd);

			req.setAttribute("subjectName", subjectName);
			req.setAttribute("tlsList", tls);
	        req.setAttribute("entYearSet", entYearSet);
	        req.setAttribute("cNumlist", classNumSet);
	        req.setAttribute("list", subjectSet);

			req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
		}
	}
