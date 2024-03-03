import java.util.List;

public class Movie extends Production implements IProduction {
    private String duration;
    private int releaseYear;

    public Movie(String title, List<String> directors, List<String> actors,
                 List<Genre> genres, List<Rating> ratings, String plot,
                 Double averageRating, String duration, int releaseYear) {
        super(title, directors, actors, genres, ratings, plot, averageRating);
        this.duration = duration;
        this.releaseYear = releaseYear;
    }

    public Movie(String title, List<String> directors, List<String> actors,
                 List<Genre> genres, String plot, String duration, int releaseYear) {
        super(title, directors, actors, genres, plot);
        this.duration = duration;
        this.releaseYear = releaseYear;
    }

    @Override
    public int compareTo(MediaParticipant other) {
        if (other instanceof Movie) {
            Movie otherMovie = (Movie) other;
            return this.getTitle().compareTo(otherMovie.getTitle());
        }
        return this.getClass().getName().compareTo(other.getClass().getName());
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public void displayMovieInfo() {
    }
    @Override
    public String toString() {
        return "{  Movie\n" +
                "  Title : " + movieTitle + "\n" +
                "  Directors : " + filmDirectors.toString() + "\n" +
                "  Actors : " + castMembers.toString() + "\n" +
                "  Genres : " + movieGenres.toString() + "\n" +
                "  Ratings : " + userRatings.toString() + "\n" +
                "  Storyline : " + storyline + ",\n" +
                "  AverageRating : " + averageMovieRating + "\n" +
                "  Duration : " + duration + "\n" +
                "  ReleaseYear : " + releaseYear + "\n" +
                "}";
    }

}
