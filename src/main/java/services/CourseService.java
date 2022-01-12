package main.java.services;

import main.java.exceptions.FailedEnrollmentException;
import main.java.exceptions.InvalidDataException;
import main.java.models.Course;
import main.java.repositories.CourseRepository;
import main.java.structures.List;

public class CourseService {

    private final ContextService contextService;
    private final CourseRepository courseRepository;

    public CourseService(ContextService contextService, CourseRepository courseRepository) {
        this.contextService = contextService;
        this.courseRepository = courseRepository;
    }

    public void createCourse(String courseName, String description, long enrollmentPeriodStart, long enrollmentPeriodEnd) {
        this.courseRepository.create(courseName, description, enrollmentPeriodStart, enrollmentPeriodEnd);
    }

    public void deleteCourse() {
        this.courseRepository.delete(this.contextService.getContext().course);
    }

    public void editCourse(String fieldName, String fieldData) throws InvalidDataException {
        this.courseRepository.edit(this.contextService.getContext().course, fieldName, fieldData);
    }

    public void enrollUser() throws FailedEnrollmentException {
        this.courseRepository.enrollUser(this.contextService.getContext().user, this.contextService.getContext().course);
    }

    public void unenrollUser() throws FailedEnrollmentException {
        this.courseRepository.unenrollUser(this.contextService.getContext().user, this.contextService.getContext().course);
    }

    public Course getCourse() {
        return this.contextService.getContext().course;
    }

    public List<Course> getAllCourses() {
        return this.courseRepository.getAllCourses();
    }

    public void selectCourse(int index) {
        contextService.setCourse(getAllCourses().get(index));
    }

    public boolean isUserFaculty() {
        return this.contextService.getContext().user.isFaculty();
    }

    public boolean isUserEnrolled() {
        Course course = this.contextService.getContext().course;
        List<Course> enrolledCourses = this.contextService.getContext().user.getCourses();

        return enrolledCourses.contains(course);
    }
}
