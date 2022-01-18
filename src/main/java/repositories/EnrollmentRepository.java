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
