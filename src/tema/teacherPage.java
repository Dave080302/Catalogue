package tema;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class teacherPage extends JFrame implements ListSelectionListener, ActionListener {
    JList<String> courseList;
    JPanel panel;
    JTextArea courseInfo;
    JTextArea unvalidatedGrades;
    JTextArea courseGrades;
    Teacher t;
    JButton validator;

    public void updateUnvalidatedGrades() {
        String a = "";
        a += "Unvalidated grades:";
        ArrayList arr = Main.sc.examScores.get(t);
        for (int i = 0; i < arr.size(); i++)
            a += "\n" + arr.get(i);
        unvalidatedGrades.setText(a);
    }

    public void updateValidatedGrades(Course cs) {
        String a = "";
        a += "Validated grades for selected course:";
        ArrayList<Grade> arr = cs.getGrades();
        for (int i = 0; i < arr.size(); i++) {
            Grade g = arr.get(i);
            a += "\n" + g.getStudent() + " partial: " + g.getPartialScore() + " exam: " + g.getExamScore() + " total: " + g.getTotal();
        }
        courseGrades.setText(a);
    }

    public teacherPage(Teacher t) {
        super("Courses of teacher " + t);
        this.t = t;
        Catalog c = Catalog.getInstance();
        setSize(1500, 500);
        setResizable(false);
        DefaultListModel<String> courses = new DefaultListModel<>();
        for (int i = 0; i < c.courses().size(); i++) {
            Course cs = c.courses().get(i);
            if (cs.getTeacher().getFirstName().equals(t.getFirstName()) && cs.getTeacher().getLastName().equals(t.getLastName()))
                courses.addElement(cs.getName());
        }
        courseList = new JList(courses);
        panel = new JPanel();
        panel.setSize(1500, 250);
        panel.add(courseList);
        courseList.addListSelectionListener(this);
        courseInfo = new JTextArea("Selected course info:");
        courseInfo.setEditable(false);
        courseGrades = new JTextArea("Validated grades for selected course: ");
        courseGrades.setEditable(false);
        unvalidatedGrades = new JTextArea("Unvalidated grades:");
        unvalidatedGrades.setEditable(false);
        JScrollPane scroll = new JScrollPane(unvalidatedGrades);
        if (Main.sc.examScores.get(t) != null)
            updateUnvalidatedGrades();
        panel.setLayout(new GridLayout(1, 3));
        panel.add(courseInfo);
        validator = new JButton("Validate grades");
        validator.setVisible(true);
        validator.addActionListener(this);
        validator.setSize(100, 100);
        panel.add(courseGrades);
        panel.add(scroll);
        panel.setBackground(Color.WHITE);
        setBackground(Color.WHITE);
        add(panel);
        add(validator, BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String str = "";
            Course cs = null;
            for (int i = 0; i < Catalog.getInstance().courses().size(); i++) {
                cs = Catalog.getInstance().courses().get(i);
                if (cs.getName().equals(courseList.getSelectedValue()))
                    break;
            }
            if (cs == null)
                return;
            str += "Selected course info:\n";
            str += "Name: " + cs.getName();
            str += "\nTeacher: " + cs.getTeacher();
            str += "\nAssistants: " + cs.assistants();
            if (cs.getBestStudent() != null)
                str += "\nBest student: " + cs.getBestStudent();
            else
                str += "\nBest student: Cannot calculate best student yet";
            if (cs.getGraduatedStudents() != null) {
                str += "\nGraduated students: ";
                for (int i = 0; i < cs.getGraduatedStudents().size(); i++)
                    str += "\n" + cs.getGraduatedStudents().get(i);
            } else
                str += "\nGraduated students: No passed students!";
            if (cs.getGrades() != null)
                updateValidatedGrades(cs);
            courseInfo.setText(str);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        t.accept(Main.sc);
        unvalidatedGrades.setText("Unvalidated grades:\n");
        validator.setEnabled(false);
    }
}

