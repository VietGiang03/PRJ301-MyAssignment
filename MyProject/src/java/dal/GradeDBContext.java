package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Assessment;
import model.Exam;
import model.Grade;
import model.Student;
import model.StudentResult;

public class GradeDBContext extends DBContext<Grade> {

    public ArrayList<Grade> getGradesFromExamIds(ArrayList<Integer> eids) {
        ArrayList<Grade> grades = new ArrayList<>();
        PreparedStatement stm = null;
        try {
            String sql = "SELECT eid,sid,score FROM grades WHERE (1>2)";
            for (Integer eid : eids) {
                sql += " OR eid = ?";
            }

            stm = connection.prepareStatement(sql);

            for (int i = 0; i < eids.size(); i++) {
                stm.setInt((i + 1), eids.get(i));
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Grade g = new Grade();
                g.setScore(rs.getFloat("score"));

                Student s = new Student();
                s.setId(rs.getInt("sid"));
                g.setStudent(s);

                Exam e = new Exam();
                e.setId(rs.getInt("eid"));
                g.setExam(e);

                grades.add(g);
            }

        } catch (SQLException ex) {
            Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return grades;
    }

    public void insertGradesForCourse(int cid, ArrayList<Grade> grades) {
        // Delete only the grades related to the specific course
        String sql_remove = "DELETE FROM grades WHERE sid IN (SELECT sid FROM students_courses WHERE cid = ?) AND eid IN (SELECT eid FROM exams WHERE aid IN (SELECT aid FROM assesments WHERE subid = (SELECT subid FROM courses WHERE cid = ?)))";
        String sql_insert = "INSERT INTO [grades]\n"
                + "           ([eid]\n"
                + "           ,[sid]\n"
                + "           ,[score])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?)";

        PreparedStatement stm_remove = null;
        ArrayList<PreparedStatement> stm_inserts = new ArrayList<>();

        try {
            connection.setAutoCommit(false);
            stm_remove = connection.prepareStatement(sql_remove);
            stm_remove.setInt(1, cid);
            stm_remove.setInt(2, cid);
            stm_remove.executeUpdate();

            for (Grade grade : grades) {
                PreparedStatement stm_insert = connection.prepareStatement(sql_insert);
                stm_insert.setInt(1, grade.getExam().getId());
                stm_insert.setInt(2, grade.getStudent().getId());
                stm_insert.setFloat(3, grade.getScore());
                stm_insert.executeUpdate();
                stm_inserts.add(stm_insert);
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                stm_remove.close();
                for (PreparedStatement stm_insert : stm_inserts) {
                    stm_insert.close();
                }
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Grade getGradeByExamAndStudent(int eid, int sid) {
        String sql = "SELECT \n"
                + "    a.aname AS ExamName,\n"
                + "    e.[from] AS ExamTime,\n"
                + "    e.duration AS Duration,\n"
                + "    g.score AS Score\n"
                + "FROM \n"
                + "    dbo.grades g\n"
                + "INNER JOIN \n"
                + "    dbo.exams e ON g.eid = e.eid\n"
                + "INNER JOIN \n"
                + "    dbo.assesments a ON e.aid = a.aid\n"
                + "WHERE \n"
                + "    g.sid = ? AND g.eid = ?\n"; // Corrected query

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, sid);
            stm.setInt(2, eid);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Grade grade = new Grade();

                    // Setting exam details
                    Exam exam = new Exam();
                    exam.setId(eid); // Using eid parameter directly
                    exam.setFrom(rs.getTimestamp("ExamTime"));
                    exam.setDuration((int) rs.getFloat("Duration")); // Casting float to int

                    // Setting assessment details
                    Assessment assessment = new Assessment();
                    assessment.setName(rs.getString("ExamName"));

                    exam.setAssessment(assessment);

                    grade.setExam(exam);

                    // Setting student details
                    Student student = new Student();
                    student.setId(sid); // Using sid parameter directly

                    grade.setStudent(student);
                    grade.setScore(rs.getFloat("Score"));

                    return grade;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, "Database error while fetching grade by exam and student", ex);
        }

        return null;
    }

    // Method to get total weighted score and pass status
    public Result getTotalWeightedScoreAndStatus(int sid, int cid) {
        String sql = "SELECT "
                + "SUM(g.score * a.weight) AS totalWeightedScore, "
                + "CASE "
                + "    WHEN SUM(g.score * a.weight) >= 5 THEN 'Pass' "
                + "    ELSE 'Not Pass' "
                + "END AS Status "
                + "FROM grades g "
                + "JOIN exams e ON g.eid = e.eid "
                + "JOIN assesments a ON e.aid = a.aid "
                + "JOIN courses c ON a.subid = c.subid "
                + "WHERE g.sid = ? AND c.cid = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, sid);
            stm.setInt(2, cid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                float totalWeightedScore = rs.getFloat("totalWeightedScore");
                String status = rs.getString("Status");
                return new Result(totalWeightedScore, status);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GradeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new Result(0, "Not Pass");
    }

    // Inner class to hold the result
    public static class Result {
        private float totalWeightedScore;
        private String status;

        public Result(float totalWeightedScore, String status) {
            this.totalWeightedScore = totalWeightedScore;
            this.status = status;
        }

        public float getTotalWeightedScore() {
            return totalWeightedScore;
        }

        public String getStatus() {
            return status;
        }
    }


    public ArrayList<StudentResult> getStudentResults(int lecturerId, int courseId) {
    ArrayList<StudentResult> results = new ArrayList<>();
    try {
        String sql = "SELECT \n" +
                "    s.sid,\n" +
                "    s.sname,\n" +
                "    se.year,\n" +
                "    se.season AS semester,\n" +
                "    sub.subname AS subject_name,\n" +
                "    ROUND(SUM(g.score * a.weight), 2) AS total_score,\n" +
                "    CASE \n" +
                "        WHEN ROUND(SUM(g.score * a.weight), 2) > 5 THEN 'Pass'\n" +
                "        ELSE 'Fail'\n" +
                "    END AS status\n" +
                "FROM \n" +
                "    students s\n" +
                "JOIN \n" +
                "    students_courses sc ON s.sid = sc.sid\n" +
                "JOIN \n" +
                "    courses c ON sc.cid = c.cid\n" +
                "JOIN \n" +
                "    subjects sub ON c.subid = sub.subid\n" +
                "JOIN \n" +
                "    semester se ON c.semid = se.semid\n" +
                "JOIN \n" +
                "    assesments a ON c.subid = a.subid\n" +
                "JOIN \n" +
                "    exams e ON a.aid = e.aid\n" +
                "JOIN \n" +
                "    grades g ON e.eid = g.eid AND g.sid = s.sid\n" +
                "WHERE \n" +
                "    c.lid = ?\n" +
                "    AND c.cid = ?\n" +
                "GROUP BY \n" +
                "    s.sid, s.sname, se.year, se.season, sub.subname\n" +
                "ORDER BY \n" +
                "    total_score DESC;";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, lecturerId);
        stmt.setInt(2, courseId);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int sid = rs.getInt("sid");
            String sname = rs.getString("sname");
            int year = rs.getInt("year");
            String semester = rs.getString("semester");
            String subjectName = rs.getString("subject_name");
            double totalScore = rs.getDouble("total_score");
            String status = rs.getString("status");

            results.add(new StudentResult(sid, sname, year, semester, subjectName, totalScore, status));
        }

        rs.close();
        stmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return results;
}


    @Override
    public void insert(Grade model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Grade model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Grade model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Grade get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Grade> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}