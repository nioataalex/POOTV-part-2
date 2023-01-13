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

     */
    @Override
    public void register(final Observer o) {
        observers.add(o);
    }

    /**
     * <p>

     */
    @Override
    public void unregister(final Observer o) {
        observers.remove(o);
    }

    /**
     * <p>

     */
    @Override
    public void notifyAllObservers(final Notification newNotification) {
        for (Observer follower : observers) {
            follower.update(newNotification);
        }
    }

    /**
     * <p>

     */
    public void notification(final Notification newNotification) {
        notifyAllObservers(newNotification);
    }
}
