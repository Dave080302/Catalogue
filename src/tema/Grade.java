package tema;

public class Grade implements Comparable,Cloneable{
    private Double partialScore, examScore;
    private Student student;
    private String course;
    public Grade(){
        partialScore = 0.0;
        examScore = 0.0;
    }
    public void setPartialScore(Double score){
        partialScore = score;
    }
    public Double getPartialScore(){
        return partialScore;
    }
    public void setExamScore(Double score){
        examScore = score;
    }
    public Double getExamScore(){
        return examScore;
    }
    public Student getStudent(){
        return student;
    }
    public void setStudent(Student s){
        student = s;
    }
    public void setCourse(String c){
        course = c;
    }
    public String getCourse(){
        return course;
    }
    public Double getTotal(){
        return getPartialScore()+getExamScore();
    }
    @Override
    public int compareTo(Object o) {
        Grade gr2 = (Grade) o;
        if(getTotal() > gr2.getTotal())
            return 1;
        else if(getTotal() < gr2.getTotal())
            return -1;
        else return getCourse().compareTo(gr2.getCourse());
    }

    @Override
    public Object clone() {
        try {
            Grade clone = (Grade) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
