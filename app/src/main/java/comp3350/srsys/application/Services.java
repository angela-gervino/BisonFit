package comp3350.srsys.application;

import comp3350.srsys.persistence.CoursePersistence;
import comp3350.srsys.persistence.SCPersistence;
import comp3350.srsys.persistence.StudentPersistence;
import comp3350.srsys.persistence.hsqldb.CoursePersistenceHSQLDB;
import comp3350.srsys.persistence.hsqldb.SCPersistenceHSQLDB;
import comp3350.srsys.persistence.hsqldb.StudentPersistenceHSQLDB;

public class Services
{
	private static StudentPersistence studentPersistence = null;
	private static CoursePersistence coursePersistence = null;
	private static SCPersistence scPersistence = null;

	public static synchronized StudentPersistence getStudentPersistence()
    {
		if (studentPersistence == null)
		{
		    //studentPersistence = new StudentPersistenceStub();
            studentPersistence = new StudentPersistenceHSQLDB(Main.getDBPathName());
        }

        return studentPersistence;
	}

    public static synchronized CoursePersistence getCoursePersistence()
    {
        if (coursePersistence == null)
        {
            // coursePersistence = new CoursePersistenceStub();
            coursePersistence = new CoursePersistenceHSQLDB(Main.getDBPathName());
        }

        return coursePersistence;
    }

	public static synchronized SCPersistence getScPersistence() {
        if (scPersistence == null) {
            // scPersistence = new SCPersistenceStub();
            scPersistence = new SCPersistenceHSQLDB(Main.getDBPathName());
        }

        return scPersistence;
    }
}
