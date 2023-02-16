package tema;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

class studentPage extends JFrame implements ListSelectionListener {
    JList<String> courseList;
    JPanel panel;
    JTextArea courseInfo;
    Student s;
    public studentPage(Student s){
        super("Grades for "+s);
        this.s=s;
        Catalog c = Catalog.getInstance();
        setSize(1500,500);
        setResizable(false);
        DefaultListModel<String> courses = new DefaultListModel<>();
        for(int i=0; i< c.courses().size();i++){
            Course cs = c.courses().get(i);
            if(cs.getAllStudents().contains(s))
                courses.addElement(cs.getName());
        }
        courseList = new JList(courses);
        panel = new JPanel();
        panel.add(courseList);
        courseList.addListSelectionListener(this);
        courseInfo = new JTextArea("Selected course info:");
        courseInfo.setEditable(false);
        panel.setLayout(new GridLayout(1,2));
        panel.add(courseInfo);
        panel.setBackground(Color.WHITE);
        setBackground(Color.WHITE);
        add(panel,BorderLayout.NORTH);
        setVisible(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            String str = "";
            Course cs = null;
            for(int i=0;i<Catalog.getInstance().courses().size();i++) {
                cs = Catalog.getInstance().courses().get(i);
                if (cs.getName().equals(courseList.getSelectedValue()))
                    break;
            }
            if(cs == null)
                return;
            str+="Selected course info:\n";
            str+="Name: "+cs.getName();
            str+="\nTeacher: "+cs.getTeacher();
            str+="\nAssistants: "+cs.assistants();
            str+="\nYour Assistant: ";
            for(int i=0;i<cs.getGroups().size();i++){
                Group<Student> g = cs.getGroups().get(i);
                if(g.contains(s)){
                    str+=g.getAssistant();
                    break;
                }
            }
            if(cs.getGrade(s) !=null) {
                str += "\nPartial Score:" + cs.getGrade(s).getPartialScore();
                str += "\nExam score:" + cs.getGrade(s).getExamScore();
                str += "\nTotal score:" + cs.getGrade(s).getTotal();
            }
            else
                str+="\nNo grade for this course yet!";
            if(cs.getBestStudent()!=null)
                str+="\nBest student total: "+cs.getGrade(cs.getBestStudent()).getTotal();
            else
                str+="\nBest student total: Cannot calculate best student yet!";
            courseInfo.setText(str);
        }
    }
}