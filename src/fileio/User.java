package fileio;

import observer.Observer;

import java.util.ArrayList;

public final class User implements Observer {
    private UserCredentials credentials;

    private ArrayList<MovieCredentials> currentMovies;

    private int tokens;

    private int numFreePremiumMovies;

    private ArrayList<MovieCredentials> watchedMovies;

    private ArrayList<MovieCredentials> likedMovies;

    private ArrayList<MovieCredentials> ratedMovies;

    private ArrayList<MovieCredentials> seenMovie;

    private ArrayList<MovieCredentials> containsMovie;

    private ArrayList<Notification> notifications;

    private ArrayList<String> subscribedGenres;

    private int alreadyEmpty;

    public int getAlreadyEmpty() {
        return alreadyEmpty;
    }

    public void setAlreadyEmpty(final int alreadyEmpty) {
        this.alreadyEmpty = alreadyEmpty;
    }

    public ArrayList<String> getSubscribedGenres() {
        return subscribedGenres;
    }

    public void setSubscribedGenres(final ArrayList<String> subscribedGneres) {
        this.subscribedGenres = subscribedGneres;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(final ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<MovieCredentials> getFilterMovies() {
        return containsMovie;
    }

    public void setFilterMovie(final ArrayList<MovieCredentials> containsMovie) {
        this.containsMovie = containsMovie;
    }

    public ArrayList<MovieCredentials> getCurrentMovies() {
        return currentMovies;
    }

    public void setCurrentMovies(final ArrayList<MovieCredentials> currentMovies) {
        this.currentMovies = currentMovies;
    }

    public ArrayList<MovieCredentials> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(final ArrayList<MovieCredentials> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<MovieCredentials> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(final ArrayList<MovieCredentials> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<MovieCredentials> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<MovieCredentials> ratedmovies) {
        this.ratedMovies = ratedmovies;
    }

    public ArrayList<MovieCredentials> getSeenMovie() {
        return seenMovie;
    }

    public void setSeenMovie(final ArrayList<MovieCredentials> seenMovie) {
        this.seenMovie = seenMovie;
    }

    public ArrayList<MovieCredentials> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(final ArrayList<MovieCredentials> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }


    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    private ArrayList<MovieCredentials> purchasedMovies;

    public UserCredentials getCredentials() {
        return credentials;
    }


    public void setCredentials(final UserCredentials credentials) {
        this.credentials = credentials;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(final int tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return "User{"
                + "credentials="
                + credentials
                + ", currentMovies="
                + currentMovies
                + ", tokens="
                + tokens
                + ", numFreePremiumMovies="
                + numFreePremiumMovies
                + ", watchedMovies="
                + watchedMovies
                + ", likedMovies="
                + likedMovies
                + ", ratedMovies="
                + ratedMovies
                + ", seenMovie="
                + seenMovie
                + ", containsMovie="
                + containsMovie
                + ", purchasedMovies="
                + purchasedMovies
                + '}';
    }

    @Override
    public void update(final Notification newNotification) {
        if (getNotifications() == null) {
            ArrayList<Notification> notifications = new ArrayList<>();
            notifications.add(newNotification);
            setNotifications(notifications);
        } else {
            getNotifications().add(newNotification);
            setNotifications(getNotifications());
        }
    }
}
