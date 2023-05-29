package comp3350.srsys.tests.business;


import org.junit.Before;
import org.junit.Test;

import comp3350.srsys.business.AccessStudents;

import comp3350.srsys.objects.Student;

import comp3350.srsys.tests.persistence.StudentPersistenceStub;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AccessStudentsTest
{
	private AccessStudents accessStudents;

	@Before
    public void setUp() {
	    this.accessStudents = new AccessStudents(new StudentPersistenceStub());
    }

    @Test
	public void test1()
	{
		final Student student = accessStudents.getSequential();
		assertNotNull(student);
		assertTrue("100".equals(student.getStudentID()));
	}
}