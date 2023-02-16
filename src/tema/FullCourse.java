package tema;

import java.util.ArrayList;

class FullCourse extends Course{
    public static class FullCourseBuilder extends CourseBuilder{
        public FullCourseBuilder(String name, int credits, Strategy strategy){
            super(name,credits, strategy);
        }
        public Course build(){
            return new FullCourse(this);
        }
    }
    public FullCourse(FullCourseBuilder builder){
        super(builder);
    }
    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> res = new ArrayList<>();
        for(int i=0; i<getGrades().size(); i++){
            Grade g = getGrades().get(i);
            if(g.getPartialScore()>=3 && g.getExamScore()>=2)
                res.add(g.getStudent());
        }
        return res;
    }
}
