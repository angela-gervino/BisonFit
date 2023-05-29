package comp3350.srsys.application;

import comp3350.srsys.presentation.CLI;

public class Main
{
	private static String dbName="SC";

	public static void main(String[] args)
	{
		CLI.run();
		System.out.println("All done");
	}

	public static void setDBPathName(final String name) {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dbName = name;
	}

	public static String getDBPathName() {
		return dbName;
	}
}
