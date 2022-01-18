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

    public void assign(Course course) throws CreationFailedException {
        try {
            this.enrollmentRepository.assign((Faculty) this.user, course);
            this.user.addCourse(course);
        } catch (SQLException e) {
            throw new CreationFailedException();
        }
    }

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

    public List<Course> getCourses() {
        return this.user.getCourses();
    }

    public String getUserName() {
        return this.user.getFirstName() + " " + this.user.getLastName();
    }

    public boolean isUserFaculty() {
        return this.user.getType().equals("faculty");
    }

    public User getUser() {
        return this.user;
    }

    public void removeCourse(Course course) {
        this.user.removeCourse(course);
    }
}
