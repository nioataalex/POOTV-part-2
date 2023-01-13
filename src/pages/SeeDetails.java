package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Actions;
import fileio.MovieCredentials;
import fileio.User;
import strategy.FilterAction;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class SeeDetails {

    private Actions actions;

    private ArrayList<User> allUsers;
    private User currentUser;
    private Output printOutput;
    private ArrayNode output;
    private Stack<String> succesfulAction;

    private String currentPage;


    /**
     * <p>
     */
    public Actions getActions() {
        return actions;
    }

    /**
     * <p>
     */
    public void setActions(final Actions actions) {
        this.actions = actions;
    }

    /**
     * <p>
     */
    public User getCurrentUser() {
        return currentUser;
    }


    /**
     * <p>
     */
    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * <p>
     */
    public Output getprintOutput() {
        return printOutput;
    }

    /**
     * <p>
     */
    public ArrayNode getOutput() {
        return output;
    }

    /**
     * <p>
     */
    public void setOutput(final ArrayNode output) {
        this.output = output;
    }

    /**
     * <p>
     */
    public Stack<String> getSuccesfulAction() {
        return succesfulAction;
    }

    /**
     * <p>
     */
    public void setSuccesfulAction(final Stack<String> succesfulAction) {
        this.succesfulAction = succesfulAction;
    }

    public SeeDetails() {
    }


    public SeeDetails(final Actions actions, final User currentUser,
                      final Output printOutput, final ArrayNode output,
                      final ArrayList<User> allUsers,
                      final Stack<String> succesfulAction,
                      final String currentPage) {
        this.actions = actions;
        this.currentUser = currentUser;
        this.printOutput = printOutput;
        this.output = output;
        this.allUsers = allUsers;
        this.succesfulAction = succesfulAction;
        this.currentPage = currentPage;
    }

    /**
     * <p>
     *  method that checks different cornes cases and prints
     *  the movie that is given from the input
     *  or errors if something is wrong
     */
    public void action() {
        if (currentUser.getSeenMovie() != null) {
            currentUser.setSeenMovie(null);
        }
        if (currentPage.compareTo("filter") == 0) {
            if (currentUser.getFilterMovies() != null
                    && currentUser.getFilterMovies().size() != 0) {
                FilterAction filterAction = new FilterAction();
                filterAction.seeDetails(actions, currentUser, printOutput, output, succesfulAction);
            } else {
                printOutput.errorOutput(output);
            }

        } else {
            if (currentUser.getCurrentMovies() != null) {
                if (currentUser.getCurrentMovies().size() == 0
                        || Objects.requireNonNull(succesfulAction).isEmpty()) {
                    printOutput.errorOutput(output);
                } else {
                    seeDetails();
                }
            }
        }
    }

    /**
     * <p>
     * method that checks some corner cases, find the movie that
     * is given from the input, and prints it
     */
    public void seeDetails() {
        String movieName = actions.getMovie();
        boolean movieExist = false;
        MovieCredentials seeMovie;

        if (succesfulAction.peek().compareTo("movies")  != 0) {
            printOutput.errorOutput(output);
            return;
        }
        for (int i = 0; i < currentUser.getCurrentMovies().size(); i++) {
            if (movieName != null) {
                if (currentUser.getCurrentMovies().get(i).getName().compareTo(movieName) == 0) {

                    movieExist = true;
                    seeMovie = new MovieCredentials(currentUser.getCurrentMovies().get(i));
                    ArrayList<MovieCredentials> helper = new ArrayList<>();
                    helper.add(seeMovie);
                    currentUser.setSeenMovie(helper);
                }
            }
        }
        if (!movieExist) {
            printOutput.errorOutput(output);
        } else {
            print(output, currentUser, currentUser.getSeenMovie());
            succesfulAction.push(actions.getPage());
        }

    }



    /**
     * <p>
     * method that prints the current seen
     * movie and the details about the user
     * @param output arrayNode where the answer is exported
     * @param currentUser current user
     * @param movieList list that contains the seen movie
     */
    public void print(final ArrayNode output, final User currentUser,
                      final ArrayList<MovieCredentials> movieList) {
        Output output2 = new Output();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node1 = mapper.createObjectNode();
        node1.put("currentMoviesList", output2.currentMovies(movieList));
        outputHelper(output, currentUser, output2, mapper, node1);
    }

    /**
     * <p>
     * method that helps printing the current user's credentials
     * @param output arrayNode where the answer is exported
     * @param currentUser current user
     * @param output2 output class
     * @param mapper object mapper
     * @param node1 object node
     */
    static void outputHelper(final ArrayNode output, final User currentUser,
                             final Output output2, final ObjectMapper mapper,
                             final ObjectNode node1) {
        ObjectNode node2 = mapper.createObjectNode();
        node2.put("credentials", output2.credentials(currentUser.getCredentials()));
        node2.put("tokensCount", currentUser.getTokens());
        node2.put("numFreePremiumMovies", currentUser.getNumFreePremiumMovies());
        node2.put("purchasedMovies", output2.currentMovies(currentUser.getPurchasedMovies()));
        node2.put("watchedMovies", output2.currentMovies(currentUser.getWatchedMovies()));
        node2.put("likedMovies", output2.currentMovies(currentUser.getLikedMovies()));
        node2.put("ratedMovies", output2.currentMovies(currentUser.getRatedMovies()));
        node2.put("notifications", output2.currentNotifications(currentUser.getNotifications()));
        node1.put("currentUser", node2);
        String nullValue = null;
        node1.put("error", nullValue);
        output.add(node1);
    }


}
