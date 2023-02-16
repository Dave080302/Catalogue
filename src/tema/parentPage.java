package tema;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.*;
public class parentPage extends JFrame{
    JList notifications;
    public parentPage(Parent p){
        super("Notifications for "+p);
        setSize(1500,500);
        setResizable(false);
        notifications = new JList(p.notificationList.toArray());
        add(notifications,BorderLayout.NORTH);
        setVisible(true);
    }
}