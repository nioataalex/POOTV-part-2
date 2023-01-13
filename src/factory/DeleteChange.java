package factory;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.MovieCredentials;
import fileio.Notification;
import fileio.User;
import observer.NewUpdates;
import pages.Output;


import java.util.ArrayList;

public class DeleteChange extends DatabaseFactory {
    private final ArrayList<MovieCredentials> movies;
    private final String deletedMovie;
    private final Output printOutput;
    private final ArrayNode output;

    private final ArrayList<User> users;


    public DeleteChange(final ArrayList<MovieCredentials> movies, final String deletedMovie,
                     final ArrayList<User> users,
                     final Output printOutput, final ArrayNode output
    ) {
        this.deletedMovie = deletedMovie;
        this.movies = movies;
        this.printOutput = printOutput;
        this.output = output;
        this.users = users;
    }

    /**
     * <p>
     *     method that verify if the movie of choice exists in the database, it if is
     *     it will be removed from the databases, if not an error will appear
     *     if the user is premium he will receive a free premium movie, if he is standard he will be
     *     given 2 tokens back
     *     informs all users that a movie is removed through Observer Pattern
     */

    @Override
    public void action() {
        boolean existsMovie = false;
        for (MovieCredentials movie : movies) {
            if (movie.getName().compareTo(deletedMovie) == 0) {
                existsMovie = true;
                break;
            }

        }

        if (!existsMovie) {
            printOutput.errorOutput(output);
            return;
        }

        deleteMovie(movies, deletedMovie);

        for (int k = 0; k < users.size(); k++) {
            boolean deleted = false;
            if (users.get(k).getCredentials().getAccountType().compareTo("premium") == 0) {
                users.get(k).setNumFreePremiumMovies(users.get(k).getNumFreePremiumMovies() + 1);
                break;
            } else  {
                users.get(k).setTokens(users.get(k).getTokens() + 2);
            }
            deleteMovie(users.get(k).getCurrentMovies(), deletedMovie);
            if (users.get(k).getPurchasedMovies() != null) {
                for (int i = 0; i < users.get(k).getPurchasedMovies().size(); i++) {
                    if (users.get(k).getPurchasedMovies().
                            get(i).getName().compareTo(deletedMovie) == 0) {
                        deleted = true;
                        deleteMovie(users.get(k).getLikedMovies(), deletedMovie);
                        deleteMovie(users.get(k).getRatedMovies(), deletedMovie);
                        deleteMovie(users.get(k).getWatchedMovies(), deletedMovie);
                    }
                    deleteMovie(users.get(k).getPurchasedMovies(), deletedMovie);
                }
            }

            if (deleted) {
                Notification newNotification = new Notification();
                newNotification.setMovieName(deletedMovie);
                newNotification.setMessage("DELETE");

                NewUpdates updates = new NewUpdates(newNotification);

                for (User user : users) {
                    updates.register(user);
                }

                updates.notifyUsers(newNotification);
            }
        }
    }

    /**
     * <p>
     * method that deletes a movie with a given name from a list of movies
     * @param movies list of movies
     * @param name name of the movie that will be removed
     */

    public void deleteMovie(final ArrayList<MovieCredentials> movies, final String name) {
        if (movies == null) {
            return;
        }
        for (MovieCredentials movie : movies) {
            if (movie.getName().compareTo(name) == 0) {
                movies.remove(movie);
                break;
            }
        }
    }
}
