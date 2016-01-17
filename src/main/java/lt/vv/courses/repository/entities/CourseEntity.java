package lt.vv.courses.repository.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Course")
public class CourseEntity implements Serializable {

	private static final long serialVersionUID = -1609054984834908128L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Timestamp startTime;

	@Column(nullable = false)
	private Timestamp endTime;

	@Column(nullable = false)
	private String location;

	protected CourseEntity() {
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Timestamp getStartTime() {
		return new Timestamp(startTime.getTime());
	}

	public Timestamp getEndTime() {
		return new Timestamp(endTime.getTime());
	}

	public String getLocation() {
		return location;
	}

}
