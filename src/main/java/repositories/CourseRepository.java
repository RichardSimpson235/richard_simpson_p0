package main.java.repositories;

import main.java.models.Course;
import main.java.models.Faculty;
import main.java.models.Student;
import main.java.structures.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRepository extends AbstractRepository {

    public CourseRepository(Connection connection) {
        super(connection);
    }

    /**
     * This method is used to create a Course in the database. It gets back the id the
     * database created for the course and updates the input course's courseId.
     *
     * @param course The course to be created
     * @return whether the creation succeeded or failed
     * @throws SQLException thrown by JDBC when an error occurs with the database
     */
    public boolean create(Course course) throws SQLException {
        String sql = "INSERT INTO courses VALUES (default, ?, ?, ?, ?, ?) RETURNING course_id;";
        PreparedStatement query = this.connection.prepareStatement(sql);
        query.setString(1, course.getName());
        query.setString(2, course.getDescription());
        query.setLong(3, course.getEnrollmentStartDate());
        query.setLong(4, course.getEnrollmentEndDate());
        query.setInt(5, course.getCredits());

        ResultSet rs = query.executeQuery();

        if(rs.next()) {
            course.setCourseId(rs.getInt("course_id"));
            rs.close();

            return true;
        } else {
            throw new SQLException("Course creation failed.");
        }
    }

    /**
     * Deletes the provided course from the database. Because the database makes use of
     * the ON DELETE CASCADE constraint, any enrollments in the enrollment table
     * are deleted as a consequence of this action
     *
     * @param course the course to be deleted
     * @return whether the deletion succeeded or failed
     * @throws SQLException thrown by JDBC when an error occurs with the database
     */
    public boolean delete(Course course) throws SQLException {
        String sql = "DELETE FROM courses WHERE course_id = ?;";
        PreparedStatement query = this.connection.prepareStatement(sql);
        query.setInt(1, course.getCourseId());

        int rows = query.executeUpdate();

        if(rows > 0) {
            return true;
        } else {
            throw new SQLException("Course deletion failed.");
        }
    }

    /**
     * Used to modify a course in the database. Obtains the course by id.
     *
     * @param course the new course data
     * @return whether the update succeeded or failed
     * @throws SQLException thrown by JDBC when an error occurs with the database
     */
    public boolean edit(Course course) throws SQLException {
        String sql = "UPDATE courses SET name = ?, description = ?, enrollment_start = ?, enrollment_end = ?, credits = ? WHERE course_id = ?;";
        PreparedStatement query = this.connection.prepareStatement(sql);
        query.setString(1, course.getName());
        query.setString(2, course.getDescription());
        query.setLong(3, course.getEnrollmentStartDate());
        query.setLong(4, course.getEnrollmentEndDate());
        query.setInt(5, course.getCredits());
        query.setInt(6, course.getCourseId());

        int rows = query.executeUpdate();
        if(rows == 1) {
            return true;
        } else {
            throw new SQLException("Course update failed.");
        }
    }

    /**
     * Returns all of the courses in the database. The first table combines
     * the information from the student and course tables to get all the
     * students enrolled in a course. The second table combines the courses
     * and which faculty members are teaching them. This information is
     * extracted to create the course objects for the list.
     *
     * @return a list of course objects with the data from the database
     * @throws SQLException thrown by JDBC when an error occurs with the database
     */
    public List<Course> getAllCourses() throws SQLException {
        String coursesSql = "WITH full_students AS (" +
                "SELECT * FROM students INNER JOIN users USING (user_id)" +
                ")," +
                "classes AS (" +
                "SELECT * FROM courses LEFT JOIN enrollments USING (course_id)" +
                ")" +
                "SELECT * FROM classes LEFT JOIN full_students USING (student_id) ORDER BY course_id;";

        String instructorSql = "WITH employees AS (" +
                "SELECT * FROM faculty INNER JOIN users USING (user_id)" +
                ")" +
                "SELECT * FROM employees INNER JOIN assignments USING (employee_id) ORDER BY course_id;";

        PreparedStatement courseStatement = this.connection.prepareStatement(coursesSql);
        PreparedStatement instructorStatement = this.connection.prepareStatement(instructorSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet courseSet = courseStatement.executeQuery();
        ResultSet facultySet = instructorStatement.executeQuery();

        List<Course> courses = new List<>();
        Course currentCourse = null;
        while(courseSet.next()) {
            if (currentCourse != null && currentCourse.getCourseId() == courseSet.getInt("course_id")) {
                // we are adding a new student to the students list
                Student student = new Student();
                student.setUserId(courseSet.getInt("user_id"));
                student.setFirstName(courseSet.getString("first_name"));
                student.setLastName(courseSet.getString("last_name"));
                student.setEmail(courseSet.getString("email"));
                student.setDateOfBirth(courseSet.getLong("date_of_birth"));
                student.setUsername(courseSet.getString("username"));
                student.setPassword(courseSet.getString("password"));
                student.setMealPlanTier(courseSet.getInt("meal_plan_tier"));
                student.setMajor(courseSet.getString("major"));

                currentCourse.addStudent(student);
            } else {
                currentCourse = new Course();
                currentCourse.setCourseId(courseSet.getInt("course_id"));
                currentCourse.setName(courseSet.getString("name"));
                currentCourse.setDescription(courseSet.getString("description"));
                currentCourse.setEnrollmentStartDate(courseSet.getLong("enrollment_start"));
                currentCourse.setEnrollmentEndDate(courseSet.getLong("enrollment_end"));
                currentCourse.setCredits(courseSet.getInt("credits"));

                if(courseSet.getInt("student_id") != 0) {
                    Student student = new Student();
                    student.setUserId(courseSet.getInt("user_id"));
                    student.setFirstName(courseSet.getString("first_name"));
                    student.setLastName(courseSet.getString("last_name"));
                    student.setEmail(courseSet.getString("email"));
                    student.setDateOfBirth(courseSet.getLong("date_of_birth"));
                    student.setUsername(courseSet.getString("username"));
                    student.setPassword(courseSet.getString("password"));
                    student.setMealPlanTier(courseSet.getInt("meal_plan_tier"));
                    student.setMajor(courseSet.getString("major"));

                    currentCourse.addStudent(student);
                }


                Faculty faculty = new Faculty();
                while(facultySet.next()) {
                    if(facultySet.getInt("course_id") == currentCourse.getCourseId()) {
                        faculty.setUserId(facultySet.getInt("user_id"));
                        faculty.setFirstName(facultySet.getString("first_name"));
                        faculty.setLastName(facultySet.getString("last_name"));
                        faculty.setEmail(facultySet.getString("email"));
                        faculty.setDateOfBirth(facultySet.getLong("date_of_birth"));
                        faculty.setUsername(facultySet.getString("username"));
                        faculty.setPassword(facultySet.getString("password"));
                        faculty.setSalary(facultySet.getInt("salary"));
                        faculty.setDepartment(facultySet.getString("department"));

                        // reposition cursor to start before first row to loop through again
                        facultySet.beforeFirst();
                        break;
                    }
                }

                currentCourse.setProfessor(faculty);
                courses.add(currentCourse);
            }
        }

        return courses;
    }
}