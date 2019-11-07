package lt.vv.courses.api;

import java.time.Instant;

public class Course {

    public final long id;
    public final String courseName;
    public final Instant startTime;
    public final Instant endTime;
    public final String location;

    public Course(long id, String courseName, Instant startTime, Instant endTime, String location) {
        this.id = id;
        this.courseName = courseName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

}
