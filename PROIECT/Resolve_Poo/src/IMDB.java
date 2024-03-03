import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.awt.Window;

public class IMDB {
    private static IMDB instance = null;

    private List<Regular<?>> regulars;
    private List<Contributor<?>> contributors;
    private List<Admin<?>> admins;
    private List<Actor> actors;
    private List<Request> requests;
    private List<Movie> movies;
    private List<Series> series;

    private IMDB() {
        regulars = new ArrayList<>();
        contributors = new ArrayList<>();
        admins = new ArrayList<>();
        movies = new ArrayList<>();
        series = new ArrayList<>();
    }

    public static IMDB getInstance() {
        if (instance == null) {
            instance = new IMDB();
        }
        return instance;
    }

    void requestsPrint() {
        for (Request request : requests)
            System.out.println(request);
    }

    void actorsPrint() {
        for (Actor actor : actors)
            System.out.println(actor);
    }

    void seriesPrint() {
        for (Series serie : series)
            System.out.println(serie);
    }

    void moviesPrint() {
        for (Movie movie : movies)
            System.out.println(movie);
    }

    void accountsPrint() {
        System.out.println(" Regulars : " + regulars.size());
        for (Regular request : regulars)
            System.out.println(request.getUsername());

        System.out.println("\n" + " Contributors : " + contributors.size());
        for (Contributor contributor : contributors)
            System.out.println(contributor.getUsername());

        System.out.println("\n" + " Admins : " + admins.size());
        for (Admin admin : admins)
            System.out.println(admin.getUsername());
    }

    void loadUserRatings() {
        for (Movie movie : movies) {
            for (Rating rating : movie.getUserRatings()) {
                updateUserRating(regulars, movie, rating);
            }
        }

        for (Series series : series) {
            for (Rating rating : series.getUserRatings()) {
                updateUserRating(regulars, series, rating);
            }
        }
    }

    private void updateUserRating(List<Regular<?>> regulars, Production production, Rating rating) {
        for (Regular<?> regular : regulars) {
            if (rating.getUsername().equals(regular.getUsername())) {
                regular.getUserRatings().put(production, rating);
                break;
            }
        }
    }

    public class AdminF {
        public static User.Information createAdminInformation() {
            User.Information.Builder adminInfoBuilder = new User.Information.Builder();
            adminInfoBuilder.credentials(new User.Credentials("ADMIN", "ADMIN"))
                    .name("Default Admin")
                    .country("Admin Country");
            return adminInfoBuilder.build();
        }
    }

    public void run() {
        JsonParser jsonParser = new JsonParser();
        actors = jsonParser.parseActors("resources/input/actors.json");
        // actorsPrint();
        requests = jsonParser.parseRequests("resources/input/requests.json");
        // requestsPrint();
        User.Information adminInfo = AdminF.createAdminInformation();
        Admin systemAdmin = new Admin(adminInfo, AccountType.ADMIN, "ADMIN", 9999);
        admins.add(systemAdmin);
        jsonParser.parseProductions("resources/input/production.json", movies, series, actors, systemAdmin);
        //moviesPrint();
        //seriesPrint();
        // System.out.println(actors);
        jsonParser.parseAccounts("resources/input/accounts.json", regulars, contributors, admins, actors, movies, series);
        //accountsPrint();
        loadUserRatings();
        Start();
    }

    public void CLImessage(String s1, String s2, String s3) {
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
    }

    public void CLI() {
        CLImessage("1. Create account", "2. Log in", "3. Close");
        int number = chooseValidNumber(3);
        if (number == 1)
            createAccount();
        else if (number == 2)
            Log_in();
        else
            System.exit(0);
    }

    public void createAccount() {
        System.out.println("Tzeapa");
        System.exit(0);
    }

    ;

    public void Log_in() {
        System.out.println("Enter your credentials : ");
        System.out.println("Email : ");
        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine().trim();
        System.out.println("Password : ");
        String password = scanner.nextLine().trim();

        boolean verified = false;
        User foundUser = null;

        for (Regular regularUser : regulars) {
            if (verifyUser(regularUser, email, password)) {
                verified = true;
                foundUser = regularUser;
                break;
            }
        }

        if (!verified) {
            for (Contributor contributorUser : contributors) {
                if (verifyUser(contributorUser, email, password)) {
                    verified = true;
                    foundUser = contributorUser;
                    break;
                }
            }
        }

        if (!verified) {
            for (Admin adminUser : admins) {
                if (verifyUser(adminUser, email, password)) {
                    verified = true;
                    foundUser = adminUser;
                    break;
                }
            }
        }

        if (verified) {
            if (foundUser instanceof Regular) {

                Regular regularUser = (Regular) foundUser;
                optionsRgular(regularUser);

            } else if (foundUser instanceof Contributor) {

                Contributor contributorUser = (Contributor) foundUser;
                optionsContributor(contributorUser);

            } else if (foundUser instanceof Admin) {

                Admin adminUser = (Admin) foundUser;
                optionsAdmin(adminUser);
            }
        } else {
            System.out.println("Login failed.\n" +
                    "You can : \n 1. Try again \n 2. Go back");
            int number = chooseValidNumber(2);
            if (number == 1)
                Log_in();
            else
                CLI();
        }

    }

    public void Log_in(String email, String password, JFrame frame, JLabel feedbackLabel) {
        boolean verified = false;
        User foundUser = null;

        for (Regular regularUser : regulars) {
            if (verifyUser(regularUser, email, password)) {
                verified = true;
                foundUser = regularUser;
                break;
            }
        }

        if (!verified) {
            for (Contributor contributorUser : contributors) {
                if (verifyUser(contributorUser, email, password)) {
                    verified = true;
                    foundUser = contributorUser;
                    break;
                }
            }
        }

        if (!verified) {
            for (Admin adminUser : admins) {
                if (verifyUser(adminUser, email, password)) {
                    verified = true;
                    foundUser = adminUser;
                    break;
                }
            }
        }

        if (verified) {
            handleSuccessfulLogin(foundUser);
        } else {
            feedbackLabel.setText("Login failed!");
            JButton retryButton = new JButton("Try Again");
            retryButton.setBounds(10, 110, 100, 25);
            retryButton.setBackground(new Color(70, 130, 180));
            retryButton.setForeground(Color.WHITE);
            retryButton.setFont(new Font("Arial", Font.BOLD, 12));
            retryButton.setBorder(BorderFactory.createRaisedBevelBorder());

            frame.add(retryButton);
            frame.revalidate();
            frame.repaint();

            retryButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    feedbackLabel.setText("");
                    frame.getContentPane().remove(retryButton);
                    frame.revalidate();
                    frame.repaint();
                }
            });


        }
    }

    private void handleSuccessfulLogin(User foundUser) {

        if (foundUser instanceof Regular) {
            displayRegularOptions((Regular<?>) foundUser);
        } else if (foundUser instanceof Contributor) {
            displayContributorOptions((Contributor<?>) foundUser);
        } else displayAdminOptions((Admin<?>) foundUser);
    }

    private void displayRegularOptions(Regular<?> user) {
        JFrame userFrame = new JFrame("User Options -> " + user.getUsername());
        userFrame.setSize(400, 400);
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        userFrame.add(panel);
        placeRegularComponents(panel, user);

        userFrame.setVisible(true);
    }

    private void placeRegularComponents(JPanel panel, Regular<?> user) {
        panel.setLayout(new GridLayout(0, 1));

        JButton productionButton = new JButton("Production");
        productionButton.addActionListener(e -> productionDetail(user, AccountType.REGULAR));
        panel.add(productionButton);

        JButton actorButton = new JButton("Actor");
        actorButton.addActionListener(e -> actorDetail(user, AccountType.REGULAR));
        panel.add(actorButton);

        JButton notificationsButton = new JButton("Notification");
        notificationsButton.addActionListener(e -> notification(user, AccountType.REGULAR));
        panel.add(notificationsButton);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> gsearch(user, AccountType.REGULAR, (JFrame) SwingUtilities.getWindowAncestor(panel)));
        panel.add(searchButton);

        JButton favoritesButton = new JButton("Favorites");
        favoritesButton.addActionListener(e -> favorite(user, AccountType.REGULAR, (JFrame) SwingUtilities.getWindowAncestor(panel)));
        panel.add(favoritesButton);

        JButton requestButton = new JButton("Request");
        requestButton.addActionListener(e -> request(user, AccountType.REGULAR));
        panel.add(requestButton);

        JButton prButton = new JButton("Production Review");
        prButton.addActionListener(e -> productionReview(user, AccountType.REGULAR));
        panel.add(prButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(panel);
            if (window instanceof JFrame) {
                JFrame currentFrame = (JFrame) window;
                currentFrame.dispose();

                if (loginFrame != null) {
                    loginFrame.setVisible(true);
                }
            }
        });
        panel.add(logoutButton);
    }

    public void favorite(User<?> user, AccountType account, JFrame frame) {
        String searchQuery = JOptionPane.showInputDialog(frame, "Enter the name of an actor, movie, or series:");

        if (searchQuery == null) {
            JOptionPane.showMessageDialog(frame, "Operation cancelled.");
            return;
        }

        if (!searchQuery.isEmpty()) {
            boolean isInFavorites = isItemInFavorites(user, searchQuery);
            if (isInFavorites) {
                int choice = JOptionPane.showConfirmDialog(frame, searchQuery + " is in favorites.\nDo you want to delete " + searchQuery + " from favorites?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    deleteItemFavorites(user, account);
                    JOptionPane.showMessageDialog(frame, searchQuery + " has been deleted from favorites.");
                } else {
                    JOptionPane.showMessageDialog(frame, "No changes made to favorites.");
                    retryOrExit(user, account, frame);
                }
            } else {
                int choice = JOptionPane.showConfirmDialog(frame, searchQuery + " is not in favorites.\nDo you want to add " + searchQuery + " to favorites?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    addItemFavorites(user, account, searchQuery);
                    JOptionPane.showMessageDialog(frame, searchQuery + " has been added to favorites.");
                } else {
                    JOptionPane.showMessageDialog(frame, "No changes made to favorites.");
                    retryOrExit(user, account, frame);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a valid name. The search query cannot be empty.");
            favorite(user, account, frame);
        }
    }

    private void retryOrExit(User<?> user, AccountType account, JFrame frame) {
        String[] options = {"Try", "Exit"};
        int choice = JOptionPane.showOptionDialog(frame, "Do you want to try with someone else or to exit?", "Choose an option", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            favorite(user, account, frame);
        } else {
            handleSuccessfulLogin(user);
        }
    }

    public void gsearch(User<?> user, AccountType account, JFrame parentFrame) {
        String searchQuery = JOptionPane.showInputDialog(parentFrame, "Enter the name of an actor, movie, or series:");

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            processgSearch(searchQuery.trim(), user, account, parentFrame);
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Please enter a valid name. The search query cannot be empty.");
        }
    }

    private void processgSearch(String searchQuery, User<?> user, AccountType account, JFrame parentFrame) {
        Movie foundMovie = findMovieByName(searchQuery);
        Series foundSeries = findSeriesByName(searchQuery);
        Actor foundActor = findActorByName(searchQuery);

        if (foundMovie != null) {
            presentgSearchResult("Movie", foundMovie, parentFrame);
        } else if (foundSeries != null) {
            presentgSearchResult("Series", foundSeries, parentFrame);
        } else if (foundActor != null) {
            presentgSearchResult("Actor", foundActor, parentFrame);
        } else {
            JOptionPane.showMessageDialog(parentFrame, "No results found for '" + searchQuery + "'.");
        }
    }

    private void presentgSearchResult(String type, Object result, JFrame parentFrame) {
        JOptionPane.showMessageDialog(parentFrame, type + " was found : \n" + result);
    }


    public void notification(User<?> user, AccountType account) {
        JFrame notificationsFrame = new JFrame("Notifications");
        notificationsFrame.setSize(300, 200);
        notificationsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        List<String> notifications = user.getNotifications();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (notifications.isEmpty()) {
            panel.add(new JLabel("There are no notifications."));
        } else {
            for (String notification : notifications) {
                panel.add(new JLabel(notification));
            }
        }

        notificationsFrame.add(new JScrollPane(panel));
        notificationsFrame.setVisible(true);
    }


    public void actorDetail(User<?> user, AccountType account) {
        JFrame detailsFrame = new JFrame("Actor Details");
        detailsFrame.setSize(300, 200);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (int i = 0; i < actors.size(); i++) {
            Actor actor = actors.get(i);
            JButton actorButton = new JButton((i + 1) + ". " + actor.getName());
            actorButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(detailsFrame, actor.toString());
                detailsFrame.dispose();
            });
            panel.add(actorButton);
        }

        detailsFrame.add(new JScrollPane(panel));
        detailsFrame.setVisible(true);
    }


    public void productionDetail(User<?> user, AccountType account) {
        JFrame detailsFrame = new JFrame("Production Details");
        detailsFrame.setSize(300, 200);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JButton moviesButton = new JButton("Movies");
        moviesButton.addActionListener(e -> {
            showProductionList("Movies", movies, detailsFrame);
        });
        panel.add(moviesButton);

        JButton seriesButton = new JButton("Series");
        seriesButton.addActionListener(e -> {
            showProductionList("Series", series, detailsFrame);
        });
        panel.add(seriesButton);

        detailsFrame.add(panel);
        detailsFrame.setVisible(true);
    }

    private void showProductionList(String type, List<?> productions, JFrame parentFrame) {
        JFrame listFrame = new JFrame(type);
        listFrame.setSize(300, 400);
        listFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (int i = 0; i < productions.size(); i++) {
            Object prod = productions.get(i);
            String buttonText = (i + 1) + ". ";

            if (prod instanceof Movie) {
                Movie movie = (Movie) prod;
                buttonText += movie.getTitle() + " -> Release Year " + movie.getReleaseYear();
            } else if (prod instanceof Series) {
                Series series = (Series) prod;
                buttonText += series.getTitle() + " -> Release Year " + series.getReleaseYear();
            } else {
                continue;
            }

            JButton btn = new JButton(buttonText);
            btn.addActionListener(e -> {
                JOptionPane.showMessageDialog(listFrame, prod.toString());
                listFrame.dispose();
                parentFrame.dispose();
            });
            panel.add(btn);
        }
        listFrame.add(new JScrollPane(panel));
        listFrame.setVisible(true);
    }


    private void displayContributorOptions(Contributor<?> user) {
        JFrame userFrame = new JFrame("User Options -> " + user.getUsername());
        userFrame.setSize(400, 400);
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        userFrame.add(panel);
        placeContributorComponents(panel, user);

        userFrame.setVisible(true);
    }

    private void placeContributorComponents(JPanel panel, Contributor<?> user) {
        panel.setLayout(new GridLayout(0, 1));

        JButton productionButton = new JButton("Production");
        productionButton.addActionListener(e -> productionDetail(user, AccountType.CONTRIBUTOR));
        panel.add(productionButton);

        JButton actorButton = new JButton("Actor");
        actorButton.addActionListener(e -> actorDetail(user, AccountType.CONTRIBUTOR));
        panel.add(actorButton);

        JButton notificationButton = new JButton("Notification");
        notificationButton.addActionListener(e -> notification(user, AccountType.CONTRIBUTOR));
        panel.add(notificationButton);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> gsearch(user, AccountType.CONTRIBUTOR, (JFrame) SwingUtilities.getWindowAncestor(panel)));
        panel.add(searchButton);

        JButton favoritesButton = new JButton("Favorites");
        favoritesButton.addActionListener(e -> favorite(user, AccountType.CONTRIBUTOR, (JFrame) SwingUtilities.getWindowAncestor(panel)));
        panel.add(favoritesButton);

        JButton requestButton = new JButton("Request");
        requestButton.addActionListener(e -> request(user, AccountType.CONTRIBUTOR));
        panel.add(requestButton);

        JButton modifyAPButton = new JButton("Modify Actor/Production");
        modifyAPButton.addActionListener(e -> modifyActorProduction(user, AccountType.CONTRIBUTOR));
        panel.add(modifyAPButton);

        JButton solveRequestButton = new JButton("Solve Requests");
        solveRequestButton.addActionListener(e -> solveRequests(user, AccountType.CONTRIBUTOR));
        panel.add(solveRequestButton);

        JButton updateAPButton = new JButton("Update Actor/Production");
        updateAPButton.addActionListener(e -> updateActorProduction(user, AccountType.CONTRIBUTOR));
        panel.add(updateAPButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(panel);
            if (window instanceof JFrame) {
                JFrame currentFrame = (JFrame) window;
                currentFrame.dispose();
                if (loginFrame != null) {
                    loginFrame.setVisible(true);
                }
            }
        });
        panel.add(logoutButton);
    }

    private void displayAdminOptions(Admin<?> user) {
        JFrame userFrame = new JFrame("User Options -> " + user.getUsername());
        userFrame.setSize(400, 400);
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        userFrame.add(panel);
        placeAdminComponents(panel, user);

        userFrame.setVisible(true);
    }

    private void placeAdminComponents(JPanel panel, Admin<?> user) {
        panel.setLayout(new GridLayout(0, 1));

        JButton productionButton = new JButton("Production");
        productionButton.addActionListener(e -> productionDetail(user, AccountType.ADMIN));
        panel.add(productionButton);

        JButton actorButton = new JButton("Actor");
        actorButton.addActionListener(e -> actorDetail(user, AccountType.ADMIN));
        panel.add(actorButton);

        JButton notificationButton = new JButton("Notification");
        notificationButton.addActionListener(e -> notification(user, AccountType.ADMIN));
        panel.add(notificationButton);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> gsearch(user, AccountType.ADMIN, (JFrame) SwingUtilities.getWindowAncestor(panel)));
        panel.add(searchButton);

        JButton favoritesButton = new JButton("Favorites");
        favoritesButton.addActionListener(e -> favorite(user, AccountType.ADMIN, (JFrame) SwingUtilities.getWindowAncestor(panel)));
        panel.add(favoritesButton);

        JButton modifyAPButton = new JButton("Modify Actor/Production");
        modifyAPButton.addActionListener(e -> modifyActorProduction(user, AccountType.ADMIN));
        panel.add(modifyAPButton);

        JButton solveRequestButton = new JButton("Solve Requests");
        solveRequestButton.addActionListener(e -> solveRequests(user, AccountType.ADMIN));
        panel.add(solveRequestButton);

        JButton updateAPButton = new JButton("Update Actor/Production");
        updateAPButton.addActionListener(e -> updateActorProduction(user, AccountType.ADMIN));
        panel.add(updateAPButton);

        JButton modifyUserButton = new JButton("Modify User");
        modifyUserButton.addActionListener(e -> modifyUser(user, AccountType.ADMIN));
        panel.add(modifyUserButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(panel);
            if (window instanceof JFrame) {
                JFrame currentFrame = (JFrame) window;
                currentFrame.dispose();
                if (loginFrame != null) {
                    loginFrame.setVisible(true);
                }
            }
        });
        panel.add(logoutButton);
    }

    private JFrame loginFrame;

    public void GUI() {
        JFrame frame = new JFrame("IMDB Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, frame);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel, JFrame frame) {
        panel.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel userLabel = new JLabel("Email:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JLabel feedbackLabel = new JLabel("");
        feedbackLabel.setBounds(10, 130, 300, 25);
        panel.add(feedbackLabel);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = userText.getText();
                String password = new String(passwordText.getPassword());
                Log_in(email, password, frame, feedbackLabel);
            }
        });
    }

    public void optionsRgular(Regular<?> user) {
        while (true) {
            System.out.println("Good to see you!");
            System.out.println("Username: " + user.getUsername());
            System.out.println("User experience: " + ((user.getExperience() == 0) ? "->" : user.getExperience()));
            System.out.println("Make a choice:");
            System.out.println("1. Production");
            System.out.println("2. Actor");
            System.out.println("3. Notifications");
            System.out.println("4. Search ");
            System.out.println("5. Favorites");
            System.out.println("6. Request");
            System.out.println("7. Production Review");
            System.out.println("8. Logout");
            int number = chooseValidNumber(8);
            if (number == 1)
                productionDetails(user, AccountType.REGULAR);
            else if (number == 2)
                actorDetails(user, AccountType.REGULAR);
            else if (number == 3)
                notifications(user, AccountType.REGULAR);
            else if (number == 4)
                search(user, AccountType.REGULAR);
            else if (number == 5)
                favorites(user, AccountType.REGULAR);
            else if (number == 6)
                request(user, AccountType.REGULAR);
            else if (number == 7)
                productionReview(user, AccountType.REGULAR);
            else
                logout(user);
            System.out.println("Do you want to log out? (yes/no)");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine().trim().toLowerCase();
            if (!response.equals("yes")) {
                continue;
            } else {
                Log_in();
            }
        }
    }

    public void optionsContributor(Contributor<?> user) {
        while (true) {
            System.out.println("Good to see you!");
            System.out.println("Username: " + user.getUsername());
            System.out.println("User experience: " + ((user.getExperience() == 0) ? "->" : user.getExperience()));
            System.out.println("Make a choice:");
            System.out.println("1. Productions");
            System.out.println("2. Actors");
            System.out.println("3. Notifications");
            System.out.println("4. Search");
            System.out.println("5. Favorites");
            System.out.println("6. Request");
            System.out.println("7. Modify Actor/Production");
            System.out.println("8. Solve Requests");
            System.out.println("9. Update Actor/Production");
            System.out.println("10. Logout");
            int number = chooseValidNumber(10);
            if (number == 1)
                productionDetails(user, AccountType.CONTRIBUTOR);
            else if (number == 2)
                actorDetails(user, AccountType.CONTRIBUTOR);
            else if (number == 3)
                notifications(user, AccountType.CONTRIBUTOR);
            else if (number == 4)
                search(user, AccountType.CONTRIBUTOR);
            else if (number == 5)
                favorites(user, AccountType.CONTRIBUTOR);
            else if (number == 6)
                request(user, AccountType.CONTRIBUTOR);
            else if (number == 7)
                modifyActorProduction(user, AccountType.CONTRIBUTOR);
            else if (number == 8)
                solveRequests(user, AccountType.CONTRIBUTOR);
            else if (number == 9)
                updateActorProduction(user, AccountType.CONTRIBUTOR);
            else logout(user);

            System.out.println("Do you want to log out? (yes/no)");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine().trim().toLowerCase();
            if (!response.equals("yes")) {
                continue;
            } else {
                Log_in();
            }
        }
    }


    public void optionsAdmin(Admin<?> user) {
        while (true) {
            System.out.println("Good to see you, boss");
            System.out.println("Username : " + user.getUsername());
            System.out.println("User experience : " + ((user.getExperience() == 0) ? "->" : user.getExperience()));
            System.out.println("Make a choice : ");
            System.out.println("1. Productions");
            System.out.println("2. Actors");
            System.out.println("3. Notifications");
            System.out.println("4. Search");
            System.out.println("5. Favorites");
            System.out.println("6. Modify Actor/Production");
            System.out.println("7. Solve Requests");
            System.out.println("8. Update Actor/Production");
            System.out.println("9. Modify User");
            System.out.println("10. Logout");
            int number = chooseValidNumber(10);
            if (number == 1)
                productionDetails(user, AccountType.ADMIN);
            else if (number == 2)
                actorDetails(user, AccountType.ADMIN);
            else if (number == 3)
                notifications(user, AccountType.ADMIN);
            else if (number == 4)
                search(user, AccountType.ADMIN);
            else if (number == 5)
                favorites(user, AccountType.ADMIN);
            else if (number == 6)
                modifyActorProduction(user, AccountType.ADMIN);
            else if (number == 7)
                solveRequests(user, AccountType.ADMIN);
            else if (number == 8)
                updateActorProduction(user, AccountType.ADMIN);
            else if (number == 9)
                modifyUser(user, AccountType.ADMIN);
            else logout(user);

            System.out.println("Do you want to log out? (yes/no)");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine().trim().toLowerCase();
            if (!response.equals("yes")) {
                continue;
            } else {
                Log_in();
            }
        }
    }


    public void search(User<?> user, AccountType accountType) {
        Scanner scanner = new Scanner(System.in);
        String searchQuery = "";

        while (searchQuery.isEmpty()) {
            System.out.print("Enter the name of an actor, movie, or series: ");
            searchQuery = scanner.nextLine().trim();

            if (searchQuery.isEmpty()) {
                System.out.println("Please enter a valid name. The search query cannot be empty.");
            } else {
                processSearch(searchQuery, user, accountType);
            }
        }
    }

    private void processSearch(String searchQuery, User<?> user, AccountType account) {
        Movie foundMovie = findMovieByName(searchQuery);
        Series foundSeries = findSeriesByName(searchQuery);
        Actor foundActor = findActorByName(searchQuery);

        if (foundMovie != null) {
            presentSearchResult("Movie", foundMovie);
        } else if (foundSeries != null) {
            presentSearchResult("Series", foundSeries);
        } else if (foundActor != null) {
            presentSearchResult("Actor", foundActor);
        } else {
            System.out.println("No results found for '" + searchQuery + "'.");
            offerRetry(user, account);
        }
    }

    private Movie findMovieByName(String name) {
        for (Movie movie : movies) {
            if (name.equalsIgnoreCase(movie.getTitle())) {
                return movie;
            }
        }
        return null;
    }

    private Series findSeriesByName(String name) {
        for (Series series : series) {
            if (name.equalsIgnoreCase(series.getTitle())) {
                return series;
            }
        }
        return null;
    }

    private Actor findActorByName(String name) {
        for (Actor actor : actors) {
            if (name.equalsIgnoreCase(actor.getName())) {
                return actor;
            }
        }
        return null;
    }

    private void presentSearchResult(String type, Object result) {
        System.out.println(type + " was found:");
        System.out.println(result);

    }

    private void offerRetry(User<?> user, AccountType accountType) {
        System.out.println("\n 1. Give it another go \n 2. Give up and exit");
        int choice = chooseValidNumber(2);
        if (choice == 1) {
            search(user, accountType);
        }
    }

    public void productionDetails(User<?> user, AccountType account) {
        CLImessage("What are you looking for?", "1. Movies", "2. Series");
        int number = chooseValidNumber(2);
        if (number == 1) {
            System.out.println("Movies : ");
            for (int i = 0; i < movies.size(); i++)
                System.out.println((i + 1) + ".  " + movies.get(i).getTitle() + " -> Release Year " + movies.get(i).getReleaseYear());
            System.out.println("Choose a movie : ");
            int movieNumber = chooseValidNumber(movies.size() + 1);
            System.out.println(movies.get(movieNumber - 1));
        } else {
            System.out.println("Series : ");
            for (int i = 0; i < series.size(); i++)
                System.out.println((i + 1) + ".  " + series.get(i).getTitle() + " -> Release Year " + series.get(i).getReleaseYear());
            System.out.println("Choose a series : ");
            int seriesNumber = chooseValidNumber(series.size() + 1);
            System.out.println(series.get(seriesNumber - 1));
        }
        System.out.println("Do you want to exit(to user options)? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine().trim().toLowerCase();
        if (!response.equals("yes")) {
            productionDetails(user, account);
        } else {
            return;
        }
    }

    public void actorDetails(User<?> user, AccountType account) {
        System.out.println("Actors : ");
        for (int i = 0; i < actors.size(); i++)
            System.out.println((i + 1) + ".  " + actors.get(i).getName());
        System.out.println("Choose the actor you want to find out about:");
        int actorNumber = chooseValidNumber(actors.size() + 1);
        if (actorNumber <= actors.size()) {
            System.out.println(actors.get(actorNumber - 1));
        }
        System.out.println("Do you want to exit(to user options)? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine().trim().toLowerCase();
        if (!response.equals("yes")) {
            actorDetails(user, account);
        } else {
            return;
        }
    }

    public void notifications(User<?> user, AccountType account) {
        List<String> notifications = user.getNotifications();

        if (notifications.isEmpty()) {
            System.out.println("There are no notifications.");
        } else {
            System.out.println("Notifications:");
            notifications.forEach(System.out::println);
        }
        System.out.println("\n You've seen the notifications. Now let's go back to the user's options :)");
        return;
    }

    public void favorites(User<?> user, AccountType account) {
        Scanner scanner = new Scanner(System.in);
        String searchQuery = "";

        while (searchQuery.isEmpty()) {
            System.out.print("Enter the name of an actor, movie, or series: ");
            searchQuery = scanner.nextLine().trim();

            if (searchQuery.isEmpty()) {
                System.out.println("Please enter a valid name. The search query cannot be empty.");
            } else {
                boolean isInFavorites = isItemInFavorites(user, searchQuery);
                if (isInFavorites) {
                    System.out.println(searchQuery + " is in favorites.");
                    System.out.println("\n Do you want to delete " +  searchQuery + " from favorites? (yes/no)");
                    Scanner scann = new Scanner(System.in);
                    String choice = scann.nextLine();
                    if(choice.equalsIgnoreCase("yes")) {
                        deleteItemFavorites(user, account);
                    } else {
                        System.out.println("Do you want to try with someone else or to exit? (try/exit)");
                        Scanner scan = new Scanner(System.in);
                        String choic = scan.nextLine();
                        if(choic.equalsIgnoreCase("try")) {
                            favorites(user, account);
                        } else {
                            handleSuccessfulLogin(user);
                        }
                    }

                } else {
                    System.out.println(searchQuery + " is not in favorites.");
                    System.out.println("\n Do you want to add " + searchQuery + " in favorites? (yes/no)");
                    Scanner scann = new Scanner(System.in);
                    String choice = scann.nextLine();
                    if(choice.equalsIgnoreCase("yes")) {
                        addItemFavorites(user, account, searchQuery);
                    } else {
                        System.out.println("Do you want to try with someone else or to exit? (try/exit)");
                        Scanner scan = new Scanner(System.in);
                        String choic = scan.nextLine();
                        if(choic.equalsIgnoreCase("try")) {
                            favorites(user, account);
                        } else {
                            handleSuccessfulLogin(user);
                        }


                    }
                }
            }
        }
    }

    public MediaParticipant findMediaParticipantByName(String name) {
        List<MediaParticipant> allMediaParticipants = new ArrayList<>();
        allMediaParticipants.addAll(movies);
        allMediaParticipants.addAll(series);
        allMediaParticipants.addAll(actors);

        for (MediaParticipant participant : allMediaParticipants) {
            if (participant instanceof Movie) {
                Movie movie = (Movie) participant;
                if (name.equalsIgnoreCase(movie.getTitle())) {
                    return movie;
                }
            } else if (participant instanceof Series) {
                Series series = (Series) participant;
                if (name.equalsIgnoreCase(series.getTitle())) {
                    return series;
                }
            } else if (participant instanceof Actor) {
                Actor actor = (Actor) participant;
                if (name.equalsIgnoreCase(actor.getName())) {
                    return actor;
                }
            }
        }

        return null;
    }


    public void addItemFavorites(User<?> user, AccountType account, String string) {
        MediaParticipant mediaParticipant = findMediaParticipantByName(string);
        if (mediaParticipant == null) {
            System.out.println(string + " was not found in list");
            favorites(user, account);
        } else {
            System.out.println(string + " was added in favorites");
            SortedSet<MediaParticipant> favorit = (SortedSet<MediaParticipant>) user.getFavorites();
            favorit.add(mediaParticipant);
        }
    }

    public void deleteItemFavorites(User<?> user, AccountType account) {
        System.out.println("Not ready yet");
        System.exit(0);
    }

    public boolean isItemInFavorites(User<?> user, String name) {
        for (MediaParticipant mediaParticipant : (SortedSet<MediaParticipant>)user.getFavorites()) {
            if (mediaParticipant instanceof Actor) {
                Actor actor = (Actor) mediaParticipant;
                if (actor.getName().equalsIgnoreCase(name)) {
                    return true;
                }
            } else if (mediaParticipant instanceof Production) {
                Production production = (Production) mediaParticipant;
                if (production.getTitle().equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void request(User<?> user, AccountType account) {

    }
    public void modifyActorProduction(User<?> user, AccountType account) {

    }

    public void solveRequests(User<?> user, AccountType account) {

    }

    public void updateActorProduction(User<?> user, AccountType account) {

    }

    public void productionReview(User<?> user, AccountType account) {

    }

    public void logout(User<?> user) {
        Log_in();
    }

    public void modifyUser(User<?> user, AccountType account) {

    }
        public Boolean verifyUser(User<?> user, String providedEmail, String providedPassword) {
        if (user == null) {
            return false;
        }

        User.Information userInfo = user.getInformation();
        if (userInfo == null) {
            return false;
        }

        User.Credentials userCredentials = userInfo.getCredentials();
        if (userCredentials == null) {
            return false;
        }

        return providedEmail.equals(userCredentials.getEmail()) && providedPassword.equals(userCredentials.getPassword());
    }



    public void Start() {
        CLImessage("Welcome! You can choose between:", "1. CLI", "2. GUI");
        int number = chooseValidNumber(2);
        if (number == 1) {
            CLI();
        }else {
             GUI();
        }
    }
    public static int chooseValidNumber(int maxValue) {
        Scanner scanner = new Scanner(System.in);
        int number;

        while (true) {
            System.out.print("Make a choice : ");
            try {
                number = scanner.nextInt();
                if (number >= 1 && number <= maxValue) {
                    return number;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and " + maxValue + ".");
                }
            } catch (InputMismatchException exception) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    public static void main(String[] args) {
        IMDB imdb = IMDB.getInstance();
        imdb.run();

    }
}

