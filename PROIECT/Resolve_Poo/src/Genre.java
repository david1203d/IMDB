public enum Genre {
    ACTION,
    ADVENTURE,
    COMEDY,
    DRAMA,
    HORROR,
    SF,
    FANTASY,
    ROMANCE,
    MYSTERY,
    THRILLER,
    CRIME,
    BIOGRAPHY,
    WAR,
    COOKING,
    UNKNOWN;


    public static Genre fromString(String value) {
        switch (value) {
            case "Action":
                return ACTION;
            case "Adventure":
                return ADVENTURE;
            case "Comedy":
                return COMEDY;
            case "Drama":
                return DRAMA;
            case "Horror":
                return HORROR;
            case "SF":
                return SF;
            case "Fantasy":
                return FANTASY;
            case "Romance":
                return ROMANCE;
            case "Mystery":
                return MYSTERY;
            case "Thriller":
                return THRILLER;
            case "Crime":
                return CRIME;
            case "Biography":
                return BIOGRAPHY;
            case "War":
                return WAR;
            case "Cooking":
                return COOKING;
            default:
                System.out.println("Warning: Unmapped JSON value for Genre - " + value);
                return UNKNOWN;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case ACTION:
                return "Action";
            case ADVENTURE:
                return "Adventure";
            case COMEDY:
                return "Comedy";
            case DRAMA:
                return "Drama";
            case HORROR:
                return "Horror";
            case SF:
                return "SF";
            case FANTASY:
                return "Fantasy";
            case ROMANCE:
                return "Romance";
            case MYSTERY:
                return "Mystery";
            case THRILLER:
                return "Thriller";
            case CRIME:
                return "Crime";
            case BIOGRAPHY:
                return "Biography";
            case WAR:
                return "War";
            case COOKING:
                return "Cooking";
            default:
                return "UNKNOWN";
        }
    }

}