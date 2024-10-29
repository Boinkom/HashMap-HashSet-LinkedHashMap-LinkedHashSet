import java.util.List;
import java.util.Map;

//        • {x} добавить сдачу студента (в зачет идет только последняя сдача, хранить все сдачи студента по одному и тому же предмету не нужно)
//
//        • {x}получить сдачу студента по имени, фамилии и предмету
//
//        •{x} вывод средней оценки по предмету вывод тех студентов, кто сдавал более одного раза
//
//        • {x}вывод последних пяти студентов, сдавших на отлично
//
//        • {x} вывод всех сданных предметов
public interface Exams {

    void putObject(Student student);
    Student getObject(String id) throws ItemNoFoundException;
    boolean containObject(String id);


   void putAll(List<Student> items);


    Map<String,Student> getAllObject();



    List<Student> AddListByNameSurnameObject(List<Student> items, String name, String surname, String object);


    List<Student> SearchLastStudentExamsFive(List<Student> items);


    Map<String, Double> calculateAverageGradeForRepeatedExams(List<Student> items);

    Integer calculateAverageGradeForRepeatedExamss(List<Student> items);
}
