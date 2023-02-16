package tema;
import java.util.*;
public abstract class Course{
    private final String name;
    private Teacher teacher;
    private Set<Assistant> assistants;
    private ArrayList<Grade> grades;
    private TreeMap<String, Group<Student>> groups;
    private final Strategy strategy;
    private static Snapshot backup;
    private final int credits;
    public Course(CourseBuilder builder){
        name=builder.name;
        teacher=builder.teacher;
        assistants=builder.assistants;
        grades=builder.grades;
        groups=builder.groups;
        credits=builder.credits;
        strategy=builder.strategy;
        backup=new Snapshot();
    }
    public abstract static class CourseBuilder{
        private final String name;
        private Teacher teacher;
        private final int credits;
        private Set<Assistant> assistants=new HashSet<>();
        private ArrayList<Grade> grades = new ArrayList<>();
        private TreeMap<String,Group<Student>> groups = new TreeMap<>();
        private final Strategy strategy;
        public CourseBuilder(String name, int credits, Strategy strategy){
            this.name = name;
            this.credits=credits;
            this.strategy = strategy;
        }
        public CourseBuilder assistants(Set<Assistant> assistants){
            this.assistants=assistants;
            return this;
        }
        public CourseBuilder teacher(Teacher teacher){
            this.teacher = teacher;
            return this;
        }
        public int getCredits(){
            return credits;
        }
        public CourseBuilder grades(ArrayList<Grade> grades){
            this.grades=grades;
            return this;
        }
        public CourseBuilder groups(TreeMap<String,Group<Student>> groups){
            this.groups=groups;
            return this;
        }
        public abstract Course build();
    }
    private class Snapshot{
        ArrayList<Grade> backup;
        boolean backuped;
        public Snapshot(){
            backup = new ArrayList<>();
            backuped=false;
        }
        public void makeBackup(){
            if(backuped == false) {
                for (int i = 0; i < grades.size(); i++) {
                    Grade g = (Grade) grades.get(i).clone();
                    backup.add(g);
                }
                backuped = true;
            }
            else{
                for(int i=0;i<grades.size();i++)
                    backup.set(i,(Grade)grades.get(i).clone());
            }
        }
        public ArrayList<Grade> getBackup(){
            return backup;
        }
    }
    public void makeBackup(){
        backup.makeBackup();
    }
    public void undo(){
        ArrayList<Grade> old = backup.getBackup();
        for(int i=0;i< old.size(); i++) {
            if (i < grades.size())
                grades.set(i, (Grade) old.get(i).clone());
            else
                grades.add((Grade) old.get(i).clone());
        }
        if(grades.size()>old.size())
            for(int i=old.size();i<grades.size();i++)
                grades.remove(i);
    }
    public ArrayList<Grade> getGrades() {
        return grades;
    }
    public void replaceGrade(Grade g, int index){
        grades.set(index,g);
    }
    public void setTeacher(Teacher t){
        teacher = t;
    }
    public Teacher getTeacher(){
        return teacher;
    }
    public ArrayList<Group<Student>> getGroups(){
        return new ArrayList<Group<Student>>(groups.values());
    }
    public String getName(){
        return name;
    }
    public void removeGrade(Grade g){
        grades.remove(g);
    }
    public void addAssistant(String ID, Assistant assistant){
        assistants.add(assistant);
        Group<Student> g = groups.get(ID);
        g.setAssistant(assistant);
        groups.put(ID,g);
    }
    public void addStudent(String ID, Student stud){ /// metoda addStudent adauga un student in grupa doar pentru acest curs
        Group<Student> g = groups.get(ID); /// nu si in lista generala a grupelor din catalog folosita pt interfata grafica
        g.add(stud);
        Catalog.getInstance().addObserver(stud.getFather());
        Catalog.getInstance().addObserver(stud.getMother());
        groups.put(ID,g);
    }
    public ArrayList<Assistant> assistants(){
        return new ArrayList<Assistant>(assistants);
    }
    public void addGroup(Group<Student> group){
        assistants.add(group.getAssistant());
        groups.put(group.getID(), group);
    }
    public void addGroup(String ID, Assistant assistant){
        boolean found = false;
        Group<Student> g;
        int i;
        for(i=0;i<Catalog.getInstance().groups().size();i++) {
            if (Catalog.getInstance().groups().get(i).getID().equals(ID)){
                found = true;
                break;
            }
        }
        if(!found)
            g = new Group<>(ID,assistant);
        else {
            Group<Student> obj = Catalog.getInstance().groups().get(i);
            g = new Group<>(ID,assistant);
            g.addAll(obj);
        }
        assistants.add(g.getAssistant());
        groups.put(ID,g);
    }
    public void addGroup(String ID, Assistant assistant, Comparator<Student> comp){
        Group<Student> g = new Group<>(ID,assistant, comp);
        groups.put(ID,g);
    }
    public Grade getGrade(Student stud){
        for(int i=0;i<grades.size(); i++){
            Grade grade = grades.get(i);
            if(grade !=null) {
                if (grade.getStudent().equals(stud))
                    return grade;
            }
        }
        return null;
    }
    public void addGrade(Grade g){
        grades.add(g);
        Collections.sort(grades);
    }
    public ArrayList<Student> getAllStudents(){
        ArrayList<Student> students = new ArrayList<>();
        Set<Map.Entry<String, Group<Student>>> set = groups.entrySet();
        List<Map.Entry<String,Group<Student>>> arr = new ArrayList<>(set);
        for(int i=0;i<set.size();i++){
            Group<Student> g = arr.get(i).getValue();
            ArrayList<Student> list = new ArrayList<>(g);
            students.addAll(list);
        }
        return students;
    }
    public HashMap<Student, Grade> getAllStudentGrades(){
        HashMap<Student,Grade> res = new HashMap<>();
        for(int i=0;i<grades.size();i++){
            Grade g = grades.get(i);
            res.put(g.getStudent(), g);
        }
        return res;
    }
    public Student getBestStudent(){
        return strategy.getBestStudent(this);
    }
    public abstract ArrayList<Student> getGraduatedStudents();
}