import java.util.ArrayList;
import java.util.List;

public abstract class Production implements MediaParticipant {
    protected String movieTitle;
    protected List<String> filmDirectors;
    protected List<String> castMembers;
    protected List<Genre> movieGenres;
    protected List<Rating> userRatings;
    protected String storyline;
    protected Double averageMovieRating;

    public Production(String movieTitle, List<String> filmDirectors, List<String> castMembers,
                      List<Genre> movieGenres, List<Rating> userRatings, String storyline,
                      Double averageMovieRating) {
        this.movieTitle = movieTitle;
        this.filmDirectors = filmDirectors;
        this.castMembers = castMembers;
        this.movieGenres = movieGenres;
        this.userRatings = userRatings;
        this.storyline = storyline;
        this.averageMovieRating = averageMovieRating;
    }

    public Production(String movieTitle, List<String> filmDirectors, List<String> castMembers,
                      List<Genre> movieGenres, String storyline) {
        this.movieTitle = movieTitle;
        this.filmDirectors = filmDirectors;
        this.castMembers = castMembers;
        this.movieGenres = movieGenres;
        this.storyline = storyline;
        this.userRatings = new ArrayList<>();
        this.averageMovieRating = 0.0;
    }

    public int compareTo(Production other) {
        return this.movieTitle.compareTo(other.movieTitle);
    }

    public String getTitle() {
        return movieTitle;
    }

    public List<Rating> getUserRatings() {
        return userRatings;
    }

    public abstract void displayMovieInfo();

    public boolean userHasRated(String username) {
        for (Rating rating : userRatings) {
            if (username.equals(rating.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public void removeCastMember(String name) {
        castMembers.remove(name);
    }

    public void addNewRating(Rating rating) {
        userRatings.add(rating);
        recalculateAverageRating();
    }

    private void recalculateAverageRating() {
        double totalScore = 0;
        for (Rating rating : userRatings) {
            totalScore += rating.getScore();
        }
        averageMovieRating = userRatings.isEmpty() ? 0.0 : totalScore / userRatings.size();
    }
}
