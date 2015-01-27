package lt.vv.courses.api.model;

public class Course {

	private final long id;
	private final String courseName;
	private final long startTime;
	private final long endTime;
	private final String location;

	public Course(long id, String courseName, long startTime, long endTime, String location) {
		this.id = id;
		this.courseName = courseName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location;
	}

	public long getId() {
		return id;
	}

	public String getCourseName() {
		return courseName;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public String getLocation() {
		return location;
	}

}
