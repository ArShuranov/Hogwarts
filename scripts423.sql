select student.name, student.age, faculty.name, faculty.color
from student
         inner join faculty on student.faculty_id = faculty.id;


select student.name
from avatar
         inner join student on avatar.student_id = student.id