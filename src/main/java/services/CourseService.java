package main.java.services;

import main.java.exceptions.FailedEnrollmentException;
import main.java.repositories.CourseRepository;

public class CourseService {

    private final ContextService contextService;
    private final CourseRepository courseRepository;

    public CourseService(ContextService contextService, CourseRepository courseRepository) {
        this.contextService = contextService;
        this.courseRepository = courseRepository;
    }

    public void createCourse(String courseName, String description, long enrollmentPeriod) {
        this.courseRepository.create(courseName, description, enrollmentPeriod);
    }

    public void deleteCourse() {
        this.courseRepository.delete(this.contextService.getContext().course);
    }

    public void editCourse(String fieldName, String fieldData) {
        this.courseRepository.edit(this.contextService.getContext().course, fieldName, fieldData);
    }

    public void enrollUser() throws FailedEnrollmentException {
        this.courseRepository.enrollUser(this.contextService.getContext().user, this.contextService.getContext().course);
    }

    public void unenrollUser() throws FailedEnrollmentException {
        this.courseRepository.unenrollUser(this.contextService.getContext().user, this.contextService.getContext().course);
    }
}
