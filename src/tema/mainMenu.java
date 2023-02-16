package tema;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class mainMenu extends JFrame implements ActionListener{
    JButton TeacherAssistantButton;
    JButton StudentButton;
    JButton ParentButton;
    JPanel buttons;
    JTextArea textField;
    public mainMenu(){
        super("Catalog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TeacherAssistantButton = new JButton();
        TeacherAssistantButton.setText("Teacher/Assistant");
        StudentButton = new JButton("Student");
        ParentButton = new JButton("Parent");
        TeacherAssistantButton.addActionListener(this);
        StudentButton.addActionListener(this);
        ParentButton.addActionListener(this);
        textField = new JTextArea("What user type are you?");
        textField.setEditable(false);
        buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,3));
        buttons.add(TeacherAssistantButton);
        buttons.add(ParentButton);
        buttons.add(StudentButton,1);
        add(textField,BorderLayout.NORTH);
        add(buttons,BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ParentButton) {
            login obj = new login("Parent");
        }
        else if(e.getSource()==TeacherAssistantButton) {
            login obj = new login("Teacher/Assistant");
        }
        else {
            login obj = new login("Student");
        }
    }
}