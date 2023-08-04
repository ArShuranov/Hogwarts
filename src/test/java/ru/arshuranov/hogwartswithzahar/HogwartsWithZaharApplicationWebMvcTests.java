package ru.arshuranov.hogwartswithzahar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.arshuranov.hogwartswithzahar.repository.AvatarRepository;
import ru.arshuranov.hogwartswithzahar.repository.StudentRepository;

@WebMvcTest
public class HogwartsWithZaharApplicationWebMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarRepository avatarRepository;




}
