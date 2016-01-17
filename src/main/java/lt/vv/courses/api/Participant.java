package lt.vv.courses.api;

public class Participant {

	public final String firstName;
	public final String lastName;
	public final long dateOfBirth;

	public Participant(String firstName, String lastName, long dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}
}
