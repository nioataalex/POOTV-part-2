package factory;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.MovieCredentials;
import fileio.Notification;
import fileio.User;
import observer.NewUpdates;
import observer.Observer;
import pages.Output;

import java.util.ArrayList;

public class AddChange extends DatabaseFactory {

    private final ArrayList<MovieCredentials> movies;
    private final MovieCredentials addedMovie;

    private final Output printOutput;
    private final ArrayNode output;

    private final ArrayList<User> users;

    private final ArrayList<Observer> observers;

    public AddChange(final ArrayList<MovieCredentials> movies, final MovieCredentials addedMovie,
                     final Output printOutput, final ArrayNode output,
                     final ArrayList<User> users) {
        this.addedMovie = addedMovie;
        this.movies = movies;
        this.printOutput = printOutput;
        this.output = output;
        this.users = users;
        observers = new ArrayList<>();
    }

    /**
     * <p>
     method that adds a new movie to the list of movies and informs the users that have
     a subscription at one of the genres from the added movie, that a new movie appeared in
     the database, through Observer Pattern
     */
    @Override
    public void action() {
        for (MovieCredentials movie : movies) {
            if (movie.getName().compareTo(addedMovie.getName()) == 0) {
                printOutput.errorOutput(output);
                return;
            }
        }
        movies.add(addedMovie);

        for (int k = 0; k < users.size(); k++) {
            boolean subscribed = false;
            if (users.get(k).getSubscribedGenres() != null) {
                for (int i = 0; i <  addedMovie.getGenres().size(); i++) {
                    for (int j = 0; j < users.get(k).getSubscribedGenres().size(); j++) {
                        if (addedMovie.getGenres().
                                get(i).compareTo(users.get(k).
                                        getSubscribedGenres().get(j)) == 0) {
                            subscribed = true;
                            break;
                        }
                    }
                }
            }

            if (subscribed) {
                Notification newNotification = new Notification();
                newNotification.setMovieName(addedMovie.getName());
                newNotification.setMessage("ADD");

                NewUpdates updates = new NewUpdates(newNotification);

                for (User user : users) {
                    updates.register(user);
                }
                updates.notifyUsers(newNotification);
            }
        }
    }

}
