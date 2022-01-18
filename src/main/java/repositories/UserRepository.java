package main.java.repositories;

import main.java.models.Faculty;
import main.java.models.Student;
import main.java.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository extends AbstractRepository {

    public UserRepository(Connection connection) {
        super(connection);
    }

    public boolean create(Student student) throws SQLException {
        String sql = "INSERT INTO users VALUES (default, ?, ? ,?, ?, ?, ?) RETURNING user_id;";
        PreparedStatement query = this.connection.prepareStatement(sql);
        query.setString(1, student.getFirstName());
        query.setString(2, student.getLastName());
        query.setString(3, student.getEmail());
        query.setLong(4, student.getDateOfBirth());
        query.setString(5, student.getUsername());
        query.setString(6, student.getPassword());

        ResultSet rs = query.executeQuery();

        if(rs.next()) {
            sql = "INSERT INTO students VALUES (default, ?, ?, ?);";
            query = this.connection.prepareStatement(sql);
            query.setInt(1, student.getMealPlanTier());
            query.setString(2, student.getMajor());
            query.setInt(3, rs.getInt("user_id"));

            int rows = query.executeUpdate();
            rs.close();
            return rows == 1;
        } else {
            throw new SQLException("Student creation failed.");
        }
    }

    public User get(String username, String password) throws SQLException {
        String sql = "WITH users_faculty AS (" +
                     "SELECT * FROM users LEFT JOIN faculty USING (user_id)" +
                     ")" +
                     "SELECT * FROM users_faculty LEFT JOIN students USING (user_id)" +
                     "WHERE username = ? AND password = ?";

        PreparedStatement query = this.connection.prepareStatement(sql);
        query.setString(1, username);
        query.setString(2, password);

        ResultSet rs = query.executeQuery();

        if(rs.next()) {
            //Should only have one user
            if(rs.getInt("employee_id") != 0) {
                return build(rs, "faculty");
            } else {
                return build(rs, "student");
            }
        }

        rs.close();
        return null;
    }

    private User build(ResultSet rs, String type) throws SQLException {
        User user;

        if(type.equals("faculty")) {
            Faculty faculty = new Faculty();
            faculty.setUserId(rs.getInt("user_id"));
            faculty.setFirstName(rs.getString("first_name"));
            faculty.setLastName(rs.getString("last_name"));
            faculty.setEmail(rs.getString("email"));
            faculty.setDateOfBirth(rs.getLong("date_of_birth"));
            faculty.setUsername(rs.getString("username"));
            faculty.setPassword(rs.getString("password"));
            faculty.setEmployeeId(rs.getInt("employee_id"));
            faculty.setSalary(rs.getInt("salary"));
            faculty.setDepartment(rs.getString("department"));

            user = faculty;
        } else {
            Student student = new Student();
            student.setUserId(rs.getInt("user_id"));
            student.setFirstName(rs.getString("first_name"));
            student.setLastName(rs.getString("last_name"));
            student.setEmail(rs.getString("email"));
            student.setDateOfBirth(rs.getLong("date_of_birth"));
            student.setUsername(rs.getString("username"));
            student.setPassword(rs.getString("password"));
            student.setStudentId(rs.getInt("student_id"));
            student.setMealPlanTier(rs.getInt("meal_plan_tier"));
            student.setMajor(rs.getString("major"));

            user = student;
        }

        rs.close();
        return user;
    }
}
