package observer;

import fileio.Notification;

public interface Subject  {

    /**
     * <p>

     */
    void register(Observer o);

    /**
     * <p>

     */
     void unregister(Observer o);

    /**
     * <p>

     */
     void notifyAllObservers(Notification newNotification);
}
