package main.java;

import main.java.repositories.CourseRepository;
import main.java.repositories.EnrollmentRepository;
import main.java.repositories.UserRepository;
import main.java.services.AccountService;
import main.java.services.CourseService;
import main.java.views.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Application {

    private static Connection connection = null;

    private void init() {
        Properties properties = new Properties();

        try {
            properties.load(Application.class.getClassLoader().getResourceAsStream("main/resources/connection.properties"));

            String endpoint = properties.getProperty("endpoint");
            String url = "jdbc:postgresql://" + endpoint + "/postgres";
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            connection = DriverManager.getConnection(url, username, password);

        } catch (IOException | SQLException e) {
            System.out.println("Missing file! Aborting...");
            System.exit(1);
        }
    }

    public void run() {
        // First we initialize our connection to the database
        init();

        // Second we set up the repositories.
        UserRepository userRepo = new UserRepository(Application.connection);
        CourseRepository courseRepo = new CourseRepository(Application.connection);
        EnrollmentRepository enrollmentRepo = new EnrollmentRepository(Application.connection);

        // Third we set up the services
        AccountService accountService = new AccountService(userRepo, courseRepo, enrollmentRepo);
        CourseService courseService = new CourseService(courseRepo);

        // Fourth we set up the main loop and routes
        AbstractView currentView = new LandingView(System.in);
        while(true) {
            currentView.render();
            String next = currentView.listen();

            switch(next) {
                case "landing":
                    currentView = new LandingView(System.in);
                    break;
                case "login":
                    currentView = new LoginView(System.in, accountService);
                    break;
                case "register":
                    currentView = new AccountRegistrationView(System.in, accountService);
                    break;
                case "faculty":
                    currentView = new FacultyView(System.in, accountService, courseService);
                    break;
                case "student":
                    currentView = new StudentView(System.in, accountService, courseService);
                    break;
                case "enroll":
                    currentView = new CourseRegistryView(System.in, courseService);
                    break;
                case "detail":
                    currentView = new CourseDetailView(System.in, accountService, courseService);
                    break;
                case "new":
                    currentView = new CourseCreationView(System.in, accountService, courseService);
                    break;
                case "exit":
                default:
                    currentView = new ApplicationQuitView(System.in);
                    currentView.render();
                    currentView.closeScanner();
                    System.exit(0);
            }
        }
    }
}
