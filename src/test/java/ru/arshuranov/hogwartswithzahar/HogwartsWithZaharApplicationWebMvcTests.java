package ru.arshuranov.hogwartswithzahar;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.arshuranov.hogwartswithzahar.controller.StudentController;
import ru.arshuranov.hogwartswithzahar.model.Faculty;
import ru.arshuranov.hogwartswithzahar.model.Student;
import ru.arshuranov.hogwartswithzahar.repository.AvatarRepository;
import ru.arshuranov.hogwartswithzahar.repository.FacultyRepository;
import ru.arshuranov.hogwartswithzahar.repository.StudentRepository;
import ru.arshuranov.hogwartswithzahar.service.impl.AvatarServiceImpl;
import ru.arshuranov.hogwartswithzahar.service.impl.FacultyServiceImpl;
import ru.arshuranov.hogwartswithzahar.service.impl.StudentServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
public class HogwartsWithZaharApplicationWebMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private StudentServiceImpl studentService;

    @MockBean
    private FacultyServiceImpl facultyService;

    @SpyBean
    private AvatarServiceImpl avatarService;

    @InjectMocks
    private StudentController studentController;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldAddStudentTest() throws Exception {
        final long id = 1;
        final String name = "Malfoy";
        final int age = 14;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/students")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andDo(print());

    }

    //здесь я сделал как показывал Захар, в принципе разницы особой нет.
    @Test
    void shouldCreateStudent() throws Exception {

        //given
        Student student = new Student();
        student.setName("Hermiona");
        student.setAge(11);
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
    }

    @Test
    void shouldGetStudentByIdTest() throws Exception {
        final long id = 1;
        final String name = "Malfoy";
        final int age = 14;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/" + id)
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andDo(print());

    }

    @Test
    void shouldDeleteStudent() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

   /* @Test
    void shouldUpdateStudentTest() throws Exception {
        final long id = 1;
        final String name = "Malfoy";
        final int age = 14;

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentService.update(id, student)).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/students/" + id)
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }*/

    @Test
    void shouldBeUpdateStudentsTest() throws Exception {
        Student student = new Student(1L, "Harry", 14);
        Long id = student.getId();
        when(studentService.update(id, student)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.put("/students/" + student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    void shouldUpdateStudentTest() throws Exception {
        final long id = 1;
        Student student = new Student(id, "Malfoy", 14);

        when(studentService.update(id, student)).thenReturn(student);
        //when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        // when(studentRepository.save(any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.put("/students/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());

    }

    @Test
    void updateStudentTest() throws Exception {
        Long id = 1L;
        String name = "Bob";
        int age = 12;

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentService.update(id, student)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/students/", +id)
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andDo(print());
    }


    @Test
    void shouldReturnFaculty() throws Exception {
        Long id = 1L;
        Faculty faculty = new Faculty(1L, "Griffindor", "red");

        Student s = new Student(1L, "test", 15);
        s.setFaculty(faculty);
        when(studentRepository.findById(id)).thenReturn(Optional.of(s));
        mockMvc.perform(MockMvcRequestBuilders.get("/students/" + id + "/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());

    }


}