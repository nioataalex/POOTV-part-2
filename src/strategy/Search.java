package strategy;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Actions;
import fileio.MovieCredentials;
import fileio.User;
import pages.Output;
import pages.SeeDetails;

import java.util.ArrayList;

public final class Search extends SeeDetails implements Strategy {
    private final Actions action;

    private final User currentUser;

    private final Output printOutput;

    private final ArrayNode output;

    public Search(final Actions action, final User currentUser1,
                  final Output printOutput1, final ArrayNode output1) {
        this.action = action;
        this.currentUser = currentUser1;
        this.printOutput = printOutput1;
        this.output = output1;
    }

    /**
     * <p>
     * method that checks if a movie from the current user's list
     * starts with certain words.
     */
    public void action() {
        String startsWith = action.getStartsWith();
        boolean moviesExists = false;
        ArrayList<MovieCredentials> helper = new ArrayList<>();
        if (currentUser.getCurrentMovies() != null) {
            for (int i = 0; i < currentUser.getCurrentMovies().size(); i++) {
                if (currentUser.getCurrentMovies().get(i).getName().startsWith(startsWith)) {
                    moviesExists = true;
                    MovieCredentials searchedMovie = new MovieCredentials(currentUser.
                            getCurrentMovies().get(i));
                    helper.add(searchedMovie);

                }
            }
            if (!moviesExists) {
                printOutput.newUser(output, currentUser);
            } else {
                print(output, currentUser, helper);
            }
        }
    }

}
