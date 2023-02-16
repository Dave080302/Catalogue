package tema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class login extends JFrame implements ActionListener {
    JTextField name;
    JLabel nameWrite;
    JPanel panel;
    JButton send;
    String type;
    public login(String type){
        super("Login as "+type);
        this.type=type;
        setSize(300,100);
        setResizable(false);
        nameWrite = new JLabel("Name:");
        nameWrite.setSize(200,50);
        name = new JTextField("");
        send = new JButton("Register name");
        name.setSize(100,50);
        panel = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        send.addActionListener(this);
        panel.setLayout(new GridLayout(2,1));
        panel.add(nameWrite);
        panel.add(name);
        panel.add(send);
        add(panel,BorderLayout.CENTER);
        setVisible(true);
        pack();
    }
    public void parentLogin(ActionEvent e) {
        String input = name.getText();
        String [] names = input.split(" ");
        Catalog c = Catalog.getInstance();
        int i;
        Parent p;
        boolean found = false;
        for(i=0;i<c.parents().size();i++){
            p = c.parents().get(i);
            if(p.getFirstName().equals(names[0]) && p.getLastName().equals(names[1])){
                found = true;
                break;
            }
        }
        if(!found){
            name.setText("");
        }
        else{
            p=c.parents().get(i);
            name.setText("");
            setVisible(false);
            parentPage obj = new parentPage(p);
        }
    }
    public void teacherAssistantLogin(ActionEvent e){
        String input = name.getText();
        String [] names = input.split(" ");
        Catalog c = Catalog.getInstance();
        int i;
        Teacher t;
        Assistant ass;
        boolean found = false;
        for(i=0;i<c.teachers().size();i++){
            t = c.teachers().get(i);
            if(t.getFirstName().equals(names[0]) && t.getLastName().equals(names[1])){
                found = true;
                break;
            }
        }
        if(!found){
            for(i=0;i<c.assistants().size();i++){
                ass = c.assistants().get(i);
                if(ass.getFirstName().equals(names[0]) && ass.getLastName().equals(names[1])) {
                    found = true;
                    break;
                }
            }
            if(!found)
                name.setText("");
            else{
                ass = c.assistants().get(i);
                name.setText("");
                setVisible(false);
                assistantPage obj = new assistantPage(ass);
            }
        }
        else{
            t=c.teachers().get(i);
            name.setText("");
            setVisible(false);
            teacherPage obj = new teacherPage(t);
        }
    }
    public void studentLogin(ActionEvent e){
        String input = name.getText();
        String [] names = input.split(" ");
        Catalog c = Catalog.getInstance();
        int i;
        Student s;
        boolean found = false;
        for(i=0;i<c.students().size();i++){
            s = c.students().get(i);
            if(s.getFirstName().equals(names[0]) && s.getLastName().equals(names[1])){
                found = true;
                break;
            }
        }
        if(!found){
            name.setText("");
        }
        else{
            s=c.students().get(i);
            name.setText("");
            setVisible(false);
            studentPage obj = new studentPage(s);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(type.equals("Parent"))
            parentLogin(e);
        else if(type.equals("Teacher/Assistant"))
            teacherAssistantLogin(e);
        else
            studentLogin(e);
    }
}