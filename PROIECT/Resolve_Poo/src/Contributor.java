import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class Contributor<T extends Comparable<T>> extends Staff<T> implements RequestsManager {
    private List<Request> userRequests;

    // Primary constructor for Contributor
    public Contributor(Information profileInfo, AccountType type, String contributorUsername, int experienceLevel) {
        super(profileInfo, type, contributorUsername, experienceLevel);
        this.userRequests = new ArrayList<>();
    }

    // Extended constructor for Contributor with additional fields
    public Contributor(Information profileInfo, AccountType type, String contributorUsername, int experienceLevel, List<String> notifications, SortedSet<T> favoriteItems, SortedSet<T> contributedItems) {
        super(profileInfo, type, contributorUsername, experienceLevel, notifications, favoriteItems, contributedItems);
        this.userRequests = new ArrayList<>();
    }
    public List<Request> getUserRequests() {
        return userRequests;
    }

    @Override
    public void createRequest(Request newRequest) {
        userRequests.add(newRequest);
    }

    @Override
    public void removeRequest(Request existingRequest) {
        userRequests.remove(existingRequest);
    }

    @Override
    public String toString() {
        return "Contributor{" +
                super.toString() +
                ", userRequests=" + userRequests +
                '}';
    }
}
