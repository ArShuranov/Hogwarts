package ru.arshuranov.hogwartswithzahar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.arshuranov.hogwartswithzahar.controller.StudentController;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HogwartsWithZaharApplicationRestTemplateTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception{
        assertThat(studentController).isNotNull();
    }

    @Test
    void  testDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students", String.class))
                .isEqualTo("Welcome to Hogwarts!");
    }

    @Test
    void  testGetStudentById() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students/1", String.class))
                .isEqualTo("{\"id\":1,\"name\":\"Harry Potter\",\"age\":14,\"faculty\":{\"id\":5,\"name\":\"Griffindor\",\"color\":\"blue\"}}");
    }

    @Test
    void  testGetStudentByIdIsNotEmpty() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students/1", String.class))
                .isNotEmpty();
    }

    @Test
    void  testGetStudentByAge() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students/by-age?age=13", String.class))
                .isEqualTo("[{\"id\":4,\"name\":\"Ron\",\"age\":13,\"faculty\":{\"id\":6,\"name\":\"Slizzerin\",\"color\":\"green\"}}]");
    }

    @Test
    void  testGetStudentByAgeBetween() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students/by-age-between?min=0&max=100", String.class))
                .isNotEqualTo("[]");
    }


}
