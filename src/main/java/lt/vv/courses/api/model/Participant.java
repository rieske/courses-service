package lt.vv.courses.api.model;

public class Participant {

	private final String firstName;
	private final String lastName;
	private final long dateOfBirth;

	public Participant(String firstName, String lastName, long dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public long getDateOfBirth() {
		return dateOfBirth;
	}

}
