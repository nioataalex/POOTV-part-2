package observer;

import fileio.Notification;

public interface Observer {
    /**
     * <p>

     */
    void update(Notification newNotification);
}
