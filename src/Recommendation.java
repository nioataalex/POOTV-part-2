import com.fasterxml.jackson.databind.node.ArrayNode;
import observer.NewUpdates;
import fileio.MovieCredentials;
import fileio.Notification;
import fileio.User;
import pages.Output;

import java.util.ArrayList;
import java.util.Comparator;

public final class Recommendation {
    private static Recommendation  instance = null;
    private static int count = 0;
    private Recommendation() {

    }

    /**
     * <p>
     * Singleton for Recommendation class
     */
    public static Recommendation getInstance() {
        if (instance == null) {
            instance = new Recommendation();
            count++;
        }
        return instance;
    }
    public static Recommendation getRecommendation() {
        return getInstance();
    }

    public static int getNumberOfInstances() {
        return count;
    }

    /**
     * <p>
     * method that creates a list of liked genres and sorts them by
     * name and number of likes
     * @param currentUser current User
     */
    public ArrayList<LikedGenres> likedGenres(final User currentUser) {
        ArrayList<LikedGenres> genres = new ArrayList<>();

        if (currentUser.getLikedMovies() != null) {
            for (int i = 0; i < currentUser.getLikedMovies().size(); i++) {
                ArrayList<LikedGenres> helper = new ArrayList<>();
                for (int j = 0; j < currentUser.getLikedMovies().get(i).getGenres().size(); j++) {
                    if (genres.size() == 0) {
                        LikedGenres likedGenres = new LikedGenres();
                        likedGenres.setGenre(currentUser.
                                getLikedMovies().get(i).getGenres().get(j));
                        likedGenres.setLikes(1);
                        genres.add(likedGenres);
                    } else {
                        for (int k = 0; k < genres.size(); k++) {
                            if (genres.get(i).getGenre().compareTo(currentUser.
                                    getLikedMovies().get(i).getGenres().get(j)) == 0) {
                                genres.get(i).setLikes(genres.get(i).getLikes() + 1);
                            } else {
                                LikedGenres likedGenres = new LikedGenres();
                                likedGenres.setGenre(currentUser.
                                        getLikedMovies().get(i).getGenres().get(j));
                                likedGenres.setLikes(1);
                                helper.add(likedGenres);
                            }
                        }
                    }
                }
                for (int x = 0; x < helper.size(); x++) {
                    genres.add(helper.get(x));
                }
            }
        }

        genres.sort((o1, o2) -> o2.getLikes() - o1.getLikes());

        genres.sort(new Comparator<LikedGenres>() {
            @Override
            public int compare(final LikedGenres o1, final LikedGenres o2) {
                return o1.getGenre().compareTo(o2.getGenre());
            }
        });

        return genres;
    }

    /**
     * <p>
     * method that finds the name of the movie that will be recommended
     * @param currentUser current User
     */
    public String recommendedMovie(final User currentUser) {

        ArrayList<LikedGenres> genres = likedGenres(currentUser);

        ArrayList<MovieCredentials> allMovies =  new ArrayList<>();

        if (currentUser.getCurrentMovies() == null) {
            return null;
        }

        for (int i = 0; i < currentUser.getCurrentMovies().size(); i++) {
                allMovies.add(currentUser.getCurrentMovies().get(i));
        }

        allMovies.sort((o1, o2) -> o2.getNumLikes() - o1.getNumLikes());

        for (int i = 0; i < allMovies.size(); i++) {
            for (int j = 0; j < currentUser.getWatchedMovies().size(); j++) {
                if (allMovies.get(i).getName().compareTo(currentUser.
                        getWatchedMovies().get(j).getName()) == 0) {
                    break;
                } else {
                    for (int k = 0; k < genres.size(); k++) {
                        for (int x = 0; x < allMovies.get(i).getGenres().size(); x++) {
                            if (genres.get(k).getGenre().compareTo(allMovies.
                                    get(i).getGenres().get(x)) == 0) {
                                return allMovies.get(i).getName();
                            }
                        }
                    }
                }
            }

        }
        return null;
    }

    /**
     * <p>
     * method where the user will receive a notification with a recommendation
     * if it was found, or suggestive message if not
     * @param currentUser current User
     * @param printOutput Output class that is used for
     *          printing information
     * @param output arrayNode where the answer is exported
     */
    public void action(final User currentUser, final ArrayNode output,
                       final Output printOutput) {
        Notification newNotification =  new Notification();
        newNotification.setMessage("Recommendation");
        String recommendedMovie = recommendedMovie(currentUser);

        if (recommendedMovie(currentUser) == null) {
            newNotification.setMovieName("No recommendation");
        } else {
            newNotification.setMovieName(recommendedMovie);
        }

        NewUpdates updates = new NewUpdates(newNotification);

        updates.register(currentUser);

        updates.notifyUsers(newNotification);
        printOutput.notifyOutput(output, currentUser);
    }

}


