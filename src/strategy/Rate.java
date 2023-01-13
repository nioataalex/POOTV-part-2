package strategy;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Actions;
import fileio.MovieCredentials;
import fileio.User;
import pages.Output;
import pages.SeeDetails;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class Rate extends SeeDetails implements Strategy {

    private final int five = 5;
    private final Actions actions;
    private final User currentUser;

    private final Output printOutput;

    private final Stack<String> succesfulAction;
    private final ArrayList<User> allUsers;

    private final ArrayNode output;

    private final boolean watched;

    public Rate(final Actions actions, final User currentUser,
                final Output printOutput, final ArrayNode output,
                final ArrayList<User> allUsers,
                final Stack<String> succesfulAction,
                final boolean watched) {
        this.actions = actions;
        this.currentUser = currentUser;
        this.printOutput = printOutput;
        this.output = output;
        this.allUsers = allUsers;
        this.succesfulAction = succesfulAction;
        this.watched = watched;
    }

    /**
     * <p>

     */
    @Override
    public void action() {
        if (Objects.requireNonNull(succesfulAction).peek().compareTo("see details") != 0
                || !watched) {
            printOutput.errorOutput(output);
        } else {
            if (Objects.requireNonNull(currentUser).getCurrentMovies().size() == 0
                    || currentUser.getSeenMovie() == null) {
                printOutput.errorOutput(output);
            } else {
                rateAction();
            }
        }
    }

    /**
     * <p>

     */
    public void changesMovies(final User currentUser, final String name,
                              final double newRate, final boolean sameNumRated) {
        changeRated(currentUser.getRatedMovies(), name, newRate, sameNumRated);
        changeRated(currentUser.getLikedMovies(), name, newRate, sameNumRated);
        changeRated(currentUser.getPurchasedMovies(), name, newRate, sameNumRated);
        changeRated(currentUser.getWatchedMovies(), name, newRate, sameNumRated);
        changeRated(currentUser.getCurrentMovies(), name, newRate, sameNumRated);
    }

    /**
     * <p>
     * method where the user rates a movie, which will be added
     * to his rated movie lists
     * the rate need to be less than 5, although an error will
     * occur
     */
    public void rateAction() {
        double rate = actions.getRate();
        boolean sameNumRated = false;
        if (rate > five) {
            printOutput.errorOutput(output);
        } else {
            MovieCredentials ratedMovie = new MovieCredentials(currentUser.getSeenMovie().get(0));
            ratedMovie.setLastRating(rate);
            String name = currentUser.getSeenMovie().get(0).getName();
            if (currentUser.getRatedMovies() != null) {
                for (int i = 0; i < currentUser.getRatedMovies().size(); i++) {
                    if (currentUser.getRatedMovies().get(i).
                            getName().compareTo(ratedMovie.getName()) == 0) {
                        double newRate = rate - currentUser.getRatedMovies().get(i).getLastRating();
                        sameNumRated = true;
                        changesMovies(currentUser, name, newRate, sameNumRated);
                        changeRated(currentUser.getSeenMovie(), name, newRate, sameNumRated);
                        print(output, currentUser, currentUser.getSeenMovie());
                        return;
                    }
                }
                currentUser.getRatedMovies().add(ratedMovie);
                currentUser.setRatedMovies(currentUser.getRatedMovies());
            } else {
                ArrayList<MovieCredentials> helper = new ArrayList<>();
                helper.add(ratedMovie);
                currentUser.setRatedMovies(helper);
            }

            for (User allUser : allUsers) {
                if (allUser.getCredentials().
                        getName().compareTo(currentUser.getCredentials().getName()) != 0) {
                    if (allUser.getRatedMovies() != null) {
                        changeRated(allUser.getRatedMovies(), name, rate, sameNumRated);
                        changeRated(allUser.getLikedMovies(), name, rate, sameNumRated);
                        changeRated(allUser.getPurchasedMovies(), name, rate, sameNumRated);
                        changeRated(allUser.getWatchedMovies(), name, rate, sameNumRated);
                    }
                }
            }

            changesMovies(currentUser, name, rate, sameNumRated);
            changeRated(currentUser.getSeenMovie(), name, rate, sameNumRated);

            print(output, currentUser, currentUser.getSeenMovie());
        }
    }

    /**
     * <p>
     * method that changes the number of rating of the movies
     *  and changes the rate, doing the arithmetic mean with
     *  all the given rates by that moment
     * @param movieCredentials given movie list
     * @param name movie name
     * @param rate rate that will be added
     */
    public void changeRated(final ArrayList<MovieCredentials> movieCredentials,
                            final String name, final double rate, final boolean sameNumRated) {
        if (movieCredentials != null) {
            for (MovieCredentials movieCredential : movieCredentials) {
                if (movieCredential.getName().compareTo(name) == 0) {
                    if (!sameNumRated) {
                        movieCredential.setNumRatings(movieCredential.getNumRatings() + 1);
                    }
                    movieCredential.setSumRatings(movieCredential.getSumRatings() + rate);
                    movieCredential.setRating(movieCredential.getSumRatings()
                            / movieCredential.getNumRatings());
                }
            }
        }
    }
}
