package lt.vv.courses.api;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Course {

	public final long id;
	public final String courseName;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mmZ", timezone = "UTC")
	public final Date startTime;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mmZ", timezone = "UTC")
	public final Date endTime;

	public final String location;

	public Course(long id, String courseName, Date startTime, Date endTime, String location) {
		this.id = id;
		this.courseName = courseName;
		this.startTime = new Date(startTime.getTime());
		this.endTime = new Date(endTime.getTime());
		this.location = location;
	}

}
