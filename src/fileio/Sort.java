package fileio;

public final class Sort {

    private String rating;
    private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
        this.duration = duration;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(final String rating) {
        this.rating = rating;
    }

    public Sort() {
    }

    @Override
    public String toString() {
        return "Sort{"
                + "rating='"
                + rating
                + '\''
                + ", duration='"
                + duration
                + '\''
                + '}';
    }
}
