package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    	HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");

        String no = "";
        String name = "";
		int entYear = 0;
		String class_num = "";
		boolean isAttend =false;
		Student student = new Student();
		StudentDao studentDao = new StudentDao();
		ClassNumDao classNumDao = new ClassNumDao();

		no = req.getParameter("no");
		student = studentDao.get(no);
		List<String> class_num_set = classNumDao.filter(teacher.getSchool());

		entYear = student. getEntYear();
		name = student. getName();
		class_num = student. getClassNum();
		isAttend = student. isAttend();

		req. setAttribute("ent_year", entYear);
		req. setAttribute("no", no);
		req. setAttribute("name", name);
		req. setAttribute("class_num", class_num);
		req. setAttribute("class_num_set", class_num_set);
		req. setAttribute("is_attend", isAttend);

		// JSPへフォワード
		req. getRequestDispatcher ("student_update.jsp"). forward (req, res);

    }
}