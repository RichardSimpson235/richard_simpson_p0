package main.java.services;

import main.java.exceptions.*;
import main.java.models.Course;
import main.java.models.Student;
import main.java.repositories.CourseRepository;
import main.java.structures.List;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CourseService {

    private final CourseRepository courseRepository;
    private Course course;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * This method creates a course object.
     *
     * @param name              name of the course
     * @param description       descriptoin of the course
     * @param enrollmentStart   enrollment start date as a long
     * @param enrollmentEnd     enrollment end date as a long
     * @param credits           number of credits the course is worth
     * @throws CreationFailedException thrown when the database fails to create the course
     */
    public void createCourse(String name, String description, long enrollmentStart, long enrollmentEnd, int credits) throws CreationFailedException {

        this.course = new Course();

        course.setName(name);
        course.setDescription(description);
        course.setEnrollmentStartDate(enrollmentStart);
        course.setEnrollmentEndDate(enrollmentEnd);
        course.setCredits(credits);

        try {
            if(!this.courseRepository.create(this.course)) {
                throw new CreationFailedException();
            }
        } catch (SQLException e) {
            throw new CreationFailedException();
        }
    }

    /**
     * This method deletes the course in the course field of this object.
     *
     * @throws DeletionFailedException thrown when the database fails to delete the course
     */
    public void deleteCourse() throws DeletionFailedException {
        try {
            if (!this.courseRepository.delete(this.course)) {
                throw new DeletionFailedException();
            }

            this.course = null;
        } catch (SQLException e) {
            throw new DeletionFailedException();
        }
    }

    /**
     * This method is used to update the currently selected course (the course in the course field of this
     * object)
     *
     * @param fieldName                     name of the field being edited
     * @param fieldData                     data of the field being edited
     * @throws ParseException               thrown when the user enters something doesn't match the date format
     * @throws InvalidFieldException        thrown when the user enters a field name that doesn't match
     * @throws CourseModificationException  thrown when the database is unable to update the course
     * @throws NumberFormatException        thrown when the user enters something that can't be parsed as an integer
     */
    public void editCourse(String fieldName, String fieldData) throws ParseException, InvalidFieldException, CourseModificationException, NumberFormatException {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        switch(fieldName.toLowerCase()) {
            case "name":
                this.course.setName(fieldData);
                break;
            case "description":
                this.course.setDescription(fieldData);
                break;
            case "start":
                this.course.setEnrollmentStartDate(formatter.parse(fieldData).getTime());
                break;
            case "end":
                this.course.setEnrollmentEndDate(formatter.parse(fieldData).getTime());
                break;
            case "credits":
                this.course.setCredits(Integer.parseInt(fieldData));
                break;
            default:
                throw new InvalidFieldException();
        }

        try {
            this.courseRepository.edit(this.course);
        } catch (SQLException e) {
            throw new CourseModificationException();
        }
    }

    /**
     * This method sets the course field of this object,
     * establish a context for the application to know which
     * course we are operating on.
     *
     * @param course the course to be select
     */
    public void selectCourse(Course course) {
        this.course = course;
    }

    /**
     * Returns the currently selected course (ie the current course context)
     *
     * @return the course that is selected
     */
    public Course getCourse() {
        return this.course;
    }

    /**
     * Returns a list of all courses in the database
     *
     * @return                          list of all courses in the database
     * @throws CourseRetrievalException thrown when the database fails to retrieve the courses
     */
    public List<Course> getAllCourses() throws CourseRetrievalException {
        try {
            return this.courseRepository.getAllCourses();
        } catch (SQLException e) {
            throw new CourseRetrievalException();
        }
    }

    /**
     * Adds a student to the current course context's student list
     *
     * @param student the student to be added
     */
    public void addStudent(Student student) {
        this.course.addStudent(student);
    }

    /**
     * Removes a student from the current course context's student list
     *
     * @param student the student to be removed
     */
    public void removeStudent(Student student) {
        this.course.removeStudent(student);
    }
}
