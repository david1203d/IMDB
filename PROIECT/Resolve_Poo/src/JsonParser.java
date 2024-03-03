import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JsonParser {
    public void parseAccounts(String fileName, List<Regular<?>> regulars, List<Contributor<?>> contributors, List<Admin<?>> admins, List<Actor> actors, List<Movie> movies, List<Series> series) {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(fileName)) {
            Object object = parser.parse(reader);
            JSONArray accountsList = (JSONArray) object;
            Iterator<JSONObject> accountIterator = accountsList.iterator();

            while (accountIterator.hasNext()) {
                JSONObject accountJson = accountIterator.next();

                List<String> notifications = new ArrayList<>();

                JSONArray notificationsJSON = (JSONArray) accountJson.get("notifications");
                if (notificationsJSON != null) {
                    Iterator<Object> notificationIterator = notificationsJSON.iterator();
                    while (notificationIterator.hasNext()) {
                        String notification = (String) notificationIterator.next();
                        notifications.add(notification);
                    }
                }

                String username = (String) accountJson.get("username");
                String experienceString = (String) accountJson.get("experience");
                int experience = (experienceString != null) ? Integer.parseInt(experienceString) : 0;

                JSONObject info = (JSONObject) accountJson.get("information");
                JSONObject cred = (JSONObject) info.get("credentials");
                String email = (String) cred.get("email");
                String password = (String) cred.get("password");
                String name = (String) info.get("name");
                String country = (String) info.get("country");
                Number ageNumber = (Number) info.get("age");
                int age = 0;
                if (ageNumber != null)
                    age = ageNumber.intValue();
                String gender = (String) info.get("gender");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String stringLocalDate = (String) info.get("birthDate");
                LocalDateTime localDateTime = null;
                if (stringLocalDate != null) {
                    LocalDate localDate = LocalDate.parse(stringLocalDate, formatter);
                    localDateTime = localDate.atStartOfDay();
                }
                String typeString = (String) accountJson.get("userType");
                AccountType userType = AccountType.UNKNOWN;
                if (typeString != null) {
                    userType = AccountType.fromString(typeString);
                    if (userType == AccountType.ADMIN)
                        experience = 9999;
                }
                SortedSet<MediaParticipant> MediaParticipants = new TreeSet<>();

                JSONArray actorsContribution = (JSONArray) accountJson.get("actorsContribution");
                if (actorsContribution != null) {
                    Iterator<Object> actorIterator = actorsContribution.iterator();
                    while (actorIterator.hasNext()) {
                        String actorContribution = (String) actorIterator.next();

                        Iterator<Actor> actorListIterator = actors.iterator();
                        while (actorListIterator.hasNext()) {
                            Actor actor = actorListIterator.next();
                            if (actorContribution.equals(actor.getName())) {
                                MediaParticipants.add(actor);
                                break;
                            }
                        }
                    }
                }

                JSONArray productionsContribution = (JSONArray) accountJson.get("productionsContribution");
                if (productionsContribution != null) {
                    Iterator<Object> productionIterator = productionsContribution.iterator();
                    while (productionIterator.hasNext()) {
                        String productionContribution = (String) productionIterator.next();

                        Iterator<Movie> movieIterator = movies.iterator();
                        while (movieIterator.hasNext()) {
                            Movie movie = movieIterator.next();
                            if (productionContribution.equals(movie.getTitle())) {
                                MediaParticipants.add(movie);
                                break;
                            }
                        }

                        Iterator<Series> seriesIterator = series.iterator();
                        while (seriesIterator.hasNext()) {
                            Series serie = seriesIterator.next();
                            if (productionContribution.equals(serie.getTitle())) {
                                MediaParticipants.add(serie);
                                break;
                            }
                        }
                    }
                }

                SortedSet<MediaParticipant> favorites = new TreeSet<>();
                JSONArray favoriteActors = (JSONArray) accountJson.get("favoriteActors");
                if (favoriteActors != null) {
                    Iterator<Object> favoriteActorIterator = favoriteActors.iterator();
                    while (favoriteActorIterator.hasNext()) {
                        String favoriteActor = (String) favoriteActorIterator.next();

                        Iterator<Actor> actorIterator = actors.iterator();
                        while (actorIterator.hasNext()) {
                            Actor actor = actorIterator.next();
                            if (favoriteActor.equals(actor.getName())) {
                                favorites.add(actor);
                                break;
                            }
                        }
                    }
                }

                JSONArray favoriteProductions = (JSONArray) accountJson.get("favoriteProductions");
                if (favoriteProductions != null) {
                    Iterator<Object> favoriteProductionIterator = favoriteProductions.iterator();
                    while (favoriteProductionIterator.hasNext()) {
                        String favoriteProduction = (String) favoriteProductionIterator.next();

                        Iterator<Movie> movieIterator = movies.iterator();
                        while (movieIterator.hasNext()) {
                            Movie movie = movieIterator.next();
                            if (favoriteProduction.equals(movie.getTitle())) {
                                favorites.add(movie);
                                break;
                            }
                        }

                        Iterator<Series> seriesIterator = series.iterator();
                        while (seriesIterator.hasNext()) {
                            Series serie = seriesIterator.next();
                            if (favoriteProduction.equals(serie.getTitle())) {
                                favorites.add(serie);
                                break;
                            }
                        }
                    }
                }

                User.Information.Builder builder = new User.Information.Builder();
                builder.credentials(new User.Credentials(email, password))
                        .name(name)
                        .country(country)
                        .age(age)
                        .gender(gender)
                        .birthDate(localDateTime);

                User.Information infoUser = builder.build();
                User<?> userNou = UserFactory.createUser(infoUser, userType, username, experience, notifications, favorites, MediaParticipants);
                if (userType == AccountType.REGULAR)
                    regulars.add((Regular<?>) userNou);
                else if (userType == AccountType.CONTRIBUTOR)
                    contributors.add((Contributor<?>) userNou);
                else if (userType == AccountType.ADMIN)
                    admins.add((Admin<?>) userNou);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Actor> parseActors(String fileName) {
        List<Actor> actors = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(fileName)) {
            JSONArray actorList = (JSONArray) parser.parse(reader);
            Iterator<JSONObject> actorIterator = actorList.iterator();

            while (actorIterator.hasNext()) {
                JSONObject actorJson = actorIterator.next();

                String name = (String) actorJson.get("name");
                String biography = (String) actorJson.get("biography");
                Actor actor = new Actor(name, biography);

                JSONArray performances = (JSONArray) actorJson.get("performances");
                if (performances != null) {
                    Iterator<JSONObject> performanceIterator = performances.iterator();
                    while (performanceIterator.hasNext()) {
                        JSONObject performanceJson = performanceIterator.next();
                        String title = (String) performanceJson.get("title");
                        String type = (String) performanceJson.get("type");
                        actor.addProductionRole(title, type);
                    }
                }

                actors.add(actor);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return actors;
    }


    public void parseProductions(String fileName, List<Movie> movies, List<Series> series, List<Actor> actorsIMDB, Admin<MediaParticipant> systemAdmin) {
        List<Actor> actoriNoi = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(fileName)) {
            JSONArray productionList = (JSONArray) parser.parse(reader);

            for (Object o : productionList) {
                JSONObject productionJson = (JSONObject) o;

                String title = (String) productionJson.get("title");
                String typeProduction = (String) productionJson.get("type");

                JSONArray genresJSON = (JSONArray) productionJson.get("genres");
                List<Genre> genres = new ArrayList<>();
                if (genresJSON != null) {
                    Iterator<Object> genreIterator = genresJSON.iterator();
                    while (genreIterator.hasNext()) {
                        String genreString = (String) genreIterator.next();
                        Genre genre = Genre.UNKNOWN;
                        if (genreString != null) {
                            genre = Genre.fromString(genreString);
                        }
                        genres.add(genre);
                    }
                }

                JSONArray directorsJSON = (JSONArray) productionJson.get("directors");
                List<String> directors = new ArrayList<>();
                if (directorsJSON != null) {
                    Iterator<Object> directorIterator = directorsJSON.iterator();
                    while (directorIterator.hasNext()) {
                        String director = (String) directorIterator.next();
                        directors.add(director);
                    }
                }

                JSONObject seasonsJSON = (JSONObject) productionJson.get("seasons");
                Map<String, List<Episode>> seasons = new TreeMap<>();
                if (seasonsJSON != null) {
                    Iterator<?> seasonIterator = seasonsJSON.keySet().iterator();
                    while (seasonIterator.hasNext()) {
                        String seasonName = (String) seasonIterator.next();
                        JSONArray episodesJSON = (JSONArray) seasonsJSON.get(seasonName);

                        List<Episode> episodes = new ArrayList<>();
                        Iterator<Object> episodeIterator = episodesJSON.iterator();
                        while (episodeIterator.hasNext()) {
                            JSONObject episodeJSON = (JSONObject) episodeIterator.next();
                            String episodeName = (String) episodeJSON.get("episodeName");
                            String episodeDuration = (String) episodeJSON.get("duration");
                            episodes.add(new Episode(episodeName, episodeDuration));
                        }
                        seasons.put(seasonName, episodes);
                    }
                }

                JSONArray actorsJSON = (JSONArray) productionJson.get("actors");
                List<String> actors = new ArrayList<>();
                if (actorsJSON != null) {
                    Iterator<Object> actorIterator = actorsJSON.iterator();
                    while (actorIterator.hasNext()) {
                        String actor = (String) actorIterator.next();
                        actors.add(actor);
                    }
                }

                JSONArray ratingsJSON = (JSONArray) productionJson.get("ratings");
                List<Rating> ratings = new ArrayList<>();
                if (ratingsJSON != null) {
                    Iterator<Object> ratingIterator = ratingsJSON.iterator();
                    while (ratingIterator.hasNext()) {
                        JSONObject ratingJSON = (JSONObject) ratingIterator.next();
                        String username = (String) ratingJSON.get("username");
                        Number ratingNumber = (Number) ratingJSON.get("rating");
                        int rating = 0;
                        if (ratingNumber != null)
                            rating = ratingNumber.intValue();
                        String comment = (String) ratingJSON.get("comment");
                        ratings.add(new Rating(username, rating, comment));
                    }
                }

                Double averageRating = Optional.ofNullable((Number) productionJson.get("averageRating"))
                        .map(Number::doubleValue)
                        .orElse(0.0);

                String duration = (String) productionJson.get("duration");

                String storyline = (String) productionJson.get("plot");

                int numSeasons = Optional.ofNullable((Number) productionJson.get("numSeasons"))
                        .map(Number::intValue)
                        .orElse(0);

                int releaseYear = Optional.ofNullable((Number) productionJson.get("releaseYear"))
                        .map(Number::intValue)
                        .orElse(0);

                if (typeProduction != null) {
                    Production production;
                    String productionType;

                    if (typeProduction.equals("Movie")) {
                        production = new Movie(title, directors, actors, genres, ratings, storyline, averageRating, duration, releaseYear);
                        productionType = "Movie";
                    } else if (typeProduction.equals("Series")) {
                        production = new Series(title, directors, actors, genres, ratings, storyline, averageRating, releaseYear, numSeasons, seasons);
                        productionType = "Series";
                    } else {
                        continue;
                    }

                    for (String actorName : actors) {
                        boolean isActorFoundInIMDB = false;

                        for (Actor imdbActor : actorsIMDB) {
                            if (actorName.equals(imdbActor.getName())) {
                                isActorFoundInIMDB = true;
                                break;
                            }
                        }

                        if (!isActorFoundInIMDB) {
                            boolean isActorFoundInNewActors = false;
                            for (Actor newActor : actoriNoi) {
                                if (actorName.equals(newActor.getName())) {
                                    newActor.addProductionRole(title, productionType);
                                    isActorFoundInNewActors = true;
                                    break;
                                }
                            }

                            if (!isActorFoundInNewActors) {
                                Actor actorToAdd = new Actor(actorName, null);
                                actorToAdd.addProductionRole(title, productionType);
                                actoriNoi.add(actorToAdd);
                            }
                        }
                    }

                    if (typeProduction.equals("Movie")) {
                        movies.add((Movie) production);
                    } else if (typeProduction.equals("Series")) {
                        series.add((Series) production);
                    }
                }

            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        for (Actor actor : actoriNoi)
            systemAdmin.addActorSystem(actor);
        actorsIMDB.addAll(actoriNoi);
    }

    public List<Request> parseRequests(String fileName) {
        List<Request> requests = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(fileName)) {
            JSONArray requestList = (JSONArray) parser.parse(reader);

            Iterator<JSONObject> iterator = requestList.iterator();
            while (iterator.hasNext()) {
                JSONObject requestJson = iterator.next();

                String description = (String) requestJson.get("description");
                String userUsername = (String) requestJson.get("username");
                String resolverUsername = (String) requestJson.get("to");

                String typeString = (String) requestJson.get("type");
                RequestType type = (typeString != null) ? RequestType.fromString(typeString) : RequestType.UNKNOWN;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                String stringLocalDateTime = (String) requestJson.get("createdDate");
                LocalDateTime localDateTime = (stringLocalDateTime != null) ? LocalDateTime.parse(stringLocalDateTime, formatter) : null;

                String title = (String) requestJson.get("movieTitle");
                String name = (String) requestJson.get("actorName");
                String titleOrName = (title != null) ? title : name;

                Request request = new Request(type, localDateTime, titleOrName, description, userUsername, resolverUsername);

                requests.add(request);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }


}
