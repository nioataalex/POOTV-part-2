package factory;

import com.fasterxml.jackson.databind.node.ArrayNode;

import fileio.MovieCredentials;
import fileio.User;
import pages.Output;

import java.util.ArrayList;

public class GetChangesFactory {
    private final ArrayList<MovieCredentials> movies;
    private  MovieCredentials addedMovie;
    private  String deletedMovie;
    private final Output printOutput;
    private final ArrayNode output;

    private final ArrayList<User> users;

    public GetChangesFactory(final ArrayList<MovieCredentials> movies,
                             final MovieCredentials addedMovie,
                             final Output printOutput, final ArrayNode output,
                             final ArrayList<User> users) {
        this.movies = movies;
        this.addedMovie = addedMovie;
        this.printOutput = printOutput;
        this.output = output;
        this.users = users;
    }

    public GetChangesFactory(final ArrayList<MovieCredentials> movies,
                             final String deletedMovie,
                             final Output printOutput, final ArrayNode output,
                             final ArrayList<User> users) {
        this.movies = movies;
        this.deletedMovie = deletedMovie;
        this.printOutput = printOutput;
        this.output = output;
        this.users = users;
    }


    /**
     * <p>
     * method that decides what type of action will be made on the database
     * of movies
     * Factory Pattern
     * @param type the type of action add or remove
     */

    public DatabaseFactory createDatabase(final String type) {
        if (type.equals("add")) {
            return new AddChange(movies, addedMovie, printOutput, output, users);
        } else if (type.equals("delete")) {
            return new DeleteChange(movies, deletedMovie, users, printOutput, output);
        }
        throw new IllegalArgumentException("The action type " + type + " is not recognized.");
    }
}
