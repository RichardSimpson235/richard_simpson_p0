package main.java.services;

import main.java.exceptions.*;
import main.java.models.Course;
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

    public void selectCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return this.course;
    }

    public List<Course> getAllCourses() throws CourseRetrievalException {
        try {
            return this.courseRepository.getAllCourses();
        } catch (SQLException e) {
            throw new CourseRetrievalException();
        }
    }
}
