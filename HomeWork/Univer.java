import java.util.*;

public class Univer implements Exams {
    private  final Map<String, Student> items = new HashMap<>(256);

    @Override
    public void putObject(Student student) {
        items.put(student.id(), student);
    }

    @Override
    public Student getObject(String id) throws ItemNoFoundException {
        Student student = items.get(id);
        if (student == null){
            throw new ItemNoFoundException(id);
        }
        return student;
    }

    @Override
    public boolean containObject(String id) {
        return  items.containsKey(id);
    }

    @Override
    public void putAll(List<Student> items) {
        for(Student item : items){
            putObject(item);
        }
    }

    @Override
    public Map<String, Student> getAllObject() {
        return new HashMap<>(items);
    }



    @Override
    public List<Student> AddListByNameSurnameObject(List<Student> items, String name, String surname, String object) {
        Student student = null;
        for (Student item : items) {
            if (item.name().equals(name) && item.surname().equals(surname) && item.object().equals(object)) {
                student = item;
                break;
            }
        }
        return student != null ? Collections.singletonList(student) : Collections.emptyList();
    }


    @Override
    public List<Student> SearchLastStudentExamsFive(List<Student> items) {
        List<Student> studentsWithGradeFive = new ArrayList<>();
        for (int i = items.size() - 1; i >= 0; i--) {
            Student student = items.get(i);
            if (student.grade() == 5) {
                studentsWithGradeFive.add(student);
                if (studentsWithGradeFive.size() == 5) {
                    break;
                }
            }
        }
        return studentsWithGradeFive;
    }

    @Override
    public Map<String, Double> calculateAverageGradeForRepeatedExams(List<Student> items) {
        Map<String, List<Integer>> subjectGrades = new HashMap<>();

        for (Student student : items) {
            String subject = student.object();
            int grade = student.grade();

            subjectGrades.computeIfAbsent(subject, k -> new ArrayList<>()).add(grade);
        }

        Map<String, Double> averageGrades = new HashMap<>();

        for (Map.Entry<String, List<Integer>> entry : subjectGrades.entrySet()) {
            String subject = entry.getKey();
            List<Integer> grades = entry.getValue();

            long count = grades.stream().filter(grade -> Collections.frequency(grades, grade) > 1).count();

            if (count > 1) {
                double average = grades.stream().mapToInt(Integer::intValue).average().orElse(0.0);
                averageGrades.put(subject, average);
            }
        }

        return averageGrades;
    }

    @Override
    public Integer calculateAverageGradeForRepeatedExamss(List<Student> items) {
        Integer quantity = 0;
        Map<String, List<Integer>> subjectGrades = new HashMap<>();

        for (Student student : items) {
            String subject = student.object();
            int grade = student.grade();
            subjectGrades.computeIfAbsent(subject, k -> new ArrayList<>()).add(grade);
        }
        Map<String, Double> averageGrades = new HashMap<>();

        for (Map.Entry<String, List<Integer>> entry : subjectGrades.entrySet()) {
            String subject = entry.getKey();
            List<Integer> grades = entry.getValue();
            long count = grades.stream().filter(grade -> Collections.frequency(grades, grade) > 1).count();

            if (count > 1) {
                double average = grades.stream().mapToInt(Integer::intValue).average().orElse(0.0);
                averageGrades.put(subject, average);
                quantity++; // Увеличиваем количество при каждом найденном повторении оценки
            }
        }
        return quantity;
    }
}



