import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExamCache {
    private final Map<String, Double> averageGradeCache = new HashMap<>();

    public Double getAverageGrade(String subject, Univer univer) {
        return averageGradeCache.computeIfAbsent(subject, s -> {
            List<Student> students = univer.getAllObject().values().stream()
                    .filter(student -> student.object().equals(s))
                    .collect(Collectors.toList());
            return univer.calculateAverageGradeForRepeatedExams(students).getOrDefault(s, 0.0);
        });
    }
}
