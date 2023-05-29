package comp3350.srsys.tests.business;


import org.junit.Before;
import org.junit.Test;


import comp3350.srsys.business.AccessSC;
import comp3350.srsys.objects.SC;
import comp3350.srsys.tests.persistence.SCPersistenceStub;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AccessSCTest
{
	private AccessSC accessSC;

	@Before
    public void setUp() {
	    this.accessSC = new AccessSC(new SCPersistenceStub());
    }

    @Test
	public void test1()
	{
		System.out.println("\nStarting test AccessSC");

		final SC sc = accessSC.getCS("COMP3350");
		assertNotNull(sc);
		assertTrue("100".equals(sc.getStudentID()));

		System.out.println("Finished test AccessSC");
	}
}