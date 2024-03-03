public enum RequestType {
    DELETE_ACCOUNT,
    ACTOR_ISSUE,
    MOVIE_ISSUE,
    OTHERS,
    UNKNOWN;
    public static RequestType fromString(String value) {
        switch (value) {
            case "DELETE_ACCOUNT":
                return DELETE_ACCOUNT;
            case "ACTOR_ISSUE":
                return ACTOR_ISSUE;
            case "MOVIE_ISSUE":
                return MOVIE_ISSUE;
            case "OTHERS":
                return OTHERS;
            default:
                System.out.println("Request UNKNOWN");
                return UNKNOWN;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case DELETE_ACCOUNT:
                return "DELETE_ACCOUNT";
            case ACTOR_ISSUE:
                return "ACTOR_ISSUE";
            case MOVIE_ISSUE:
                return "MOVIE_ISSUE";
            case OTHERS:
                return "OTHERS";
            default:
                return "UNKNOWN request";
        }
    }
}
