package ru.arshuranov.hogwartswithzahar;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.arshuranov.hogwartswithzahar.controller.StudentController;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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


    @Test
    void addStudentTest() throws Exception {
        final long id = 1;
        final String name = "Malfoy";
        final int age =14;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/students")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }


}
