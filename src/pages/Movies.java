package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.MovieCredentials;
import fileio.User;


import java.util.ArrayList;

public final class Movies {

    private static Movies instance = null;
    private static int count = 0;
    private Movies() {

    }

    /**
     * <p>
     * Singleton for Movies class
     */
    public static Movies getInstance() {
        if (instance == null) {
            instance = new Movies();
            count++;
        }
        return instance;
    }
    public static Movies getMovies() {
        return getInstance();
    }

    public static int getNumberOfInstances() {
        return count;
    }

    /**
     * <p>
     * method that checks different cornes cases and prints
     * the movies that meets the necessary conditions
     * or errors if the conditions are not fulfilled
     * @param movies list of movies from the platform
     * @param currentUser current user
     * @param printOutput Output class that is used for
     *                    printing information
     * @param output arrayNode where the answer is exported
     */

    public void action(final User currentUser,
                       final ArrayList<MovieCredentials> movies,
                       final Output printOutput, final ArrayNode output) {
        ArrayList<MovieCredentials> movieList = new ArrayList<>();
        if (currentUser != null) {
            if (currentUser.getCredentials() != null) {
                movieList(movies, movieList, currentUser);
                currentUser.setCurrentMovies(movieList);
                outputMovies(output, currentUser);
            } else {
                printOutput.errorOutput(output);
            }
        }
    }


    /**
     * <p>
     * method where is checked if the movies are banned
     * from the current user's country
     * @param movieList array where the movies which are not banned
     *                  from the country are added
     * @param movies list of movies from the platform
     * @param currentUser current user
     */

    public void movieList(final ArrayList<MovieCredentials> movies,
                       final ArrayList<MovieCredentials> movieList,
                       final User currentUser) {
        for (MovieCredentials movie : movies) {
            boolean isBanned = false;
            for (int j = 0; j < movie.getCountriesBanned().size(); j++) {
                if (movie.getCountriesBanned().
                        get(j).compareTo(currentUser.
                                getCredentials().getCountry()) == 0) {
                    isBanned = true;
                    break;
                }
            }
            if (!isBanned) {
                movieList.add(movie);
            }
        }
    }

    /**
     * <p>
     * prints the current list of movies and credentials of the user
     * @param output arrayNode where the answer is exported
     * @param user current user
     */
    public void outputMovies(final ArrayNode output, final User user) {
        Output printOutput = new Output();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node1 = mapper.createObjectNode();
        node1.put("currentMoviesList", printOutput.currentMovies(user.getCurrentMovies()));
        SeeDetails.outputHelper(output, user, printOutput, mapper, node1);
    }
}
