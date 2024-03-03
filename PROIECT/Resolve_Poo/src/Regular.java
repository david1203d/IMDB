import java.util.*;

public class Regular<T extends Comparable<T>> extends User<T> implements RequestsManager {
    private List<Request> userRequests;
    private Map<Production, Rating> userRatings;
    private Set<Production> removedProductions;

    public Regular(Information info, AccountType accountType,
                       String username, int experience) {
        this(info, accountType, username, experience, new ArrayList<>(), new TreeSet<>());
    }

    public Regular(Information info, AccountType accountType,
                       String username, int experience,
                       List<String> notifications, SortedSet<T> favorites) {
        super(info, accountType, username, experience, notifications, favorites);
        this.userRequests = new ArrayList<>();
        this.userRatings = new TreeMap<>();
        this.removedProductions = new TreeSet<>();
    }

    public List<Request> getUserRequests() {
        return userRequests;
    }

    public Map<Production, Rating> getUserRatings() {
        return userRatings;
    }

    public Set<Production> getRemovedProductions() {
        return removedProductions;
    }

    @Override
    public void createRequest(Request request) {
        userRequests.add(request);
    }

    @Override
    public void removeRequest(Request request) {
        userRequests.remove(request);
    }

    public void addReviewToProduction(Rating rating, Production production) {
        production.addNewRating(rating);
    }
    public void removeProductionAndRelatedData(Production production) {
        if (userRatings.containsKey(production)) {
            userRatings.remove(production);
        }
        removedProductions.remove(production);
    }

    @Override
    public String toString() {
        return "RegularUser { \n" +
                super.toString() +
                "   userRequests = " + userRequests + "\n" +
                '}';
    }
}
