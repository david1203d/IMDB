public class Rating {
    private String username;
    private int score;
    private String comment;

    public Rating(String username, int score, String comment) {
        setUsername(username);
        setScore(score);
        setComment(comment);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if (score < 0 || score > 10) {
            throw new IllegalArgumentException("Score must be between 0 and 10");
        }
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Rating { " +
                "username = '" + username + '\'' +
                ", score = " + score +
                ", comment = '" + comment + '\'' +
                '}';
    }
}
