package tema;
import java.util.*;

public class ScoreVisitor implements Visitor {
    HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>> examScores;
    HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>> partialScores;

    public ScoreVisitor() {
        examScores = new HashMap<>();
        partialScores = new HashMap<>();
    }

    static private class Tuple<K, V, T> {
        private final K key;
        private final V value1;
        private final T value2;

        public Tuple(K key, V value1, T value2) {
            this.key = key;
            this.value1 = value1;
            this.value2 = value2;
        }

        public K getKey() {
            return key;
        }

        public V getValue1() {
            return value1;
        }

        public T getValue2() {
            return value2;
        }

        public String toString() {
            return "(" + key + ", " + value1 + ", " + value2 + ")";
        }
    }
    public void addGrade(Assistant assistant, Student s, String name, Double grade) {
        Tuple<Student, String, Double> newGrade = new Tuple<>(s,name,grade);
        ArrayList<Tuple<Student, String, Double>> gradeList = partialScores.get(assistant);
        if (gradeList == null)
            gradeList = new ArrayList<>();
        gradeList.add(newGrade);
        partialScores.put(assistant, gradeList);
    }
    public void addGrade(Teacher teacher, Student s, String name, Double grade) {
        Tuple<Student, String, Double> newGrade = new Tuple<>(s,name,grade);
        ArrayList<Tuple<Student, String, Double>> gradeList = examScores.get(teacher);
        if (gradeList == null)
            gradeList = new ArrayList<>();
        gradeList.add(newGrade);
        examScores.put(teacher, gradeList);
    }
    @Override
    public void visit(Assistant assistant) {
        ArrayList<Tuple<Student, String, Double>> grades = partialScores.get(assistant);
        Catalog c = Catalog.getInstance();
        for (int i = 0; i < grades.size(); i++) {
            Student stud = grades.get(i).getKey();
            String courseName = grades.get(i).getValue1();
            int courseIndex;
            for (courseIndex = 0; courseIndex < c.courses().size(); courseIndex++)
                if (courseName.equals(c.courses().get(courseIndex).getName()))
                    break;
            if (c.courses().get(courseIndex).getGrade(stud) != null) {
                Grade oldGrade = c.courses().get(courseIndex).getGrade(stud);
                Grade g = new Grade();
                g.setStudent(oldGrade.getStudent());
                g.setExamScore(oldGrade.getExamScore());
                g.setCourse(oldGrade.getCourse());
                g.setPartialScore(grades.get(i).getValue2());
                c.courses().get(courseIndex).replaceGrade(g, c.courses().get(courseIndex).getGrades().indexOf(oldGrade));
                c.notifyObservers(g);
            } else {
                Grade g = new Grade();
                g.setStudent(stud);
                g.setCourse(courseName);
                g.setPartialScore(grades.get(i).getValue2());
                c.courses().get(courseIndex).addGrade(g);
                c.notifyObservers(g);
            }
        }
        partialScores.remove(assistant);
    }

    @Override
    public void visit(Teacher teacher) {
        ArrayList<Tuple<Student, String, Double>> grades = examScores.get(teacher);
        Catalog c = Catalog.getInstance();
        for (int i = 0; i < grades.size(); i++) {
            Student stud = grades.get(i).getKey();
            String courseName = grades.get(i).getValue1();
            int courseIndex;
            for (courseIndex = 0; courseIndex < c.courses().size(); courseIndex++)
                if (courseName.equals(c.courses().get(courseIndex).getName()))
                    break;
            if (c.courses().get(courseIndex).getGrade(stud) != null) {
                Grade oldGrade = c.courses().get(courseIndex).getGrade(stud);
                Grade g = new Grade();
                g.setStudent(oldGrade.getStudent());
                g.setPartialScore(oldGrade.getPartialScore());
                g.setCourse(oldGrade.getCourse());
                g.setExamScore(grades.get(i).getValue2());
                c.courses().get(courseIndex).replaceGrade(g, c.courses().get(courseIndex).getGrades().indexOf(oldGrade));
                c.notifyObservers(g);
            } else {
                Grade g = new Grade();
                g.setStudent(stud);
                g.setCourse(courseName);
                g.setExamScore(grades.get(i).getValue2());
                c.courses().get(courseIndex).addGrade(g);
                c.notifyObservers(g);
            }
        }
        examScores.remove(teacher);
    }
}