public enum AccountType {
    REGULAR,
    CONTRIBUTOR,
    ADMIN,
    UNKNOWN;

    public static AccountType fromString(String value) {
        switch (value) {
            case "Regular":
                return REGULAR;
            case "Contributor":
                return CONTRIBUTOR;
            case "Admin":
                return ADMIN;
            default:
                System.out.println("Warning: Unknown AccountType - " + value);
                return UNKNOWN;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case REGULAR:
                return "Regular";
            case CONTRIBUTOR:
                return "Contributor";
            case ADMIN:
                return "Admin";
            default:
                return "Unknown";
        }
    }
}
