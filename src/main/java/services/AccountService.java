package main.java.services;

import main.java.exceptions.*;
import main.java.models.Course;
import main.java.models.Faculty;
import main.java.models.Student;
import main.java.models.User;
import main.java.repositories.CourseRepository;
import main.java.repositories.EnrollmentRepository;
import main.java.repositories.UserRepository;
import main.java.structures.List;

import java.sql.SQLException;
import java.util.Date;

public class AccountService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private User user;

    public AccountService(UserRepository userRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    /**
     * Uses the EnrollmentRepository to save the assignment to the database and updates
     * the User object to have this course.
     *
     * @param course the course to be assigned to
     * @throws CreationFailedException this is thrown when the database fails to assign the user
     */
    public void assign(Course course) throws CreationFailedException {
        try {
            this.enrollmentRepository.assign((Faculty) this.user, course);
            this.user.addCourse(course);
        } catch (SQLException e) {
            throw new CreationFailedException();
        }
    }

    /**
     * This method authenticates someone trying to log into the system. It returns a string
     * that tells the system what type of user they are so that it can navigate to the correct page.
     * The user's information is saved in this class' user field.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return         a string representing the type of user
     * @throws MissingAccountException thrown when there is no user found with that username and password
     */
    public String authenticate(String username, String password) throws MissingAccountException {
        try {
            this.user = this.userRepository.get(username, password);

            if(this.user == null) {
                throw new MissingAccountException();
            }

            // Need to populate course lists in the user
            String userType = user.getType();
            List<Course> allCourses = courseRepository.getAllCourses();
            for (int i = 0; i < allCourses.size(); i++) {
                if(userType.equals("faculty")) {
                    if(allCourses.get(i).isProfessor((Faculty) this.user)) {
                        this.user.addCourse(allCourses.get(i));
                    }
                } else {
                    if(allCourses.get(i).isEnrolled((Student) this.user)) {
                        this.user.addCourse(allCourses.get(i));
                    }
                }
            }

            return userType;
        } catch (SQLException e) {
            throw new MissingAccountException();
        }
    }

    /**
     * This method is used to register a new Student in the system. It uses the UserRepository to
     * save the data to the database.
     *
     * @param firstName       the first name of the student
     * @param lastName        the last name of the student
     * @param email           the email of the student
     * @param dateOfBirth     the date of birth of the student, as a long
     * @param username        the username of the student
     * @param password        the password of the student
     * @param mealPlanTier    which meal plan tier they chose
     * @param major           the major of the student
     * @throws RegistrationFailedException thrown when the database isn't able to register the user
     */
    public void register(String firstName, String lastName, String email, long dateOfBirth, String username, String password, int mealPlanTier, String major) throws RegistrationFailedException {

        Student student = new Student();

        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setDateOfBirth(dateOfBirth);
        student.setUsername(username);
        student.setPassword(password);
        student.setMealPlanTier(mealPlanTier);
        student.setMajor(major);

        try {
            if(!this.userRepository.create(student)) {
                throw new RegistrationFailedException();
            }
        } catch (SQLException e) {
            throw new RegistrationFailedException();
        }
    }

    /**
     * This method is used to enroll a user in a course. It checks to see if the enrollment
     * period has passed and throws an EnrollmentRangeException if it has, otherwise it calls
     * the EnrollmentRepository to enroll then user.
     *
     * @param course the course that is being enrolled in
     * @throws EnrollmentFailedException thrown when the database is unable to enroll the user
     * @throws EnrollmentRangeException  thrown when the user tries to enroll in a course whose
     * enrollment period has ended
     */
    public void enroll(Course course) throws EnrollmentFailedException, EnrollmentRangeException {
        try {
            Date today = new Date();
            if(today.getTime() > course.getEnrollmentStartDate() && today.getTime() < course.getEnrollmentEndDate()) {
                this.enrollmentRepository.create((Student) this.user, course);
                this.user.addCourse(course);
            } else {
                throw new EnrollmentRangeException();
            }
        } catch (SQLException e) {
            throw new EnrollmentFailedException();
        }
    }

    /**
     * This method is used to unenroll a user in a course. It checks to see if the enrollment
     * period has passed and throws an EnrollmentRangeException if it has, otherwise it calls
     * the EnrollmentRepository to unenroll then user.
     *
     * @param course the course that is being unenrolled from
     * @throws EnrollmentFailedException thrown when the database is unable to unenroll the user
     * @throws EnrollmentRangeException  thrown when the user tries to enroll in a course whose
     * enrollment period has ended
     */
    public void unenroll(Course course) throws EnrollmentFailedException, EnrollmentRangeException {
        try {
            Date today = new Date();
            if(today.getTime() > course.getEnrollmentStartDate() && today.getTime() < course.getEnrollmentEndDate()) {
                this.enrollmentRepository.delete((Student) this.user, course);
                this.user.removeCourse(course);
            } else {
                throw new EnrollmentRangeException();
            }
        } catch (SQLException e) {
            throw new EnrollmentFailedException();
        }
    }

    /**
     * This is used to get all the courses the user is either teaching or is enrolled in
     *
     * @return the courses this user is either teaching or is enrolled in
     */
    public List<Course> getCourses() {
        return this.user.getCourses();
    }

    /**
     * This is used to get the name of the user
     *
     * @return the name of the user
     */
    public String getUserName() {
        return this.user.getFirstName() + " " + this.user.getLastName();
    }

    /**
     * Represents whether the user is faculty or a student
     *
     * @return whether the user is a faculty member or not
     */
    public boolean isUserFaculty() {
        return this.user.getType().equals("faculty");
    }

    /**
     * Gets the current user of the application
     *
     * @return the user of the application
     */
    public User getUser() {
        return this.user;
    }


    /**
     * Called by the View, removes a course from the user's course list.
     *
     * @param course the course to be removed
     */
    public void removeCourse(Course course) {
        this.user.removeCourse(course);
    }
}
