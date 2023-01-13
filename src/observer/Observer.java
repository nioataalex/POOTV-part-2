package observer;

import fileio.Notification;

public interface Observer {
    /**
     * <p>
     * method that is used in Observer Pattern to announce
     * all observers that a change is going to happen
     * Observer Pattern
     @param newNotification new notification
     */
    void update(Notification newNotification);
}
