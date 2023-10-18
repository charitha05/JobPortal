package com.example.jobportal;

public class CompanyDetails {
    private String companyName;
    private int yearOfBatch;
    private String role;
    private double ctc;
    private String applyByDate;
    private String type;
    private String status;

    public CompanyDetails() {
        // Default constructor required for Firebase
    }

    public CompanyDetails(String companyName, int yearOfBatch, String role, double ctc, String applyByDate, String type, String status) {
        this.companyName = companyName;
        this.yearOfBatch = yearOfBatch;
        this.role = role;
        this.ctc = ctc;
        this.applyByDate = applyByDate;
        this.type = type;
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getYearOfBatch() {
        return yearOfBatch;
    }

    public String getRole() {
        return role;
    }

    public double getCtc() {
        return ctc;
    }

    public String getApplyByDate() {
        return applyByDate;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }
}
