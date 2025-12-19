package com.student.model;

public class Course {
    private int id;
    private String courseName;
    private int studentCount;

    public Course() {}

    public Course(int id, String courseName) {
        this.id = id;
        this.courseName = courseName;
    }

    public Course(int id, String courseName, int studentCount) {
        this.id = id;
        this.courseName = courseName;
        this.studentCount = studentCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }
}
