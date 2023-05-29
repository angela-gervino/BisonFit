package comp3350.srsys.objects;

import java.util.Objects;

public class Student
{
	private final String studentID;
	private final String studentName;
	private final String studentAddress;

	public Student(final String newID)
	{
		this.studentID = newID;
		this.studentName = null;
		this.studentAddress = null;
	}

	public Student(final String newID, final String newStudentName, final String newStudentAddress)
	{
		this.studentID = newID;
		this.studentName = newStudentName;
		this.studentAddress = newStudentAddress;
	}

	public String getStudentID()
	{
		return (studentID);
	}

	public String getStudentName()
	{
		return (studentName);
	}

	public String getStudentAddress()
	{
		return (studentAddress);
	}

	public String toString()
	{
		return String.format("Student: %s %s %s", studentID, studentName, studentAddress);
	}

	public int hashCode()
	{
		return Objects.hash(studentID, studentName, studentAddress);
	}

	public boolean equals(final Student o)
	{
		return Objects.equals(this.studentID, o.studentID) &&
				Objects.equals(this.studentName, o.studentName) &&
				Objects.equals(this.studentAddress, o.studentAddress);
	}
}
