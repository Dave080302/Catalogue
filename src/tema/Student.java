package tema;

class Student extends User implements Comparable<Object>{
    private Parent mother, father;
    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }
    public void setMother(Parent mother){
        this.mother = mother;
    }
    public Parent getMother(){
        return mother;
    }
    public Parent getFather(){
        return father;
    }
    public void setFather(Parent father){
        this.father = father;
    }

    @Override
    public int compareTo(Object o) {
        Student stud = (Student) o;
        if(getLastName().compareTo(stud.getLastName()) !=0)
            return getLastName().compareTo((stud.getLastName()));
        else return getFirstName().compareTo(stud.getFirstName());
    }
}
