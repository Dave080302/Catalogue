package tema;
import java.util.*;
public class Catalog implements Subject {
    private final List<Course> courseList;
    private final List<Observer> observerList;
    private final List<Teacher> teacherList;
    private final List<Group<Student>> groupList;
    private final List<Parent> parentList;
    private final List<Student> studentList;
    private final List<Assistant> assistantList;
    private final static Catalog instance = new Catalog();

    private Catalog() {
        courseList = new ArrayList<>();
        observerList = new ArrayList<>();
        teacherList = new ArrayList<>();
        groupList = new ArrayList<>();
        studentList = new ArrayList<>();
        parentList = new ArrayList<>();
        assistantList = new ArrayList<>();
    }
    public List<Assistant> assistants(){
        return assistantList;
    }
    public void addAssistant(Assistant ass){
        boolean contains = false;
        for(int i=0;i<assistants().size();i++)
            if(assistants().get(i).getFirstName().equals(ass.getFirstName())
                && assistants().get(i).getLastName().equals(ass.getLastName()))
                contains=true;
        if(!contains)
            assistantList.add(ass);
    }
    public List<Course> courses(){
        return courseList;
    }
    public List<Group<Student>> groups(){
        return groupList;
    }
    public List<Student> students(){
        return studentList;
    }
    public void addStudent(Student stud){
        studentList.add(stud);
    }
    public List<Parent> parents(){
        return parentList;
    }
    public void addParent(Parent p){
        boolean contains = false;
        for(int i=0;i<parents().size();i++)
            if(parents().get(i).getFirstName().equals(p.getFirstName())
                    && parents().get(i).getLastName().equals(p.getLastName()))
                contains=true;
        if(!contains)
            parentList.add(p);
    }
    public List<Teacher> teachers(){
        return teacherList;
    }
    public static Catalog getInstance() {
        return instance;
    }
    public void addTeacher(Teacher t){
        boolean contains = false;
        for(int i=0;i<teachers().size();i++)
            if(teachers().get(i).getFirstName().equals(t.getFirstName())
                    && teachers().get(i).getLastName().equals(t.getLastName()))
                contains=true;
        if(!contains)
            teacherList.add(t);
    }
    public void addGroup(Group<Student> g){
        boolean contains=false;
        for(int i=0;i<groupList.size();i++)
            if(groupList.get(i).getID().equals(g.getID())) {
                contains = true;
                break;
            }
        if(!contains)
            groupList.add(g);
            groupList.set(groupList.indexOf(g),g);
    }
    public void addCourse(Course curs) {
        courseList.add(curs);
    }

    public void removeCourse(Course curs) {
            courseList.remove(curs);
    }

    @Override
    public void addObserver(Observer observer) {
        if(!observerList.contains(observer))
            observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers(Grade grade) {
        for (int i = 0; i < observerList.size(); i++) {
            Observer o = observerList.get(i);
            Parent p = (Parent) o;
            if(grade.getStudent().getFather().getFirstName().equals(p.getFirstName())
            && grade.getStudent().getFather().getLastName().equals(p.getLastName()))
                o.update(new Notification("father",grade.getStudent(),grade.getCourse(), grade));
            if(grade.getStudent().getMother().getFirstName().equals(p.getFirstName())
                    && grade.getStudent().getMother().getLastName().equals(p.getLastName()))
                o.update(new Notification("mother",grade.getStudent(),grade.getCourse(), grade));
            }
        }
    }
