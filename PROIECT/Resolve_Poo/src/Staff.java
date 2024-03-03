import java.util.*;

public abstract class Staff<T extends Comparable<T>> extends User<T> implements StaffInterface {
    protected List<Request> requestsFromUsers;
    protected SortedSet<T> contributedMediaParticipant;

    public Staff(Information info, AccountType accountType, String username, int experience) {
        super(info, accountType, username, experience);
        this.contributedMediaParticipant = new TreeSet<>();
        this.requestsFromUsers = new ArrayList<>();
    }

    public Staff(Information info, AccountType accountType, String username, int experience, List<String> notifications, SortedSet<T> favorites, SortedSet<T> contributedMediaParticipant) {
        super(info, accountType, username, experience, notifications, favorites);
        this.requestsFromUsers = new ArrayList<>();
        this.contributedMediaParticipant = contributedMediaParticipant;
    }

    public List<Request> getListOfRequestsFromUsers() {
        return requestsFromUsers;
    }

    public Request getRequestFromUser(int index) {
        return requestsFromUsers.get(index);
    }

    public void printListOfRequest() {
        for (Request request : requestsFromUsers) {
            System.out.println(request);
        }
    }

    public SortedSet<T> getContributedMediaParticipant() {
        return contributedMediaParticipant;
    }

    public int printProductionsAdded(int index) {
        for (T item : contributedMediaParticipant) {
            if (item instanceof MediaParticipant) {
                MediaParticipant participant = (MediaParticipant) item;
                if (participant instanceof Movie || participant instanceof Series) {
                    System.out.println(index + ")  " + ((Production) participant).getTitle());
                    index++;
                }
            }
        }
        return index;
    }

    public Production getIndexProductionContributed(int index) {
        int i = 1;
        for (MediaParticipant participant : (SortedSet<MediaParticipant>) contributedMediaParticipant) {
            if (participant instanceof Movie || participant instanceof Series) {
                if (i == index) {
                    return (Production) participant;
                }
                i++;
            }
        }
        return null;
    }

    public int printActorsAdded(int index) {
        for (MediaParticipant participant : (SortedSet<MediaParticipant>) contributedMediaParticipant) {
            if (participant instanceof Actor) {
                System.out.println(index + ")  " + ((Actor) participant).getName());
                index++;
            }
        }
        return index;
    }

    public Actor getIndexActorContributed(int index) {
        int i = 1;
        for (MediaParticipant participant : (SortedSet<MediaParticipant>) contributedMediaParticipant) {
            if (participant instanceof Actor) {
                if (i == index) {
                    return (Actor) participant;
                }
                i++;
            }
        }
        return null;
    }

    @Override
    public void addProductionSystem(Production p) {
        contributedMediaParticipant.add((T) p);
    }

    @Override
    public void addActorSystem(Actor a) {
        contributedMediaParticipant.add((T) a);
    }

    public boolean isActorProdContributedWithName(String name) {
        for (MediaParticipant participant : (SortedSet<MediaParticipant>) contributedMediaParticipant) {
            if (participant instanceof Actor) {
                Actor a = (Actor) participant;
                if (name.equals(a.getName())) {
                    return true;
                }
            } else if (participant instanceof Production) {
                Production p = (Production) participant;
                if (name.equals(p.getTitle())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isActorContributedWithName(String name) {
        for (MediaParticipant participant : (SortedSet<MediaParticipant>) contributedMediaParticipant) {
            if (participant instanceof Actor) {
                Actor a = (Actor) participant;
                if (name.equals(a.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isProdContributedWithName(String name) {
        for (MediaParticipant participant : (SortedSet<MediaParticipant>) contributedMediaParticipant) {
            if (participant instanceof Production) {
                Production p = (Production) participant;
                if (name.equals(p.getTitle())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void deleteProduction(Production p) {
        contributedMediaParticipant.remove((T) p);
    }

    public void deleteActor(Actor a) {
        contributedMediaParticipant.remove((T) a);
    }

    @Override
    public void updateProduction(Production p) {
    }

    @Override
    public void updateActor(Actor a) {
    }

    @Override
    public void resolveUserRequests() {
    }

    public void receivedRequest(Request request) {
        requestsFromUsers.add(request);
    }

    public void removeReceivedRequest(Request request) {
        requestsFromUsers.remove(request);
    }

    @Override
    public String toString() {
        return "Staff { \n" +
                super.toString() +
                "   requestsFromUsers :" + requestsFromUsers + "\n" +
                "   contributedMediaParticipant : " + contributedMediaParticipant + "\n" +
                '}';
    }
}
