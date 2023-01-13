package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.MovieCredentials;
import fileio.Notification;
import fileio.UserCredentials;
import fileio.User;

import java.util.ArrayList;

public final class Output {
    private ObjectNode node1;

    private ObjectNode node2;

    private ObjectNode node3;

    private ArrayNode arrayNodeList;

    private ArrayNode arrayNode;

    /**
     * <p>
     * output for the errors that will occur
     * @param output arrayNode where the answer is exported
     */
    public void errorOutput(final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        node1 = mapper.createObjectNode();
        node1.put("error", "Error");
        arrayNode = mapper.createArrayNode();
        node1.put("currentMoviesList", arrayNode);
        String nullValue = null;
        node1.put("currentUser", nullValue);
        output.add(node1);
    }

    /**
     * <p>
     * puts in an object node the user's credentials
     * @param credentials user's credentials
     */
    public ObjectNode credentials(final UserCredentials credentials) {
        ObjectMapper mapper = new ObjectMapper();
        node3 = mapper.createObjectNode();
        node3.put("name", credentials.getName());
        node3.put("password", credentials.getPassword());
        node3.put("accountType", credentials.getAccountType());
        node3.put("country", credentials.getCountry());
        node3.put("balance", credentials.getBalance());
        return node3;
    }


    /**
     * <p>
     * output for a new user on the platform
     * also, is used for some errors too
     * @param output arrayNode where the answer is exported
     * @param user current user
     */
    public void newUser(final ArrayNode output, final User user) {
        ObjectMapper mapper = new ObjectMapper();
        node1 = mapper.createObjectNode();
        arrayNode = mapper.createArrayNode();
        node1.put("currentMoviesList", arrayNode);
        node1.put("currentUser", currentUser(user));
        String nullValue = null;
        node1.put("error", nullValue);
        output.add(node1);
    }

    /**
     * <p>
     * puts in an object nodes the credentials of a given movie
     * @param movies movie's credentials
     */
    public ObjectNode movieCredentials(final MovieCredentials movies) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node2 = mapper.createObjectNode();
        node2.put("name", movies.getName());
        node2.put("year", String.valueOf(movies.getYear()));
        node2.put("duration", movies.getDuration());
        ArrayNode arrayNodeList = mapper.createArrayNode();
        for (int j = 0; j < movies.getGenres().size(); j++) {
            arrayNodeList.add(movies.getGenres().get(j));
        }
        node2.put("genres", arrayNodeList);
        arrayNodeList = mapper.createArrayNode();
        for (int j = 0; j < movies.getActors().size(); j++) {
            arrayNodeList.add(movies.getActors().get(j));
        }
        node2.put("actors", arrayNodeList);

        arrayNodeList = mapper.createArrayNode();
        for (int j = 0; j < movies.getCountriesBanned().size(); j++) {
            arrayNodeList.add(movies.getCountriesBanned().get(j));
        }
        node2.put("countriesBanned", arrayNodeList);
        node2.put("numLikes", movies.getNumLikes());
        node2.put("rating", movies.getRating());
        node2.put("numRatings", movies.getNumRatings());
        return node2;

    }

    /**
     * <p>
     * adds in an array node every movie from the list that is given
     * @param movieList list of movies
     */
    public ArrayNode currentMovies(final ArrayList<MovieCredentials> movieList) {
        ObjectMapper mapper = new ObjectMapper();
        arrayNode = mapper.createArrayNode();
        if (movieList == null) {
            return arrayNode;
        }
        if (movieList != null) {
            String ceva = "lala";

            for (MovieCredentials movies : movieList) {
                ObjectNode node3 = mapper.createObjectNode();
                node3 = movieCredentials(movies);
                arrayNode.add(node3);
            }
        }
        return arrayNode;
    }

    /**
     * <p>
     *  adds in an array node every notification from the list that is given
     * @param notifications list of notifications received by a user
     */
    public ArrayNode currentNotifications(final ArrayList<Notification> notifications) {
        ObjectMapper mapper = new ObjectMapper();
        arrayNode = mapper.createArrayNode();
        if (notifications == null) {
            return arrayNode;
        }
        if (notifications != null) {
            for (Notification notification : notifications) {
                ObjectNode node2 = mapper.createObjectNode();
                node2.put("movieName", notification.getMovieName());
                node2.put("message", notification.getMessage());
                arrayNode.add(node2);
            }
        }
        return arrayNode;
    }

    /**
     * <p>
     * puts in an object node user's credentials, tokens,
     * number of free movies, etc.
     * @param user current user
     */
    public  ObjectNode currentUser(final User user) {
        ObjectMapper mapper = new ObjectMapper();
        node2 = mapper.createObjectNode();
        node2.put("credentials", credentials(user.getCredentials()));
        node2.put("tokensCount", user.getTokens());
        node2.put("numFreePremiumMovies", user.getNumFreePremiumMovies());
        arrayNode = mapper.createArrayNode();
        node2.put("purchasedMovies", currentMovies(user.getPurchasedMovies()));
        arrayNode = mapper.createArrayNode();
       node2.put("watchedMovies", currentMovies(user.getWatchedMovies()));
        arrayNode = mapper.createArrayNode();
        node2.put("likedMovies", currentMovies(user.getLikedMovies()));
        arrayNode = mapper.createArrayNode();
        node2.put("ratedMovies", currentMovies(user.getRatedMovies()));
        node2.put("notifications", currentNotifications(user.getNotifications()));
        return node2;
    }

    /**
     * <p>
     * output used to print the user's information at the end of
     * the program
     * @param output arrayNode where the answer is exported
     * @param user current user
     */
    public void notifyOutput(final ArrayNode output, final User user) {
        ObjectMapper mapper = new ObjectMapper();
        node1 = mapper.createObjectNode();
        String nullValue = null;
        arrayNode = mapper.createArrayNode();
        node1.put("currentMoviesList", nullValue);
        node1.put("currentUser", currentUser(user));
        node1.put("error", nullValue);
        output.add(node1);
    }
}
