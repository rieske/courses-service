package lt.vv.courses.api.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Course {

	private final long id;
	private final String courseName;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mmZ", timezone = "UTC")
	private final Date startTime;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mmZ", timezone = "UTC")
	private final Date endTime;

	private final String location;

	public Course(long id, String courseName, Date startTime, Date endTime, String location) {
		this.id = id;
		this.courseName = courseName;
		this.startTime = new Date(startTime.getTime());
		this.endTime = new Date(endTime.getTime());
		this.location = location;
	}

	public long getId() {
		return id;
	}

	public String getCourseName() {
		return courseName;
	}

	public Date getStartTime() {
		return new Date(startTime.getTime());
	}

	public Date getEndTime() {
		return new Date(endTime.getTime());
	}

	public String getLocation() {
		return location;
	}

}
