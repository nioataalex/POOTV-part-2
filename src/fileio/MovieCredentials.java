package fileio;

import java.util.ArrayList;

public final class MovieCredentials {
    private String name;
    private int duration;
    private int year;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private int numLikes;
    private double rating;
    private int numRatings;

    private double sumRatings;

    private double lastRating;

    public double getLastRating() {
        return lastRating;
    }

    public void setLastRating(final double lastRating) {
        this.lastRating = lastRating;
    }

    public double getSumRatings() {
        return sumRatings;
    }

    public void setSumRatings(final double sumRatings) {
        this.sumRatings = sumRatings;
    }

    public MovieCredentials() {
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public MovieCredentials(final MovieCredentials otherMovieCredentials) {
        this.name = new String(otherMovieCredentials.name);
        this.duration = otherMovieCredentials.duration;
        this.actors = new ArrayList<String>(otherMovieCredentials.actors);
        this.genres = new ArrayList<String>(otherMovieCredentials.genres);
        this.year = otherMovieCredentials.year;
        this.countriesBanned = new ArrayList<String>(otherMovieCredentials.countriesBanned);
        this.numLikes = otherMovieCredentials.numLikes;
        this.rating = otherMovieCredentials.rating;
        this.numRatings = otherMovieCredentials.numRatings;
        this.sumRatings = otherMovieCredentials.sumRatings;
    }

    @Override
    public String toString() {
        return "MovieCredentials{"
                + "name='"
                + name
                + '\''
                + ", duration="
                + duration
                + ", year="
                + year
                + ", genres="
                + genres
                + ", actors="
                + actors
                + ", countriesBanned="
                + countriesBanned
                + ", numLikes="
                + numLikes
                + ", rating="
                + rating
                + ", numRatings="
                + numRatings
                + '}';
    }
}
