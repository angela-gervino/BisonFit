package comp3350.srsys.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.srsys.objects.Student;
import comp3350.srsys.persistence.StudentPersistence;

public class StudentPersistenceHSQLDB implements StudentPersistence {

    private final String dbPath;

    public StudentPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Student fromResultSet(final ResultSet rs) throws SQLException {
        final String studentID = rs.getString("studentID");
        final String studentName = rs.getString("name");
        final String address = rs.getString("address");

        return new Student(studentID, studentName, address);
    }

    @Override
    public List<Student> getStudentSequential() {
        final List<Student> students = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM students");
            while (rs.next()) {
                final Student student = fromResultSet(rs);
                students.add(student);
            }
            rs.close();
            st.close();

            return students;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Student> getStudentRandom(Student currentStudent) {
        final List<Student> students = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM students WHERE studentID = ?");
            st.setString(1, currentStudent.getStudentID());

            final ResultSet rs = st.executeQuery();
            while(rs.next()) {
                final Student student = fromResultSet(rs);
                students.add(student);
            }

            rs.close();
            st.close();

            return students;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Student insertStudent(Student currentStudent) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO students VALUES(?, ?, ?)");
            st.setString(1, currentStudent.getStudentID());
            st.setString(2, currentStudent.getStudentName());
            st.setString(3, currentStudent.getStudentAddress());
            st.executeUpdate();
            return currentStudent;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Student updateStudent(Student currentStudent) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE students SET name = ?, address = ? WHERE studentID = ?");
            st.setString(1, currentStudent.getStudentName());
            st.setString(2, currentStudent.getStudentAddress());
            st.setString(3, currentStudent.getStudentID());
            st.executeUpdate();
            return currentStudent;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteStudent(Student currentStudent) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM studentscourses WHERE studentID = ?");
            sc.setString(1, currentStudent.getStudentID());
            sc.executeUpdate();
            final PreparedStatement st = c.prepareStatement("DELETE FROM students WHERE studentID = ?");
            st.setString(1, currentStudent.getStudentID());
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
