package tema;

class BestExamScore implements Strategy{
    public Student getBestStudent(Course c) {
        double bestExamScore = 0.00;
        Student currentBest = null;
        for(int i=0; i < c.getGrades().size(); i++){
            if(c.getGrades().get(i).getExamScore() > bestExamScore  && c.getGraduatedStudents().contains(c.getGrades().get(i).getStudent())){
                bestExamScore = c.getGrades().get(i).getExamScore();
                currentBest = c.getGrades().get(i).getStudent();
            }
        }
        return currentBest;
    }
}
