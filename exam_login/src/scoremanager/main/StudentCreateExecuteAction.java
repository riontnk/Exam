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
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        String no = req.getParameter("no");
        String name = req.getParameter("name");
        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");

        HashMap<String, String> errors = new HashMap<>();
        if (entYearStr.equals("0")) {  // デフォルト選択肢の場合
            errors.put("ent_year", "入学年度を選択してください");
        }


        // バリデーション: 学生番号の重複チェック
        if (no != null && !no.trim().isEmpty()) {
            StudentDao studentDao = new StudentDao();
            Student existingStudent = studentDao.get(no);
            if (existingStudent != null) {
                errors.put("no", "学生番号が重複しています");
            }
        }

        // エラーがある場合、元のフォームに戻る
        if (!errors.isEmpty()) {
            // フォーム表示に必要なデータを準備
            LocalDate todaysDate = LocalDate.now();
            int year = todaysDate.getYear();
            ClassNumDao cNumDao = new ClassNumDao();

            List<Integer> entYearSet = new ArrayList<>();
            for (int i = year - 10; i < year + 10; i++) {
                entYearSet.add(i);
            }

            List<String> list = cNumDao.filter(teacher.getSchool());

            // 入力値を保持するために再度設定
            req.setAttribute("class_num_set", list);
            req.setAttribute("ent_year_set", entYearSet);
            req.setAttribute("no", no);
            req.setAttribute("name", name);
            req.setAttribute("ent_year", entYearStr);
            req.setAttribute("class_num", classNum);
            req.setAttribute("errors", errors);

            // エラーがあるため、入力フォームに戻る
            req.getRequestDispatcher("student_create.jsp").forward(req, res);
            return;
        }

        // エラーがない場合は登録処理を実行
        Student student = new Student();
        StudentDao studentDao = new StudentDao();
        int entYear = Integer.parseInt(entYearStr);

        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(false);
        student.setSchool(school);

        studentDao.save(student);

        // 成功した場合は完了画面へ
        req.getRequestDispatcher("student_create_done.jsp").forward(req, res);
    }
}