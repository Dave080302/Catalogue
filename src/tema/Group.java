package tema;
import java.util.*;
public class Group<Student> extends TreeSet<Student>{
    private Assistant assistant;
    private String ID;
    public Group(String id){
        super();
        ID = id;
    }
    public Group(String id, Assistant ass){
        super();
        ID=id;
        assistant=ass;
    }
    public Group(String id, Assistant ass, Comparator<Student> comp){
        super(comp);
        ID=id;
        assistant=ass;
    }
    public String getID(){
        return ID;
    }
    public void setID(String id){
        ID=id;
    }
    public void setAssistant(Assistant ass){
        assistant=ass;
    }
    public Assistant getAssistant(){
        return assistant;
    }
}
