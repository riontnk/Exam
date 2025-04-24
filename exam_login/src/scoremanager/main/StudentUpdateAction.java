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
		Teacher teacher = (Teacher) session.getAttribute("user");
		String no = req.getParameter("no");
		StudentDao studentDao = new StudentDao();
		Student student = studentDao.get(no);
		ClassNumDao cNumDao = new ClassNumDao();
		List<String> list = cNumDao.filter(teacher.getSchool());
		req.setAttribute("student", student);
		req.setAttribute("class_num_set", list);
		req.setAttribute("is_attend", student.isAttend());
		System.out.println(student.isAttend());
		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}
}