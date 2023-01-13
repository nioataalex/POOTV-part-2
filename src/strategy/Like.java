package strategy;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.MovieCredentials;
import fileio.User;
import pages.Output;
import pages.SeeDetails;

import java.util.ArrayList;

public class Like extends SeeDetails implements Strategy {

    private final User currentUser;

    private final Output printOutput;

    private final ArrayNode output;

    private final String currentPage;

    private final boolean watched;

    public Like(final User currentUser, final Output printOutput, final ArrayNode output,
                final String currentPage, final boolean watched) {
        this.currentUser = currentUser;
        this.printOutput = printOutput;
        this.output = output;
        this.currentPage = currentPage;
        this.watched = watched;
    }

    /**
     * <p>
     * method that checks some corner cases, if something
     * is wrong an error will appear, if not the like
     * action can be made
     */
    @Override
    public void action() {
        if (currentPage.compareTo("watch") != 0 || !watched) {
            printOutput.errorOutput(output);
        } else {
            assert currentUser != null;
            if (currentUser.getCurrentMovies() != null) {
                if (currentUser.getCurrentMovies().size() == 0
                        || currentUser.getSeenMovie() == null) {
                    printOutput.errorOutput(output);
                } else {
                    likeAction();
                }
            }
        }
    }

    /**
     * <p>
     method that changes the number of likes on every list of movies that
     * the current user has
     * @param currentUser current user
     * @param name name of the movie
     */
    public void changesMovies(final User currentUser, final String name) {
        changeNumLikes(currentUser.getPurchasedMovies(), name);
        changeNumLikes(currentUser.getCurrentMovies(), name);
        changeNumLikes(currentUser.getWatchedMovies(), name);
        changeNumLikes(currentUser.getSeenMovie(), name);
        changeNumLikes(currentUser.getRatedMovies(), name);
    }

    /**
     * <p>
     * method where the user likes a movie, which will be added
     * to his liked  movie lists
     * the likes of the movie will increase with 1
     */
    public void likeAction() {
        MovieCredentials likedMovie = new MovieCredentials(currentUser.getSeenMovie().get(0));
        String name = currentUser.getSeenMovie().get(0).getName();
        likedMovie.setNumLikes(likedMovie.getNumLikes() + 1);
        if (currentUser.getWatchedMovies() == null) {
            printOutput.errorOutput(output);
            return;
        }
        if (currentUser.getLikedMovies() != null) {
            for (int i = 0; i < currentUser.getLikedMovies().size(); i++) {
                if (currentUser.getLikedMovies().get(i).
                        getName().compareTo(likedMovie.getName()) == 0) {
                    print(output, currentUser, currentUser.getSeenMovie());
                    return;
                }
            }
            currentUser.getLikedMovies().add(likedMovie);
            currentUser.setLikedMovies(currentUser.getLikedMovies());
        } else {
            ArrayList<MovieCredentials> helper = new ArrayList<>();
            helper.add(likedMovie);
            currentUser.setLikedMovies(helper);
        }
        changesMovies(currentUser, name);
        print(output, currentUser, currentUser.getSeenMovie());

    }

    /**
     * <p>
     * method that changes the number of likes from the movie lists
     * @param name movie name
     * @param movieCredentials given movie list
     */
    public void changeNumLikes(final ArrayList<MovieCredentials> movieCredentials,
                               final String name) {
        if (movieCredentials != null) {
            for (MovieCredentials movieCredential : movieCredentials) {
                if (movieCredential.getName().compareTo(name) == 0) {
                    movieCredential.setNumLikes(movieCredential.getNumLikes() + 1);
                }
            }
        }
    }
}
