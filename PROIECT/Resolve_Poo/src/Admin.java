import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class Admin<T extends Comparable<T>> extends Staff<T> {
    private List<User<T>> systemUsers;

    public Admin(Information profileInfo, AccountType type, String adminUsername, int adminExperience) {
        super(profileInfo, type, adminUsername, adminExperience);
        this.systemUsers = new ArrayList<>();
    }

    public Admin(Information profileInfo, AccountType type, String adminUsername, int adminExperience, List<String> adminNotifications, SortedSet<T> favoriteItems, SortedSet<T> contributions) {
        super(profileInfo, type, adminUsername, adminExperience, adminNotifications, favoriteItems, contributions);
        this.systemUsers = new ArrayList<>();
    }

    public void registerUser(User<T> newUser) {
        systemUsers.add(newUser);
    }

    public void deregisterUser(User<T> existingUser) {
        systemUsers.remove(existingUser);
    }

    @Override
    public void deleteProduction(Production production) {
        super.deleteProduction(production);
    }

    @Override
    public void deleteActor(Actor actor) {
        super.deleteActor(actor);
    }

    public static class RequestHolder {
        private static List<Request> allRequests = new ArrayList<>();

        public static void submitRequest(Request newRequest) {
            allRequests.add(newRequest);
        }

        public static void resolveRequest(Request existingRequest) {
            allRequests.remove(existingRequest);
        }

        public static List<Request> listAllRequests() {
            return new ArrayList<>(allRequests);
        }
    }

    public void processNewRequest(Request newRequest) {
        RequestHolder.submitRequest(newRequest);
    }

    public void processResolvedRequest(Request resolvedRequest) {
        RequestHolder.resolveRequest(resolvedRequest);
    }

    @Override
    public String toString() {
        return "Admin { " +
                super.toString() +
                ", systemUsers = " + systemUsers +
                " }";
    }
}
