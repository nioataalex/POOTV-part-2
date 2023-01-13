package strategy;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.MovieCredentials;
import fileio.User;
import pages.Output;
import pages.SeeDetails;

import java.util.ArrayList;

public class Purchase extends SeeDetails implements Strategy {

    private final User currentUser;

    private final Output printOutput;

    private final ArrayNode output;

    public Purchase(final User currentUser, final Output printOutput,
                    final ArrayNode output) {
        this.currentUser = currentUser;
        this.printOutput = printOutput;
        this.output = output;
    }

    /**
     * <p>

     */
    @Override
    public void action() {
        if (currentUser.getCurrentMovies() != null) {
            if (currentUser.getCurrentMovies().size() == 0
                    || currentUser.getSeenMovie() == null) {
                printOutput.errorOutput(output);
            } else if (currentUser.getTokens() <= 0
                    && currentUser.getCredentials().getAccountType().compareTo("standard") == 0)  {
                printOutput.errorOutput(output);
            } else {
               purchaseAction();
            }
        }
    }

    /**
     * <p>
     * method where the user purchase a movie, which will be added
     * to his puchased movie lists
     * also the movie costs 2 tokens, if the user is premium he has
     * a number of 15 movies that are free
     */
    public void purchaseAction() {
        MovieCredentials purchasedMovie = new MovieCredentials(currentUser.getSeenMovie().get(0));
        if (currentUser.getCredentials().getAccountType().compareTo("standard") == 0) {
            currentUser.setTokens(currentUser.getTokens() - 2);
        } else {
            if (currentUser.getNumFreePremiumMovies() > 0) {
                currentUser.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies() - 1);
            } else {
                currentUser.setTokens(currentUser.getTokens() - 2);
            }
        }
        if (currentUser.getPurchasedMovies() != null) {
            for (int i = 0; i < currentUser.getPurchasedMovies().size(); i++) {
                if (currentUser.getPurchasedMovies().get(i).
                        getName().compareTo(purchasedMovie.getName()) == 0) {
                    currentUser.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies() + 1);
                    printOutput.errorOutput(output);
                    return;
                }
            }
            currentUser.getPurchasedMovies().add(purchasedMovie);
            currentUser.setPurchasedMovies(currentUser.getPurchasedMovies());
        } else {
            ArrayList<MovieCredentials> helper = new ArrayList<>();
            helper.add(purchasedMovie);
            currentUser.setPurchasedMovies(helper);
        }
        print(output, currentUser, currentUser.getSeenMovie());
    }


}
