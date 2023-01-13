package observer;

import fileio.Notification;
import java.util.ArrayList;

public class NewUpdates implements Subject {

    private final Notification newNotification;
    private final ArrayList<Observer> observers;


    public NewUpdates(final Notification newNotification) {
        this.newNotification = newNotification;
        observers = new ArrayList<>();
    }

    /**
     * <p>
     *  adds a new observer to a list of observers
     * @param observer observer
     */
    @Override
    public void register(final Observer observer) {
        observers.add(observer);
    }

    /**
     * <p>
     * deletes an observer from the list of observers
     * @param observer observer
     */
    @Override
    public void unregister(final Observer observer) {
        observers.remove(observer);
    }

    /**
     * <p>
     * informs all observers that a new notification appeared
     * @param newNotification new notification
     */
    @Override
    public void notifyAllObservers(final Notification newNotification) {
        for (Observer observer : observers) {
            observer.update(newNotification);
        }
    }

    /**
     * <p>
     * appeals notifyAllObservers method, so every user will be informed about the new changes
     * @param newNotification new notification
     */
    public void notifyUsers(final Notification newNotification) {
        notifyAllObservers(newNotification);
    }
}
