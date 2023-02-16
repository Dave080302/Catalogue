package tema;

class BestTotalScore implements Strategy{
    public Student getBestStudent(Course c) {
        double bestTotalScore = 0.00;
        Student currentBest = null;
        for(int i=0; i < c.getGrades().size(); i++){
            if(c.getGrades().get(i).getTotal() > bestTotalScore  && c.getGraduatedStudents().contains(c.getGrades().get(i).getStudent())){
                bestTotalScore = c.getGrades().get(i).getTotal();
                currentBest = c.getGrades().get(i).getStudent();
            }
        }
        return currentBest;
    }
}
