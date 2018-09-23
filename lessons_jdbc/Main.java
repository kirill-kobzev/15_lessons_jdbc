package lessons_jdbc;

import lessons_jdbc.dao.StudentDao;
import lessons_jdbc.dao.StudentDaoImpl;
import lessons_jdbc.pojo.Student;

public class Main
{
    public static void main(String[] args) {

        StudentDao studentDao = new StudentDaoImpl();
        Student student = studentDao.getStudentById(1);
        System.out.println(student);

    }
}
