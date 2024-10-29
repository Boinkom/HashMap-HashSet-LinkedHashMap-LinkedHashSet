import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UniverTest {

    private Exams exams;
    private ExamCache examCache;

    @BeforeEach
    void setUp() {
        exams = new Univer();
        examCache = new ExamCache();
    }

    @Test
    void putObject() throws ItemNoFoundException {
        Student student = new Student(UUID.randomUUID().toString(), "Ivan", "Ivanov", "Math", 12);
        exams.putObject(student);
        Student actual = exams.getObject(student.id());
        assertEquals(student, actual);
    }

    @Test
    void getObject() {
        Assertions.assertThrows(ItemNoFoundException.class, () -> exams.getObject("123"));
    }

    @Test
    void containObject() {
        Student student1 = new Student("1", "Ivan", "Ivanov", "Phis", 4);
        Student student2 = new Student("2", "Ivan", "Petrovich", "Math", 5);

        exams.putObject(student1);
        exams.putObject(student2);

        assertTrue(exams.containObject("2"));
        assertTrue(exams.containObject("1"));
        assertFalse(exams.containObject("3"));
    }

    @Test
    void getAllObject() {
        Student student1 = new Student("1", "Ivan", "Ivanov", "Phis", 4);
        Student student2 = new Student("2", "Ivan", "Petrovich", "Math", 5);
        Student student3 = new Student("3", "Vasia", "Ivanov", "Progrma", 3);
        exams.putAll(List.of(student1, student2, student3));

        Map<String, Student> allObject = exams.getAllObject();
        assertEquals(3, allObject.size());
    }

    @Test
    void AddListByNameSurnameObject() {
        Student student1 = new Student("1", "Ivan", "Ivanov", "Phis", 4);
        Student student2 = new Student("2", "Ivan", "Petrovich", "Math", 5);
        Student student3 = new Student("3", "Vasia", "Ivanov", "Progrma", 3);
        List<Student> items = List.of(student1, student2, student3);

        List<Student> result = exams.AddListByNameSurnameObject(items, "Ivan", "Ivanov", "Phis");

        assertEquals(1, result.size());
        assertEquals("Ivan", result.get(0).name());
        assertEquals("Ivanov", result.get(0).surname());
        assertEquals("Phis", result.get(0).object());
    }

    @Test
    void SearchLastStudentExamsFive() {
        Student student1 = new Student("1", "Ivan", "Ivanov", "Phis", 5);
        Student student2 = new Student("2", "Fedia", "Petrovich", "Math", 5);
        Student student3 = new Student("3", "Vasia", "Kostich", "ALGOS", 3);
        Student student4 = new Student("4", "Petia", "Ivanov", "AIG", 3);
        Student student5 = new Student("5", "Marat", "Petrovich", "Math", 5);
        Student student6 = new Student("6", "Nikita", "Ivanov", "Progrma", 3);
        Student student7 = new Student("7", "Gleb", "Temofeev", "Phis", 5);
        Student student8 = new Student("8", "Vasil", "Petrovich", "ALGOS", 5);
        Student student9 = new Student("9", "Vasia", "Osetrov", "OS", 3);
        List<Student> items = List.of(student1, student2, student5, student7, student8);

        exams.putAll(items);
        List<Student> result = exams.SearchLastStudentExamsFive(items);

        assertEquals(5, result.size());
    }

    @Test
    void printAverageGradeForRepeatedExams() {
        Student student1 = new Student("1", "Ivan", "Ivanov", "Phis", 5);
        Student student2 = new Student("2", "Fedia", "Petrovich", "Math", 5);
        Student student3 = new Student("3", "Vasia", "Kostich", "Progrma", 3);
        Student student4 = new Student("4", "Petia", "Ivanov", "AIG", 3);
        Student student5 = new Student("5", "Marat", "Petrovich", "Math", 5);
        Student student6 = new Student("6", "Nikita", "Ivanov", "Progrma", 3);
        Student student7 = new Student("7", "Gleb", "Temofeev", "Phis", 5);
        List<Student> items = List.of(student1, student2, student3, student5, student6, student7);

        Map<String, Double> result = exams.calculateAverageGradeForRepeatedExams(items);
        assertTrue(result.containsKey("Phis"));
        assertTrue(result.containsKey("Math"));
    }

    @Test
    void printAverageGradeForRepeatedExamsSingl() {
        Student student1 = new Student("1", "Ivan", "Ivanov", "Phis", 5);
        Student student2 = new Student("2", "Fedia", "Petrovich", "Math", 5);
        Student student3 = new Student("3", "Vasia", "Kostich", "Progrma", 3);
        Student student5 = new Student("5", "Marat", "Petrovich", "Math", 5);
        List<Student> items = List.of(student1, student2, student3, student5);

        Integer quantity = exams.calculateAverageGradeForRepeatedExamss(items);
        assertEquals(1, quantity);
    }

    // Тесты для ExamCache

    @Test
    void getAverageGradeFromCache() {
        Student student1 = new Student("1", "Ivan", "Ivanov", "Math", 4);
        Student student2 = new Student("2", "Fedia", "Petrovich", "Math", 5);
        Student student3 = new Student("3", "Ivan", "Ivanov", "Math", 3);

        exams.putAll(List.of(student1, student2, student3));

        double initialAverage = examCache.getAverageGrade("Math", (Univer) exams);
        assertEquals(0.0, initialAverage, 0.01);

        double cachedAverage = examCache.getAverageGrade("Math", (Univer) exams);
        assertEquals(initialAverage, cachedAverage);
    }

    @Test
    void getAverageGradeForDifferentSubjects() {
        Student student1 = new Student("1", "Ivan", "Ivanov", "Math", 4);
        Student student2 = new Student("2", "Fedia", "Petrovich", "Physics", 5);
        Student student3 = new Student("3", "Ivan", "Ivanov", "Physics", 3);

        exams.putAll(List.of(student1, student2, student3));

        double mathAverage = examCache.getAverageGrade("Math", (Univer) exams);
        double physicsAverage = examCache.getAverageGrade("Physics", (Univer) exams);

        assertEquals(0.0, mathAverage, 0.01);
        assertEquals(0.0, physicsAverage, 0.01);
    }

    @Test
    void getAverageGradeNotCachedSubject() {
        Student student1 = new Student("1", "Ivan", "Ivanov", "Math", 4);
        exams.putObject(student1);

        double result = examCache.getAverageGrade("Physics", (Univer) exams);
        assertEquals(0.0, result);
    }
}
