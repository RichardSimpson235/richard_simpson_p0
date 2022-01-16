package main.java.models;

import main.java.structures.List;

public abstract class User {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private long dateOfBirth;
    private String username;
    private String password;
    List<Course> courses;

    public User() {
        courses = new List<>();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addCourse(Course course) {
        if(!this.courses.contains(course)) {
            this.courses.add(course);
        }
    }

    public void removeCourse(Course course) {
        if(this.courses.contains(course)) {
            this.courses.remove(course);
        }
    }

    public abstract String getType();
}