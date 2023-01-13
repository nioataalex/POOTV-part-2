package fileio;


public final class Actions {
    private String type;
    private String page;
    private String movie;
    private String feature;

    private UserCredentials credentials;
    private String startsWith;
    private String count;
    private int rate;
    private Filter filters;

    private MovieCredentials addedMovie;

    private String deletedMovie;

    private String subscribedGenre;

    public String getSubscribedGenre() {
        return subscribedGenre;
    }

    public MovieCredentials getAddedMovie() {
        return addedMovie;
    }

    public String getDeletedMovie() {
        return deletedMovie;
    }

    public String getCount() {
        return count;
    }


    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }


    public Filter getFilters() {
        return filters;
    }


    public String getPage() {
        return page;
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public int getRate() {
        return rate;
    }


    public String getStartsWith() {
        return startsWith;
    }


    public UserCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final UserCredentials credentials) {
        this.credentials = credentials;
    }

    public String getType() {
        return type;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(final String movie) {
        this.movie = movie;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Actions{"
                + "type='"
                + type
                + '\''
                + ", page='"
                + page
                + '\''
                + ", movie='"
                + movie
                + '\''
                + ", feature='"
                + feature
                + '\''
                + ", credentials="
                + credentials
                + ", startsWith='"
                + startsWith
                + '\''
                + ", count='"
                + count
                + '\''
                + ", rate="
                + rate
                + ", filters="
                + filters
                + '}';
    }
}
