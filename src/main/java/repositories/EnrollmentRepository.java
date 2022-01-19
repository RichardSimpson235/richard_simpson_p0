package main.java.repositories;

import main.java.models.Course;
import main.java.models.Faculty;
import main.java.models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrollmentRepository extends AbstractRepository {

    public EnrollmentRepository(Connection connection) {
        super(connection);
    }

    /**
     * This method creates a record in the junction table between faculty
     * and courses, thereby assigning a teacher to the course.
     *
     * @param faculty the faculty member who will be teaching this course
     * @param course the course being taught
     * @return whether the assignment succeeded or failed
     * @throws SQLException thrown by JDBC when an error occurs with the database
     */
    public boolean assign(Faculty faculty, Course course) throws SQLException {
        String sql = "INSERT INTO assignments VALUES (?, ?);";
        PreparedStatement query = this.connection.prepareStatement(sql);
        query.setInt(1, faculty.getEmployeeId());
        query.setInt(2, course.getCourseId());

        int row = query.executeUpdate();

        if(row == 1) {
            return true;
        } else {
            throw new SQLException("Assignment creation failed.");
        }
    }

    /**
     * This method creates a record in the junction table between students
     * and courses, thereby enrolling the student in the course.
     *
     * @param student the student enrolling in the course
     * @param course the course being enrolled in
     * @return whether the enrollment succeeded or failed
     * @throws SQLException thrown by JDBC when an error occurs with the database
     */
    public boolean create(Student student, Course course) throws SQLException {
        String sql = "INSERT INTO enrollments VALUES (?, ?);";
        PreparedStatement query = this.connection.prepareStatement(sql);
        query.setInt(1, student.getStudentId());
        query.setInt(2, course.getCourseId());

        int row = query.executeUpdate();

        if(row == 1) {
            return true;
        } else {
            throw new SQLException("Enrollment creation failed.");
        }
    }

    /**
     * This method removes a record in the junction table between students
     * and courses, thereby unenrolling the student in the course.
     *
     * @param student the student unenrolling in the course
     * @param course the course being unenrolled from
     * @return whether the unenrollment succeeded or failed
     * @throws SQLException thrown by JDBC when an error occurs with the database
     */
    public boolean delete(Student student, Course course) throws SQLException {
        String sql = "DELETE FROM enrollments USING students " +
                     "WHERE students.student_id = enrollments.student_id " +
                     "AND enrollments.student_id = ? AND enrollments.course_id = ?;";
        PreparedStatement query = this.connection.prepareStatement(sql);
        query.setInt(1, student.getStudentId());
        query.setInt(2, course.getCourseId());

        int rows = query.executeUpdate();

        if(rows == 1) {
            return true;
        } else {
            throw new SQLException("Enrollment creation failed.");
        }
    }
}
