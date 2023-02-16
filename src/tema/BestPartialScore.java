package tema;
class BestPartialScore implements Strategy{

    @Override
    public Student getBestStudent(Course c) {
        double bestPartialScore = 0.00;
        Student currentBest = null;
        for(int i=0; i < c.getGrades().size(); i++){
            if(c.getGrades().get(i).getPartialScore() > bestPartialScore && c.getGraduatedStudents().contains(c.getGrades().get(i).getStudent())){
                bestPartialScore = c.getGrades().get(i).getPartialScore();
                currentBest = c.getGrades().get(i).getStudent();
            }
        }
        return currentBest;
    }
}