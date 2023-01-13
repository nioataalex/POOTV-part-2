package strategy;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.MovieCredentials;
import fileio.User;
import pages.Output;
import pages.SeeDetails;

import java.util.ArrayList;

public class Watch extends SeeDetails implements Strategy {

    private final User currentUser;

    private final Output printOutput;

    private final ArrayNode output;

    public Watch(final User currentUser, final Output printOutput,
                 final ArrayNode output) {
        this.currentUser = currentUser;
        this.printOutput = printOutput;
        this.output = output;
    }


    /**
     * <p>
     * method where the user watched a movie, which will be added
     * to his watched movie lists
     */
    public void action() {
        MovieCredentials watchedMovie = new MovieCredentials(currentUser.getSeenMovie().get(0));
        if (currentUser.getPurchasedMovies() == null) {
            printOutput.errorOutput(output);
            return;
        }
        if (currentUser.getWatchedMovies() != null) {
            for (int i = 0; i < currentUser.getWatchedMovies().size(); i++) {
                if (currentUser.getWatchedMovies().get(i).
                        getName().compareTo(watchedMovie.getName()) == 0) {
                    print(output, currentUser, currentUser.getSeenMovie());
                    return;
                }
            }
            currentUser.getWatchedMovies().add(watchedMovie);
            currentUser.setWatchedMovies(currentUser.getWatchedMovies());
        } else {
            ArrayList<MovieCredentials> helper = new ArrayList<>();
            helper.add(watchedMovie);
            currentUser.setWatchedMovies(helper);
        }
        print(output, currentUser, currentUser.getSeenMovie());
    }
}
