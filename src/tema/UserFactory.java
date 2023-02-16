package tema;

class UserFactory{
    public User getUser(String user,String firstname, String lastname){
        if(user==null)
            return null;
        else if(user.equals("Teacher"))
            return new Teacher(firstname,lastname);
        else if(user.equals("Parent"))
            return new Parent(firstname,lastname);
        else if(user.equals("Assistant"))
            return new Assistant(firstname,lastname);
        else
            return new Student(firstname,lastname);
    }
}
