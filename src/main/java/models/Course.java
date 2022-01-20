package main.java.models;

import main.java.structures.List;

public class Course {

    private int courseId;
    private String name;
    private String description;
    private long enrollmentStartDate;
    private long enrollmentEndDate;
    private int credits;
    private Faculty professor;
    private final List<Student> students;

    public Course() {
        students = new List<>();
    }

    /**
     * Returns the id of this course object
     *
     * @return the id of this course
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Sets the id of this course object
     *
     * @param courseId the new id for this course object
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**
     * Returns the name of this course object
     *
     * @return the name of this course object
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this course object
     *
     * @param name the name of this course object
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of this course object
     *
     * @return the description of this course object
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this course object
     *
     * @param description the new description of this course object
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the long version of the enrollment start date of this course object
     *
     * @return the long version of the enrollment start date of this course object
     */
    public long getEnrollmentStartDate() {
        return enrollmentStartDate;
    }

    /**
     * Sets the long version of the enrollment start date of this course object
     *
     * @param enrollmentStartDate the new long version of the enrollment start date of this course object
     */
    public void setEnrollmentStartDate(long enrollmentStartDate) {
        this.enrollmentStartDate = enrollmentStartDate;
    }

    /**
     * Returns the long version of the enrollment end date of this course object
     *
     * @return the long version of the enrollment end date of this course object
     */
    public long getEnrollmentEndDate() {
        return enrollmentEndDate;
    }

    /**
     * Sets the long version of the enrollment end date of this course object
     *
     * @param enrollmentEndDate the new long version of the enrollment end date of this course object
     */
    public void setEnrollmentEndDate(long enrollmentEndDate) {
        this.enrollmentEndDate = enrollmentEndDate;
    }

    /**
     * Returns the number of credits this course gives
     *
     * @return the number of credits this course gives
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Sets the number of credits this course gives
     *
     * @param credits the number of credits this course gives
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Returns the Faculty object representing who teaches this course
     *
     * @return the Faculty object representing who teaches this course
     */
    public Faculty getProfessor() {
        return professor;
    }

    /**
     * Sets the Faculty object representing who teaches this course
     *
     * @param professor the Faculty object representing who teaches this course
     */
    public void setProfessor(Faculty professor) {
        this.professor = professor;
    }

    /**
     * Gets a list of students who are taking this course
     *
     * @return a list of students who are taking this course
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Adds a new student to the list of students taking the course
     *
     * @param student the student to be added
     */
    public void addStudent(Student student) {
        this.students.add(student);
    }

    /**
     * Removes a student from the list of students taking the course
     *
     * @param student the student to be removed
     */
    public void removeStudent(Student student) {
        this.students.remove(student);
    }

    /**
     * Returns a boolean that can be used to test whether the given faculty object
     * is the professor of this course or not.
     *
     * @param faculty the faculty object in question
     * @return a boolean saying if the faculty is the professor of this course
     */
    public boolean isProfessor(Faculty faculty) {
        return faculty.getUserId() == this.professor.getUserId();
    }

    /**
     * Returns a boolean that can be used to test whether the given student object
     * is enrolled in this course
     *
     * @param student the student object in question
     * @return a boolean saying if the student is enrolled in this course
     */
    public boolean isEnrolled(Student student) {
        return this.students.contains(student);
    }

    /**
     * Returns a boolean that can be used to test whether the given field name
     * is a field of this object
     *
     * @param fieldName the name of the field in question
     * @return a boolean saying if the given field is a field
     */
    public boolean isValidField(String fieldName) {
        switch(fieldName.toLowerCase()) {
            case "name":
            case "description":
            case "start":
            case "end":
            case "credits":
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns a boolean that can be used to tell if this course object
     * is the same as another. The equality test is done by comparing their
     * course ids.
     *
     * @param obj the object in question
     * @return a boolean saying whether the objects are the same
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof Course)) {
            return false;
        }

        Course o = (Course) obj;

        return o.getCourseId() == this.courseId;
    }
}
