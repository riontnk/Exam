package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao; // Giả sử có SubjectDao để lấy danh sách môn học
import tool.Action;

public class TestListAction extends Action {



	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String entYearStr = request.getParameter("f1");
        String classNum = request.getParameter("f2");
        String subject = request.getParameter("f3");
        String studentNo = request.getParameter("no");

        int entYear = 0;
        List<Student> students = null;
        LocalDate todayDate = LocalDate.now();
        int year = todayDate.getYear();
        Map<String, String> errors = new HashMap<>();

        StudentDao sDao = new StudentDao();
        ClassNumDao cNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();


        //
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                errors.put("f1", "入学年度は数値で入力してください");
            }
        }


        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }


        List<String> classNumSet = cNumDao.filter(teacher.getSchool());


        List<String> subjectSet = subjectDao.getSubjects(teacher.getSchool());

        if (studentNo != null && !studentNo.isEmpty()) {

            students = sDao.filterByNo(teacher.getSchool(), studentNo);
            if (students.isEmpty()) {
                errors.put("no", "指定された学生番号が見つかりません");
            }
        } else {

            if (entYear != 0 && classNum != null && !classNum.equals("0") && subject != null && !subject.equals("0")) {

                students = sDao.filter(teacher.getSchool(), entYear, classNum, subject);
            } else if (entYear != 0 && classNum != null && !classNum.equals("0")) {

                students = sDao.filter(teacher.getSchool(), entYear, classNum, false);
            } else if (entYear != 0) {

                students = sDao.filter(teacher.getSchool(), entYear, false);
            } else if (classNum != null && !classNum.equals("0")) {

                errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
                students = sDao.filter(teacher.getSchool(), false);
            } else {

                students = sDao.filter(teacher.getSchool(), false);
            }
        }


        request.setAttribute("f1", entYear);
        request.setAttribute("f2", classNum);
        request.setAttribute("selected_subject", subject);
        request.setAttribute("no", studentNo);
        request.setAttribute("students", students);
        request.setAttribute("ent_year_set", entYearSet);
        request.setAttribute("class_num_set", classNumSet);
        request.setAttribute("subject_set", subjectSet);


        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
        }

        // jsp
        request.getRequestDispatcher("test_list.jsp").forward(request, response);
    }
}