package tema;

import java.util.ArrayList;

class PartialCourse extends Course{
    public static class PartialCourseBuilder extends CourseBuilder{
        public PartialCourseBuilder(String name, int credits, Strategy strategy){
            super(name,credits, strategy);
        }
        public Course build(){
            return new PartialCourse(this);
        }
    }
    public PartialCourse(PartialCourseBuilder builder){
        super(builder);
    }
    public ArrayList<Student> getGraduatedStudents(){
        ArrayList<Student> res = new ArrayList<>();
        for(int i=0;i<getGrades().size(); i++){
            Grade g = getGrades().get(i);
            if(g.getTotal()>=5)
                res.add(g.getStudent());
        }
        return res;
    }

}