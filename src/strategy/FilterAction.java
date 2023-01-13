package strategy;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Actions;
import fileio.MovieCredentials;
import fileio.User;
import pages.Output;
import pages.SeeDetails;

import java.util.ArrayList;
import java.util.Stack;

public class FilterAction extends SeeDetails implements Strategy {

    public FilterAction() {
    }
    public FilterAction(final Actions actions, final User currentUser,
                        final Output output1, final ArrayNode output,
                        final ArrayList<User> users,
                        final Stack<String> succesfulAction, final String currentPage) {
        super(actions, currentUser, output1, output, users, succesfulAction, currentPage);
    }

    /**
     * <p>
     * method that deals with checking some corner cases,
     * gets the given actors, genres, rating and duration
     * from the and calls sort method
     */
    public void action() {
        String rating = "";
        String duration = "";
        ArrayList<String> actors = new ArrayList<>();
        ArrayList<String> genres = new ArrayList<>();
        if (getActions().getFilters().getSort() != null) {
            rating = getActions().getFilters().getSort().getRating();
            duration = getActions().getFilters().getSort().getDuration();
            if (rating == null) {
                rating = "";
            }
            if (duration == null) {
                duration = "";
            }
        }
        if (getActions().getFilters().getContains() != null) {
            if (getActions().getFilters().getContains().getActors() != null) {
                actors.addAll(getActions().getFilters().getContains().getActors());
            }
            if (getActions().getFilters().getContains().getGenre() != null) {
                genres.addAll(getActions().getFilters().getContains().getGenre());
            }
        }
        if (getSuccesfulAction().peek().compareTo("movies") == 0
                || getSuccesfulAction().peek().compareTo("search") == 0
                || getSuccesfulAction().peek().compareTo("see details") == 0) {
            if (getCurrentUser().getCurrentMovies() != null) {
                if (getCurrentUser().getCurrentMovies().size() == 0) {
                    getprintOutput().newUser(getOutput(), getCurrentUser());
                } else {
                    ArrayList<MovieCredentials> helper = new
                            ArrayList<>(getCurrentUser().getCurrentMovies());
                    sorts(helper, rating, duration,
                            actors, genres, getCurrentUser(), getOutput());
                }
            }
        } else {
            getprintOutput().errorOutput(getOutput());
            getSuccesfulAction().pop();
            setSuccesfulAction(getSuccesfulAction());
        }
    }

    /**
     * <p>
     * method that deals with filtering the list of
     * movies according to the desired criteria
     * @param movies list of movies that needs to be filtered
     * @param duration duration used to sort
     * @param rating rating used to sort
     * @param actors list of actors that need to
     *               be contained by the movies
     * @param genres list of genres that need to be
     *               contained by the movies
     * @param currentUser current user
     * @param output arrayNode where the answer is exported
     */
    public void sorts(final ArrayList<MovieCredentials> movies, final  String rating,
                      final String duration, final ArrayList<String> actors,
                     final  ArrayList<String> genres, final User currentUser,
                      final ArrayNode output) {
        boolean contains = false;
        currentUser.setFilterMovie(new ArrayList<>());
        if (actors.size() != 0 && genres.size() != 0) {
            contains = true;
            sortActorsAndGenres(movies,
                    currentUser.getFilterMovies(), actors, genres);
        } else {
            if (actors.size() != 0) {
                contains = true;
                sortActors(movies, currentUser.getFilterMovies(), actors);
            } else if (genres.size() != 0) {
                contains = true;
                sortGenres(movies, currentUser.getFilterMovies(), genres);
            }
        }

        if (currentUser.getFilterMovies().size() == 0 && !contains) {
            currentUser.setFilterMovie(currentUser.getCurrentMovies());
        }


        currentUser.getFilterMovies().sort((o1, o2) -> {
            if (o1.getDuration() == o2.getDuration() || duration.isEmpty()) {
                if (rating.compareTo("increasing") == 0) {
                    return (int) (o1.getRating() - o2.getRating());
                } else {
                    if (o2.getRating() - o1.getRating() < 0) {
                        return -1;
                    }
                    return 1;
                }
            } else if (duration.compareTo("increasing") == 0) {
                return o1.getDuration() - o2.getDuration();
            } else if (duration.compareTo("decreasing") == 0) {
                return o2.getDuration() - o1.getDuration();
            }
            return 0;
        });

        currentUser.setFilterMovie(currentUser.getFilterMovies());
        print(output, currentUser, currentUser.getFilterMovies());

    }

    /**
     * <p>
     * method that helps printing in output and
     * verifies corner cases
     * @param actions action given from the input
     * @param currentUser current user
     * @param output arrayNode where the answer is exported
     * @param output1 output class
     */
    public void seeDetails(final Actions actions,
                           final User currentUser,
                           final Output output1,
                           final ArrayNode output,
    final Stack<String> succesfulAction) {
        String movieName = actions.getMovie();
        boolean movieExist = false;
        MovieCredentials seeMovie;
        ArrayList<MovieCredentials> helper = new ArrayList<>();
        if (currentUser.getFilterMovies().size() != 0) {
            for (int i = 0; i < currentUser.getFilterMovies().size(); i++) {
                if (movieName != null) {
                    if (currentUser.getFilterMovies().
                            get(i).getName().compareTo(movieName) == 0) {
                        movieExist = true;
                        seeMovie = new MovieCredentials(currentUser.getFilterMovies().get(i));
                        helper = new ArrayList<>();
                        helper.add(seeMovie);
                    }
                }
            }
            if (!movieExist) {
                output1.errorOutput(output);

            } else {
                succesfulAction.push(actions.getPage());
                print(output, currentUser, helper);
            }
        }

    }

    /**
     * <p>
     * calls the getActors method
     * @param movies list of movies that needs to be filtered
     * @param sortedMovies array where the sorted movies will
     *                     be stored
     * @param actors list of actors that need to
     *            be contained by the movies
     */
    public void sortActors(final ArrayList<MovieCredentials> movies,
                            final ArrayList<MovieCredentials> sortedMovies,
                            final ArrayList<String> actors) {
        getActors(movies, sortedMovies, actors);
    }

    /**
     * <p>
     * method where the movies that meet the conditions
     * are stored
     * @param movies list of movies that needs to be filtered
     * @param sortedMovies array where the sorted movies will
     *                     be stored
     * @param actors list of actors that need to
     *           be contained by the movies
     */
    private void getActors(final ArrayList<MovieCredentials> movies,
                           final ArrayList<MovieCredentials> sortedMovies,
                           final ArrayList<String> actors) {
        for (MovieCredentials movie : movies) {
            boolean containsActors = false;
            for (int j = 0; j < movie.getActors().size(); j++) {
                for (String actor : actors) {
                    if (movie.getActors().get(j).compareTo(actor) == 0) {
                        containsActors = true;
                        break;
                    }
                }
            }
            if (containsActors) {
                sortedMovies.add(movie);
            }
        }
    }

    /**
     * <p>
     * method where the movies that meet the conditions
     * are stored
     * @param movies list of movies that needs to be filtered
     * @param sortedMovies array where the sorted movies will
     *                     be stored
     * @param genres list of genres that need to
     *          be contained by the movies
     */
    public void sortGenres(final ArrayList<MovieCredentials> movies,
                            final ArrayList<MovieCredentials> sortedMovies,
                            final ArrayList<String> genres) {
        for (MovieCredentials movie : movies) {
            boolean containsGenres = false;
            for (int j = 0; j < movie.getGenres().size(); j++) {
                for (String genre : genres) {
                    if (movie.getGenres().get(j).compareTo(genre) == 0) {
                        containsGenres = true;
                        break;
                    }
                }
            }
            if (containsGenres) {
                sortedMovies.add(movie);
            }
        }
    }

    /**
     * <p>
     * method that stores the movies that have the actors given
     * from the input and then, checks the given genres
     * @param movies list of movies that needs to be filtered
     * @param sortedMovies array where the sorted movies will
     *                     be stored
     * @param actors list of actors that need to
     *             be contained by the movies
     * @param genres list of genres that need to
      *             be contained by the movies
     */
    public void sortActorsAndGenres(final ArrayList<MovieCredentials> movies,
                                    final ArrayList<MovieCredentials> sortedMovies,
                                    final ArrayList<String> actors,
                                    final ArrayList<String> genres) {
        ArrayList<MovieCredentials> checkActor = new ArrayList<>();
        getActors(movies, checkActor, actors);

        for (int i = 0; i < checkActor.size(); i++) {
            boolean containsGenres = false;
            for (int j = 0; j < movies.get(i).getGenres().size(); j++) {
                for (String genre : genres) {
                    if (movies.get(i).getGenres().get(j).compareTo(genre) == 0) {
                        containsGenres = true;
                        break;
                    }
                }
            }
            if (containsGenres) {
                sortedMovies.add(movies.get(i));
            }
        }
    }
}
