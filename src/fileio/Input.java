package fileio;

import java.util.ArrayList;

public final class Input {

    private ArrayList<User> users;
    private ArrayList<MovieCredentials> movies;
    private ArrayList<Actions> actions;

    public ArrayList<Actions> getActions() {
        return actions;
    }

    public void setActions(final ArrayList<Actions> actions) {
        this.actions = actions;
    }

    public ArrayList<MovieCredentials> getMovies() {
        return movies;
    }

    public void setMovies(final ArrayList<MovieCredentials> movies) {
        this.movies = movies;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Input{"
                + "users="
                + users
                + ", movies="
                + movies
                + ", actions="
                + actions
                + '}';
    }
}
