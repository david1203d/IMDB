import java.util.List;
import java.util.Map;

public class Series extends Production implements IProduction {
    private int releaseYear;
    private int numberOfSeasons;
    private Map<String, List<Episode>> episodesBySeason;
    public Series(String title, List<String> directors, List<String> actors, List<Genre> genres,
                  List<Rating> ratings, String storyline, Double averageRating,
                  int releaseYear, int numberOfSeasons, Map<String, List<Episode>> episodesBySeason) {
        super(title, directors, actors, genres, ratings, storyline, averageRating);
        this.releaseYear = releaseYear;
        this.numberOfSeasons = numberOfSeasons;
        this.episodesBySeason = episodesBySeason;
    }

    public Series(String title, List<String> directors, List<String> actors, List<Genre> genres,
                  String storyline, int releaseYear, int numberOfSeasons,
                  Map<String, List<Episode>> episodesBySeason) {
        super(title, directors, actors, genres, storyline);
        this.releaseYear = releaseYear;
        this.numberOfSeasons = numberOfSeasons;
        this.episodesBySeason = episodesBySeason;
    }

    @Override
    public int compareTo(MediaParticipant other) {
        if (other instanceof Series) {
            Series otherSeries = (Series) other;
            return this.getTitle().compareTo(otherSeries.getTitle());
        }
        return this.getClass().getName().compareTo(other.getClass().getName());
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public Map<String, List<Episode>> getEpisodesBySeason() {
        return episodesBySeason;
    }

    public void setEpisodesBySeason(Map<String, List<Episode>> episodesBySeason) {
        this.episodesBySeason = episodesBySeason;
    }

    @Override
    public void displayMovieInfo() {
    }

    @Override
    public String toString() {
        return "{  Series\n" +
                "  Title : " + movieTitle + "\n" +
                "  Directors : " + filmDirectors.toString() + "\n" +
                "  Actors : " + castMembers.toString() + "\n" +
                "  Genres : " + movieGenres.toString() + "\n" +
                "  Ratings : " + userRatings.toString() + "\n" +
                "  Storyline : " + storyline + ",\n" +
                " AverageRating : " + averageMovieRating + "\n" +
                "  ReleaseYear : " + releaseYear + "\n" +
                "  NumSeasons : " + numberOfSeasons + "\n" +
                "  Seasons : " + episodesBySeason.toString() + "\n" +
                "}";
    }
}
