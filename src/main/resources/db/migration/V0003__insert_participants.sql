INSERT INTO COURSES.PARTICIPANT(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, COURSE_ID) 
	VALUES('Test', 'Testing', '1970-01-01', (SELECT ID FROM COURSES.COURSE WHERE NAME='LT course'));
INSERT INTO COURSES.PARTICIPANT(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, COURSE_ID) 
	VALUES('Swedish', 'Student', '1975-01-01', (SELECT ID FROM COURSES.COURSE WHERE NAME='SE course'));
INSERT INTO COURSES.PARTICIPANT(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, COURSE_ID) 
	VALUES('British', 'Student', '1976-05-01', (SELECT ID FROM COURSES.COURSE WHERE NAME='SE course'));
INSERT INTO COURSES.PARTICIPANT(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, COURSE_ID) 
	VALUES('British', 'Student', '1976-05-01', (SELECT ID FROM COURSES.COURSE WHERE NAME='DK course'));