package tema;
import java.io.*;
import java.util.*;

public class Main {
    static ScoreVisitor sc;
    static File inputFile;
    static File outputFile;
    static Scanner inputReader;
    static FileWriter outputWriter;
    public static void readStudents(Group<Student> g){
        int studentsNo = Integer.parseInt(inputReader.nextLine());
        for(int i=0;i<studentsNo;i++){
            String studentInfo=inputReader.nextLine();
            String[] infoArray = studentInfo.split(" ");
            Student s = new Student(infoArray[0], infoArray[1]);
            Parent mother = new Parent(infoArray[2],infoArray[3]);
            Catalog.getInstance().addParent(mother);
            s.setMother(mother);
            Catalog.getInstance().addObserver(mother);
            Parent father = new Parent(infoArray[4],infoArray[5]);
            s.setFather(father);
            Catalog.getInstance().addParent(father);
            Catalog.getInstance().addObserver(father);
            Catalog.getInstance().addStudent(s);
            g.add(s);
        }
    }
    public static void readCourseInfo(){
        String courseInfo = inputReader.nextLine();
        String [] info = courseInfo.split(" ");
        String name = info[0];
        Course c;
        Teacher t = new Teacher(info[2],info[3]);
        if(!Catalog.getInstance().teachers().contains(t))
            Catalog.getInstance().addTeacher(t);
        int creditsNo = Integer.parseInt(info[4]);
        if(info[1].equals("Partial"))
            c = new PartialCourse.PartialCourseBuilder(name, creditsNo, new BestPartialScore()).teacher(t).build();
        else if(info[1].equals("Total"))
            c= new FullCourse.FullCourseBuilder(name,creditsNo, new BestTotalScore()).teacher(t).build();
        else
            c=new FullCourse.FullCourseBuilder(name,creditsNo, new BestExamScore()).teacher(t).build();
        for(int i=5; i<info.length; i+=3){
            String groupID = info[i];
            Assistant ass = new Assistant(info[i+1],info[i+2]);
            int j;
            for(j=0;j<Catalog.getInstance().groups().size();j++)
                if(Catalog.getInstance().groups().get(j).getID().equals(groupID))
                    break;
            Catalog.getInstance().addAssistant(ass);
            Group<Student> groupStudents = Catalog.getInstance().groups().get(j);
            Group<Student> g = new Group<>(groupID, ass);
            g.addAll(groupStudents);
            if(i%5 == 0)
                c.addGroup(g);
            else
                c.addGroup(g.getID(),ass);

        }
        Catalog.getInstance().addCourse(c);
    }
    public static void readPartialGrades() {
        Catalog c = Catalog.getInstance();
        for (int i = 0; i < c.assistants().size(); i++) {
            Assistant assistant = c.assistants().get(i); /// fiecare asistent isi completeaza notele pentru toate grupele la care preda
            for (int j = 0; j < c.courses().size(); j++) {
            Course course = c.courses().get(j);
            for (int k = 0; k < course.getGroups().size(); k++) {
                Group<Student> g = course.getGroups().get(k);
                if (g.getAssistant().getFirstName().equals(assistant.getFirstName()) &&
                        g.getAssistant().getLastName().equals((assistant.getLastName()))) {
                    for (int y = 0; y < g.size(); y++) {
                        String s = Main.inputReader.nextLine();
                        String[] splittedString = s.trim().split("\\s+");
                        String firstName = splittedString[0];
                        String lastName = splittedString[1];
                        Double partialResult = Double.parseDouble(splittedString[2]);
                        List<Student> studentList = new ArrayList<>(g);
                        int x;
                        for (x = 0; x < studentList.size(); x++)
                            if (firstName.equals(studentList.get(x).getFirstName())
                                    && lastName.equals(studentList.get(x).getLastName()))
                                break;
                        if (x < studentList.size()) {
                            sc.addGrade(assistant,studentList.get(x),course.getName(),partialResult);
                        }
                    }
                }
            }
        }
        }
    }
    public static void readExamGrades(){
        Catalog c = Catalog.getInstance();
        for(int i=0;i<c.teachers().size();i++){
            Teacher teacher = c.teachers().get(i);
            for (int j = 0; j < c.courses().size(); j++) {
                Course course = c.courses().get(j);
                if (course.getTeacher().getFirstName().equals(teacher.getFirstName())
                        && course.getTeacher().getLastName().equals(teacher.getLastName())) {
                    ArrayList<Student> studentList = course.getAllStudents();
                    for (int k = 0; k < studentList.size(); k++) {
                        String s = Main.inputReader.nextLine();
                        String[] splittedString = s.trim().split("\\s+");
                        String firstName = splittedString[0];
                        String lastName = splittedString[1];
                        Double examResult = Double.parseDouble(splittedString[2]);
                        int x;
                        for (x = 0; x < studentList.size(); x++)
                            if (firstName.equals(studentList.get(x).getFirstName())
                                    && lastName.equals(studentList.get(x).getLastName()))
                                break;
                        if (x < studentList.size())
                            sc.addGrade(teacher,studentList.get(x), course.getName(), examResult);
                    }
                }
            }
        }
        }
    public static void main(String[] args) throws IOException {
        try {
            inputFile = new File("src/tema/input.txt");
            inputReader = new Scanner(inputFile);
            outputFile = new File("src/tema/output.txt");
            if(outputFile.createNewFile())
                System.out.println("File created");
            else System.out.println("File exists");
            outputWriter = new FileWriter(outputFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Catalog c = Catalog.getInstance();
        int noGroups;
        noGroups = Integer.parseInt(inputReader.nextLine());
        for(int i=0;i< noGroups; i++){
            Group<Student> g = new Group<>(inputReader.nextLine());
            readStudents(g);
            c.addGroup(g);
        }
        int noCourses;
        noCourses = Integer.parseInt(inputReader.nextLine());
        for (int i = 0; i < noCourses; i++)
            readCourseInfo();
        int i;
        outputWriter.write("Course creation test\n");
        for(i=0;i<c.courses().size();i++) {
            Course cs = c.courses().get(i);
            outputWriter.write(cs.getName() + " " + cs.getTeacher() + " " + cs.getAllStudents() + "\n");
        }
        Student newStud = new Student("Divad","Naghel");
        newStud.setFather(new Parent("Rcisti","Naghel"));
        newStud.setMother(new Parent("Gamda","Naghel"));
        c.courses().get(3).addStudent("341C3", newStud);
        sc = new ScoreVisitor();
        readPartialGrades();
        outputWriter.flush();
        outputWriter.write("\n\nAssistants & groups for each course\n");
        for(i=0;i<c.courses().size();i++){
            Course cs = c.courses().get(i);
            outputWriter.write("Assistants for course " +cs.getName()+", teacher "+cs.getTeacher()+"\n");
            for(int j=0;j<cs.getGroups().size();j++){
                Group<Student> g = cs.getGroups().get(j);
                outputWriter.write("Assistant: "+g.getAssistant()+" Group:"+g.getID()+"\n");
            }
        }
        outputWriter.write("\n\nStudent Adder && Grades add test\n");
        readExamGrades();
        for(i=0;i<c.assistants().size();i++)
            c.assistants().get(i).accept(sc);
        c.teachers().get(0).accept(sc);
        c.teachers().get(1).accept(sc);
        c.teachers().get(2).accept(sc);
        for(i=0;i<c.courses().size();i++){
            Course cs = c.courses().get(i);
            outputWriter.write(cs.getName() + " grades\n");
            Collection<Grade> collection = cs.getAllStudentGrades().values();
            ArrayList<Grade> grades = new ArrayList<>(collection);
            for(int j=0;j<cs.getAllStudentGrades().size();j++){
                Grade g = grades.get(j);
                outputWriter.write(g.getStudent() +" partialScore:"+g.getPartialScore()+
                        " examScore: "+g.getExamScore()+" total: "+g.getTotal());
                if(cs.getGraduatedStudents().contains(g.getStudent()))
                    outputWriter.write(" PASSED");
                else
                    outputWriter.write(" FAILED");
                outputWriter.write("\n");
            }
        }
        outputWriter.write("\n\nPassed Students for every course test\n");
        for(i=0;i<c.courses().size();i++){
            Course cs = c.courses().get(i);
            outputWriter.write("Passed students for course "+cs.getName()+ ", teacher "+cs.getTeacher() +cs.getGraduatedStudents()+"\n");
        }
        outputWriter.write("\n\nParent notification list test\n");
        for(i=0;i<c.parents().size();i++)
            outputWriter.write(c.parents().get(i)+ " "+c.parents().get(i).notificationList+"\n");
        outputWriter.write("\n\nBackup test\n");
        Course cs2 = c.courses().get(5);
        cs2.makeBackup();
        for(i=0;i<cs2.getGrades().size();i++)
            cs2.replaceGrade(null, i);
        outputWriter.write("After deletion of grades & teacher\n");
        outputWriter.write(cs2.getGrades()+"\n");
        cs2.undo();
        outputWriter.write("After restoration\n");
        outputWriter.write(cs2.getAllStudentGrades()+"\n");
        outputWriter.write("\n\nBest Student test\n");
        for(i=0;i<c.courses().size();i++){
            Course cs = c.courses().get(i);
            outputWriter.write("Best student for course "+cs.getName()+" is "+cs.getBestStudent()+"\n");
        }
        for(i=0;i<c.courses().size();i++){
            Course cs = c.courses().get(i);
            while(cs.getGrades().size()>0){
                Grade g = cs.getGrades().get(0);
                ArrayList<Group<Student>> groups=cs.getGroups();
                int k;
                for(k=0;k<groups.size();k++)
                    if (groups.get(k).contains(g.getStudent()))
                     break;
                Group gr = groups.get(k);
                Teacher t = null;
                Assistant ass = null;
                for(int x=0;x<c.teachers().size();x++)
                    if(cs.getTeacher().getLastName().equals(c.teachers().get(x).getLastName())&&
                            cs.getTeacher().getFirstName().equals(c.teachers().get(x).getFirstName()))
                        t=c.teachers().get(x);
                for(int x=0;x<c.assistants().size();x++)
                    if(gr.getAssistant().getLastName().equals(c.assistants().get(x).getLastName())
                        && gr.getAssistant().getFirstName().equals(c.assistants().get(x).getFirstName()))
                            ass=c.assistants().get(x);
                sc.addGrade(t,g.getStudent(),g.getCourse(),g.getExamScore());
                sc.addGrade(ass,g.getStudent(),g.getCourse(),g.getPartialScore());
                cs.removeGrade(g);
            }
        }
        for(i=0;i<c.parents().size();i++){
            Parent p = c.parents().get(i);
            while(p.notificationList.size()>0)
                p.notificationList.remove(0);
        }
        mainMenu obj = new mainMenu();
//        outputWriter.write("\n\nCourse removal\n");
//        c.removeCourse(c.courses().get(4));
//        c.removeCourse(c.courses().get(4));
//        c.removeCourse(c.courses().get(0));
//        for(i=0;i<c.courses().size();i++) {
//            Course cs = c.courses().get(i);
//            outputWriter.write(cs.getName() + " " + cs.getTeacher() + " " + cs.getAllStudents() + "\n");
//        }
        outputWriter.close();
    }
}

