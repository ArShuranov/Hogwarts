package ru.arshuranov.hogwartswithzahar.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.arshuranov.hogwartswithzahar.model.Faculty;
import ru.arshuranov.hogwartswithzahar.model.Student;
import ru.arshuranov.hogwartswithzahar.service.AvatarService;
import ru.arshuranov.hogwartswithzahar.service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest_2 {

   // Здесь попробовал все, как показывал Захар, ничего не работает.
    /* @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;




    @Test
    void shouldCreateStudent() throws Exception {

        //given
        Student student = new Student();
        student.setId(null);
        student.setName("Hermiona");
        student.setAge(11);
        student.setFaculty(null);

        when(studentService.add(student)).thenReturn(student);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student))
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }*/

    ///////////////////////////////////////
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;
    @MockBean
    private AvatarService avatarService;
    @InjectMocks
    private StudentController studentController;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addStudentTest() throws Exception {
        Long id = 1L;
        String name = "Bob";
        int age = 12;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentService.add(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/students")
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getStudentByIdTest() throws Exception {
        Long id = 1L;
        String name = "Bob";
        int age = 12;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentService.get(any(Long.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/" + id)
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        Long id = 1L;
        String name = "Bob";
        int age = 12;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentService.add(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/students/" + id)
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentsByAgeTest() throws Exception {
        List<Student> students = new ArrayList<>();
        Long id = 1L;
        String name = "Bob";
        int age = 12;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        students.add(student);

        when(studentService.getStudentsByAge(age)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/by-age?age=12")
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentBetweenAge() throws Exception {
        List<Student> students = new ArrayList<>();
        Long id = 1L;
        String name = "Bob";
        int age = 12;
        int minAge = 10;
        int maxAge = 12;


        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        students.add(student);

        when(studentService.findByAgeBetween(minAge, maxAge)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/by-age-between?min=10&max=12")
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(students.size())));
    }

    @Test
    public void updateStudentTest() throws Exception {
        Long id = 1L;
        String name = "Bob";
        int age = 12;

        String facultyName = "Гриффиндор";
        Long facultyID = 1L;
        String color = "Красный";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", facultyID);
        facultyObject.put("name", facultyName);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(facultyID);
        faculty.setName(facultyName);
        faculty.setColor(color);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        studentObject.put("faculty", faculty);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(faculty);

        when(studentService.update(id, student)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/students/" + id)
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getFacultyOfStudentTest() throws Exception {
        Long id = 1L;
        String name = "Bob";
        int age = 12;

        String facultyName = "Гриффиндор";
        Long facultyID = 1L;
        String color = "Красный";

        Faculty faculty = new Faculty();
        faculty.setId(facultyID);
        faculty.setName(facultyName);
        faculty.setColor(color);


        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(faculty);

        when(studentService.getFacultyByStudent(any(Long.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/"+ id + "/faculty", id)
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyID))
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(color));
    }

}
