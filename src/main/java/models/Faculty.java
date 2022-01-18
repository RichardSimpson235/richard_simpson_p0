package main.java.models;

public class Faculty extends User {

    private int employeeId;
    private int salary;
    private String department;

    /**
     * Returns the faculty member's id
     *
     * @return the faculty member's id
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the faculty member's id
     * @param employeeId faculty member's new id
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Returns the salary of this faculty member
     *
     * @return the salary of this faculty member
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Sets the salary of this faculty member object
     *
     * @param salary the new salary
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * Returns the department of this faculty member
     *
     * @return the department of this faculty member
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the department of this faculty member
     *
     * @param department the department of this faculty member
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Used by other parts of the application to know what kind of
     * user this is.
     *
     * @return a string representation of the type of this object
     */
    @Override
    public String getType() {
        return "faculty";
    }



}
