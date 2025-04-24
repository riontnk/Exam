package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao; // Giả sử có SubjectDao để lấy danh sách môn học
import tool.Action;

public class TestListAction extends Action {



	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

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


        request.setAttribute("ent_year_set", entYearSet);
        request.setAttribute("class_num_set", classNumSet);
        request.setAttribute("subject_set", subjectSet);

        // jsp
        request.getRequestDispatcher("test_list.jsp").forward(request, response);
    }
}