package main.java.structures;

import main.java.models.Course;
import main.java.models.User;

/**
 * This class is used to apply a context to view switching.
 * It allows different services ot know which User is using
 * the application, and which Course they're manipulating.
 */
public class Context {

    public User user;
    public Course course;

    public Context(User user) {
        this.user = user;
    }

    public Context(User user, Course course) {
        this.user = user;
        this.course = course;
    }
}
