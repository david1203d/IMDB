public class Episode {
    private String episodeName;
    private String duration;

    public Episode(String episodeName, String duration) {
        this.episodeName = episodeName;
        this.duration = duration;
    }

    public String getName() {
        return episodeName;
    }

    public void setName(String episodeName) {
        this.episodeName = episodeName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "\n{\n" +
                "  EpisodeName : " + episodeName + ",\n" +
                "  Duration : " + duration + "\n" +
                "}";
    }

}
