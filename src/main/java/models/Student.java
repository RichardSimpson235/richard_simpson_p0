package main.java.models;

public class Student extends User {

    private int studentId;
    private int mealPlanTier;
    private String major;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getMealPlanTier() {
        return mealPlanTier;
    }

    public void setMealPlanTier(int mealPlanTier) {
        this.mealPlanTier = mealPlanTier;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String getType() {
        return "student";
    }
}
