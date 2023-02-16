package tema;

public class Notification {
    String parentType;
    String course;
    Student stud;
    Grade grade;
    public Notification(String introduction, Student s, String c, Grade g){
        parentType=introduction;
        stud=s;
        course=c;
        grade=g;
    }
    public String toString(){
        String a = "";
        a+= "Dear ";
        if(parentType.equals("father")){
            a+=stud.getFather().getFirstName() +" "+ stud.getFather().getLastName();
        }
        else{
            a+=stud.getMother().getFirstName()+" "+stud.getMother().getLastName();
        }
        a+=" your child's grade has been updated in "+course;
        a+=" it is now "+grade.getPartialScore() +" during the semester and ";
        a+=grade.getExamScore() +" in the exam, total of "+grade.getTotal();
        return a;
    }
}
