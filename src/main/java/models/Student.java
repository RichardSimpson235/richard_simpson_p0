package main.java.models;

public class Student extends User {

    private int studentId;
    private int mealPlanTier;
    private String major;

    /**
     * Returns the id of this student
     *
     * @return the id of this student
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Sets the id of this student
     *
     * @param studentId the new student id
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * Returns the meal plan tier of this student
     *
     * @return the meal plan tier of this student
     */
    public int getMealPlanTier() {
        return mealPlanTier;
    }

    /**
     * Sets the meal plan tier of the new meal plan tier of this student
     *
     * @param mealPlanTier the new meal plan tier of this student
     */
    public void setMealPlanTier(int mealPlanTier) {
        this.mealPlanTier = mealPlanTier;
    }

    /**
     * Returns the major of this student
     *
     * @return the major of this student
     */
    public String getMajor() {
        return major;
    }

    /**
     * Sets the major of this student
     *
     * @param major the new major of this student
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * Used by other parts of the application to know what kind of
     * user this is.
     *
     * @return a string representation of the type of this object
     */
    @Override
    public String getType() {
        return "student";
    }
}
