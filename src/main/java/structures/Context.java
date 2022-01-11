package main.java.structures;

/**
 * This class is used to apply a context to view switching.
 * It allows different services ot know which User is using
 * the application, and which Course they're manipulating.
 */
public class Context {
    public User user;
    public Course course;
}
