package model;

public class StudentResult {
    private int sid;
    private String sname;
    private int year;
    private String semester;
    private String subjectName;
    private double totalWeightedScore;
    private String status;

    public StudentResult(int sid, String sname, int year, String semester, String subjectName, double totalWeightedScore, String status) {
        this.sid = sid;
        this.sname = sname;
        this.year = year;
        this.semester = semester;
        this.subjectName = subjectName;
        this.totalWeightedScore = totalWeightedScore;
        this.status = status;
    }

    public StudentResult() {
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public double getTotalWeightedScore() {
        return totalWeightedScore;
    }

    public void setTotalWeightedScore(double totalWeightedScore) {
        this.totalWeightedScore = totalWeightedScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}