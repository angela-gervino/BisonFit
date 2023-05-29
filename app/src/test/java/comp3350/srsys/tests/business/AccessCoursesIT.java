package comp3350.srsys.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.srsys.application.Main;
import comp3350.srsys.business.AccessCourses;
import comp3350.srsys.objects.Course;
import comp3350.srsys.persistence.CoursePersistence;
import comp3350.srsys.persistence.hsqldb.CoursePersistenceHSQLDB;
import comp3350.srsys.tests.utils.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AccessCoursesIT {
    private AccessCourses accessCourses;
    private File tempDB;


    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final CoursePersistence persistence = new CoursePersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.accessCourses = new AccessCourses(persistence);
    }

    @Test
    public void testListCourses() {
        final Course course;

        course = accessCourses.getSequential();
        assertNotNull("first sequential course should not be null", course);
        assertTrue("COMP3010".equals(course.getCourseID()));

        System.out.println("Finished test AccessCourses");
    }

    @Test
    public void testGetCourses() {
        final List<Course> courses = accessCourses.getCourses();
        assertEquals(4, courses.size());
    }

    @Test
    public void testDeleteCourse() {
        final Course c = accessCourses.getSequential();
        List<Course> courses = accessCourses.getCourses();
        assertEquals(4, courses.size());
        accessCourses.deleteCourse(c);
        courses = accessCourses.getCourses();
        assertEquals(3, courses.size());
    }

    @Test
    public void testInsertCourse() {
        final Course c = new Course("3190", "AI");
        accessCourses.insertCourse(c);
        assertEquals(5, accessCourses.getCourses().size());
    }

    @Test
    public void testUpdateCourse() {
        final Course c = accessCourses.getSequential();
        final Course u = new Course(c.getCourseID(), "A new name");
        accessCourses.updateCourse(u);
        assertEquals(4, accessCourses.getCourses().size());
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }
}
