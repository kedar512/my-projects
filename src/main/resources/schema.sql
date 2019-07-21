CREATE TABLE users  
( user_id number(10) NOT NULL,  
  user_name varchar2(50) NOT NULL,  
  CONSTRAINT users_pk PRIMARY KEY (user_id)  
);

CREATE SEQUENCE users_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 
 select * from users;
 
CREATE TABLE department  
( id number(10) NOT NULL,  
  department_name varchar2(50) NOT NULL,
  creation_date DATE DEFAULT (sysdate),
  CONSTRAINT departments_pk PRIMARY KEY (id)  
);

SELECT
    "A1"."ID"                "ID",
    "A1"."DEPARTMENT_NAME"   "DEPARTMENT_NAME",
    "A1"."CREATION_DATE"     "CREATION_DATE"
FROM
    "SYSTEM"."DEPARTMENT" "A1";

CREATE SEQUENCE departments_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 
 CREATE TABLE faculty  
( id number(10) NOT NULL,  
  first_name varchar2(50) NOT NULL,
  last_name varchar2(50) NOT NULL,
  creation_date DATE DEFAULT (sysdate),
  department_id number(10) NOT NULL,
  CONSTRAINT faculty_pk PRIMARY KEY (id),
  CONSTRAINT faculty_fk FOREIGN KEY (department_id)
        REFERENCES department (id)
);

CREATE SEQUENCE faculty_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 
 CREATE TABLE subject  
( id number(10) NOT NULL,  
  subject_name varchar2(50) NOT NULL,
  creation_date DATE DEFAULT (sysdate),
  CONSTRAINT subjects_pk PRIMARY KEY (id)  
);

select * from subject;

CREATE SEQUENCE subjects_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 
CREATE TABLE student  
( id number(10) NOT NULL,  
  first_name varchar2(50) NOT NULL,
  last_name varchar2(50) NOT NULL,
  creation_date DATE DEFAULT (sysdate),
  department_id number(10) NOT NULL,
  CONSTRAINT students_pk PRIMARY KEY (id),
  CONSTRAINT students_fk FOREIGN KEY (department_id)
        REFERENCES department (id)
);

SELECT
    "A1"."ID"              "ID",
    "A1"."FIRST_NAME"      "FIRST_NAME",
    "A1"."LAST_NAME"       "LAST_NAME",
    "A1"."CREATION_DATE"   "CREATION_DATE",
    "A1"."DEPARTMENT_ID"   "DEPARTMENT_ID"
FROM
    "SYSTEM"."STUDENT" "A1";
    
delete from student where id in (1002, 1003);

CREATE SEQUENCE students_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 
CREATE TABLE student_subject  
( student_id number(10) NOT NULL,
  subject_id number(10) NOT NULL,
  CONSTRAINT students_join_table_fk FOREIGN KEY (student_id)
        REFERENCES student (id),
  CONSTRAINT subjects_join_table_fk FOREIGN KEY (subject_id)
        REFERENCES subject (id)
);

select * from student_subject;

delete from student_subject where student_id in (1002, 1003);

 CREATE TABLE phone_number  
( id number(10) NOT NULL,  
  phone_number number(10) NOT NULL,
  creation_date DATE DEFAULT (sysdate),
  student_id number(10) NOT NULL,
  CONSTRAINT phone_numbers_pk PRIMARY KEY (id),
  CONSTRAINT phone_numbers_fk FOREIGN KEY (student_id)
        REFERENCES student (id)
);

ALTER TABLE 
   phone_number 
drop constraint
   phone_numbers_fk;
   
alter table
   phone_number
add constraint
   phone_numbers_fk FOREIGN KEY (student_id)
references
   student (id)
initially deferred deferrable;

select * from phone_number;

CREATE SEQUENCE phone_numbers_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 
CREATE TABLE author  
( id number(10) NOT NULL,  
  first_name varchar2(50) NOT NULL,
  last_name varchar2(50) NOT NULL,
  creation_date DATE DEFAULT (sysdate),
  CONSTRAINT authors_pk PRIMARY KEY (id)  
);

select * from author;

CREATE SEQUENCE authors_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 
 CREATE TABLE book  
( id number(10) NOT NULL,  
  title varchar2(50) NOT NULL,
  pages number(10) NOT NULL,
  publication_date DATE DEFAULT (sysdate),
  CONSTRAINT books_pk PRIMARY KEY (id)  
);

select * from book;

CREATE SEQUENCE books_seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 
 CREATE TABLE book_author  
( author_id number(10) NOT NULL,
  book_id number(10) NOT NULL,
  CONSTRAINT authors_join_table_fk FOREIGN KEY (author_id)
        REFERENCES author (id),
  CONSTRAINT books_join_table_fk FOREIGN KEY (book_id)
        REFERENCES book (id)
);

select * from book_author;