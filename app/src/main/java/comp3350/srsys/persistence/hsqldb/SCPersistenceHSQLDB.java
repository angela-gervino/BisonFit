package comp3350.srsys.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.SC;
import comp3350.srsys.objects.Student;
import comp3350.srsys.persistence.SCPersistence;

public class SCPersistenceHSQLDB implements SCPersistence {

    private final String dbPath;

    public SCPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private SC fromResultSet(final ResultSet rs) throws SQLException {
        final String rsStudentID = rs.getString("studentID");
        final String courseId = rs.getString("courseID");
        final String grade = rs.getString("grade");
        final String courseName = rs.getString("name");

        final Student student = new Student(rsStudentID);
        final Course course = new Course(courseId, courseName);
        return new SC(student, course, grade);
    }

    @Override
    public List<SC> getSC(String studentID) {
        final List<SC> studentCourses = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM courses,studentsCourses WHERE courses.courseID=studentsCourses.courseID AND studentID = ?");
            st.setString(1, studentID);

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final SC record = fromResultSet(rs);
                studentCourses.add(record);
            }

            rs.close();
            st.close();

            return studentCourses;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<SC> getCS(String courseID) {
        final List<SC> studentCourses = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM students,studentsCourses WHERE students.studentID=studentsCourses.studentID AND courseID = ?");
            st.setString(1, courseID);

            final ResultSet rs = st.executeQuery();

            while (rs.next()) {
                final SC record = fromResultSet(rs);
                studentCourses.add(record);
            }

            rs.close();
            st.close();

            return studentCourses;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }
}
