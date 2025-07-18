create database QuanliSinhVien;
use QuanLiSinhVien;
create table Class(
	ClassId int not null auto_increment key,
    ClassName varchar(60) not null,
    StartDate datetime not null ,
    Status BIT
);
create table Student(
		studentID int not null auto_increment key,
        studentName varchar(30) not null,
        address varchar(50) ,
        phone varchar(20),
        status BIT,
        classID int not null,
        foreign key (classID) references class(classID)
);
create table Subject(

         SubId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,

         SubName VARCHAR(30) NOT NULL,

         Credit TINYINT NOT NULL DEFAULT 1 CHECK ( Credit >= 1 ),

         Status BIT DEFAULT 1

);
create table Mark(

        MarkId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,

        SubId INT NOT NULL,

        StudentId INT NOT NULL,

        Mark FLOAT DEFAULT 0 CHECK ( Mark BETWEEN 0 AND 100),

        ExamTimes TINYINT DEFAULT 1,

        UNIQUE (SubId, StudentId),

        FOREIGN KEY (SubId) REFERENCES Subject (SubId),

        FOREIGN KEY (StudentId) REFERENCES Student (StudentId)

 );
 
INSERT INTO Class
VALUES (1, 'A1', '2008-12-20', 1);
INSERT INTO Class
VALUES (2, 'A2', '2008-12-22', 1);
INSERT INTO Class
VALUES (3, 'B3', current_date, 0);

INSERT INTO Student (StudentName, Address, Phone, Status, ClassId)
VALUES ('Hung', 'Ha Noi', '0912113113', 1, 1);
INSERT INTO Student (StudentName, Address, Status, ClassId)
VALUES ('Hoa', 'Hai phong', 1, 1);
INSERT INTO Student (StudentName, Address, Phone, Status, ClassId)
VALUES ('Manh', 'HCM', '0123123123', 0, 2);

INSERT INTO Subject
VALUES (1, 'CF', 5, 1),
 (2, 'C', 6, 1),
 (3, 'HDJ', 5, 1),
 (4, 'RDBMS', 10, 1);
 
 INSERT INTO Mark (SubId, StudentId, Mark, ExamTimes)
VALUES (1, 1, 8, 1),
 (1, 2, 10, 2),
 (2, 1, 12, 1);
 
 select* 
 from Student
 where lower(StudentName) like 'h%';
 
 select*
 from Class
 where month(StartDate) = 12;
 
 select * 
from Subject
where Credit between 3 and 5;

update Student
set ClassID = 2
where StudentName = 'Hung';

select
    s.StudentName,
    sj.SubName,
    m.Mark
from Mark m
join Student s on m.StudentId = s.StudentID
join Subject sj on m.SubId = sj.SubId
order by m.Mark desc, s.StudentName asc;



