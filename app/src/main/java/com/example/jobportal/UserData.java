package com.example.jobportal;

public class UserData {
    private String fullName;
    private String lastName;
    private String dob;
    private int currentSemester;
    private double cgpa;
    private int passOutYear;
    private String mobileEmail;
    private String address;
    private String interest;
    private String type;
    private String username;
    private String email;
    public UserData() {
        // Default constructor required for DataSnapshot.getValue(UserData.class)
    }

    public UserData(String fullName, String lastName, String dob, int currentSemester, double cgpa, int passOutYear, String address, String interest, String type, String username, String email) {
        this.fullName = fullName;
        this.lastName = lastName;
        this.dob = dob;
        this.currentSemester = currentSemester;
        this.cgpa = cgpa;
        this.passOutYear = passOutYear;
        this.address = address;
        this.interest = interest;
        this.type = type;
        this.username = username;
        this.email = email;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setdob(String dob) {
        this.dob = dob;
    }
    public String getemail() {
        return email;
    }
    public void setemail(String email) {
        this.dob = email;
    }
    public String getdob() {
        return dob;
    }
    public void getCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }
    public int getCurrentSemester() {
        return currentSemester;
    }
    public void getCgpa(double cgpa) {
        this.cgpa = cgpa;
    }
    public double getCgpa() {
        return cgpa;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }
    public void setPassOutYear(int passOutYear) {
        this.passOutYear = passOutYear;
    }
    public int getPassOutYear() {
        return passOutYear;
    }
    public void setInterest(String interest) {
        this.interest = interest;
    }
    public String getInterest() {
        return interest;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setUserName(String username) {
        this.username = username;
    }
    public String getUserName() {
        return username;
    }
    // Define getters and setters for other fields in a similar manner.
}
