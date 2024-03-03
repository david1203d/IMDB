import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Request {
    private RequestType type;
    private LocalDateTime creationDateTime;
    private String title;
    private String description;
    private String requestingUser;
    private String resolverUser;

    public Request(RequestType type, LocalDateTime creationDateTime, String title,
                   String description, String requestingUser, String resolverUser) {
        this.type = type;
        this.creationDateTime = creationDateTime;
        this.title = title;
        this.description = description;
        this.requestingUser = requestingUser;
        this.resolverUser = resolverUser;
    }

    public RequestType getType() {
        return type;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getRequestingUser() {
        return requestingUser;
    }

    public String getResolverUser() {
        return resolverUser;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRequestingUser(String requestingUser) {
        this.requestingUser = requestingUser;
    }

    public void setResolverUser(String resolverUser) {
        this.resolverUser = resolverUser;
    }

    public String getFormattedCreationDate() {
        if (creationDateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return creationDateTime.format(formatter);
    }

    @Override
    public String toString() {
        return "Request { " +
                "Type = " + type +
                " CreationDateTime = " + getFormattedCreationDate() +
                " Title = " + title +
                " Description = " + description +
                " RequestingUser = " + requestingUser +
                " ResolverUser = " + resolverUser +
                '}';
    }
}
