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
    private List<Course> courses;

    public User() {
        courses = new List<>();
    }

    /**
     * Returns the user id of this object
     * @return the user id of this object
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user id of this object
     *
     * @param userId the new user id of this object
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the first name of this user
     *
     * @return the first name of this user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of this user
     *
     * @param firstName the first name of this user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of this user
     * @return the last name of this user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of this user
     *
     * @param lastName the last name of this user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the email of this user
     * @return the email of this user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of this user
     *
     * @param email the email of this user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the date of birth of this user as a long
     *
     * @return the date of birth of this user as a long
     */
    public long getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of this user as a long
     *
     * @param dateOfBirth the date of birth of this user as a long
     */
    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns the username of this user
     *
     * @return the username of this user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of this user
     *
     * @param username the username of this user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of this user
     *
     * @return the password of this user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of this user
     *
     * @param password the username of this user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Adds a course to this users courses. If they
     * are a Faculty member they teach it, if they're a
     * student, they are enrolled in it.
     *
     * @param course the new course to be added
     */
    public void addCourse(Course course) {
        if(!this.courses.contains(course)) {
            this.courses.add(course);
        }
    }

    /**
     * Removes a course to this users courses. If they
     * are a Faculty member they teach it, if they're a
     * student, they are enrolled in it.
     *
     * @param course the new course to be added
     */
    public void removeCourse(Course course) {
        if(this.courses.contains(course)) {
            this.courses.remove(course);
        }
    }

    /**
     * Returns all of the courses the user is
     * involved with
     *
     * @return the courses relevant to the user
     */
    public List<Course> getCourses() {
        return this.courses;
    }


    /**
     * Used to test equality between users. Users are equal
     * if their user ids are equal.
     *
     * @param obj the object in question
     * @return represents whether the objects were eaual
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof User)) {
            return false;
        }

        User o = (User) obj;

        return o.getUserId() == this.userId;
    }

    /**
     * Used by other parts of the application to know what kind of
     * user this is.
     *
     * @return a string representation of the type of this object
     */
    public abstract String getType();
}
