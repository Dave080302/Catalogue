package tema;

import java.util.ArrayList;
import java.util.List;

class Parent extends User implements Observer{
    List<Notification> notificationList;
    public Parent(String firstName, String lastName) {
        super(firstName, lastName);
        notificationList = new ArrayList<>();
    }

    @Override
    public void update(Notification notification) {
        notificationList.add(notification);
    }
}
