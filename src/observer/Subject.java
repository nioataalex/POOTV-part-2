package observer;

import fileio.Notification;

public interface Subject  {

    /**
     * <p>
     * method that is used to add a new observer to a list of observers
     * Observer Pattern
     * @param observer observer
     */
    void register(Observer observer);

    /**
     * <p>
     * method that is used to delete an observer from the list of observers
     * Observer Pattern
     *  @param observer observer
     */
     void unregister(Observer observer);

    /**
     * <p>
     * method that is used to inform all observers that a new notification appeared
     * Observer Pattern
     * @param newNotification new notification
     */
     void notifyAllObservers(Notification newNotification);
}
