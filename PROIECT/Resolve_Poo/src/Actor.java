import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Actor implements MediaParticipant {
    private String name;
    private String biography;
    private List<ProductionRole> filmography;

    public Actor(String name, String biography) {
        this.name = name;
        this.biography = biography;
        this.filmography = new ArrayList<>();
    }

    @Override
    public int compareTo(MediaParticipant other) {
        if (other instanceof Actor) {
            Actor otherActor = (Actor) other;
            return this.name.compareTo(otherActor.name);
        }
        return this.getClass().getName().compareTo(other.getClass().getName());
    }

    public void addProductionRole(String filmName, String filmType) {
        filmography.add(new ProductionRole(filmName, filmType));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<ProductionRole> getFilmography() {
        return filmography;
    }

    public void removeProductionRoleByName(String roleName) {
        Iterator<ProductionRole> iterator = filmography.iterator();
        while (iterator.hasNext()) {
            ProductionRole role = iterator.next();
            if (roleName.equals(role.getName())) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public String toString() {
        String productionCredits = filmography.isEmpty()
                ? "Empty Filmography.\n\n"
                : filmography.stream()
                .map(role -> role.name + " -> " + role.type + "\n")
                .collect(Collectors.joining());

        return name + "\n" + productionCredits + "\n";
    }

    public static class ProductionRole {
        private String name;
        private String type;

        public ProductionRole(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
