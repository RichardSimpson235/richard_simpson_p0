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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEnrollmentStartDate() {
        return enrollmentStartDate;
    }

    public void setEnrollmentStartDate(long enrollmentStartDate) {
        this.enrollmentStartDate = enrollmentStartDate;
    }

    public long getEnrollmentEndDate() {
        return enrollmentEndDate;
    }

    public void setEnrollmentEndDate(long enrollmentEndDate) {
        this.enrollmentEndDate = enrollmentEndDate;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Faculty getProfessor() {
        return professor;
    }

    public void setProfessor(Faculty professor) {
        this.professor = professor;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }

    public boolean isProfessor(Faculty faculty) {
        return faculty.getUserId() == this.professor.getUserId();
    }

    public boolean isEnrolled(Student student) {
        return this.students.contains(student);
    }
}
