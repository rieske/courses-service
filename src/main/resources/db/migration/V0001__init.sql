CREATE TABLE COURSES.COURSE(
    ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    START_TIME BIGINT NOT NULL,
    END_TIME BIGINT NOT NULL,
	LOCATION VARCHAR(255) NOT NULL
);

CREATE TABLE COURSES.PARTICIPANT(
	ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	FIRST_NAME VARCHAR(50) NOT NULL,
	LAST_NAME VARCHAR(50) NOT NULL,
	DATE_OF_BIRTH DATE NOT NULL,
	COURSE_ID BIGINT NOT NULL,
	FOREIGN KEY (COURSE_ID) REFERENCES COURSES.COURSE(ID)
);