package ru.arshuranov.hogwartswithzahar.controller;

import org.springframework.web.bind.annotation.*;
import ru.arshuranov.hogwartswithzahar.model.Faculty;
import ru.arshuranov.hogwartswithzahar.model.Student;
import ru.arshuranov.hogwartswithzahar.service.FacultyService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/faculties")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

  /*  @GetMapping
    public Map<Long, Faculty> getAllfacultys() {
        return  facultyService.getAll();
    }*/

    @GetMapping("/{id}")
    public Faculty get(@PathVariable Long id) {
        return facultyService.get(id);
    }

    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return facultyService.add(faculty);
    }

    @PutMapping("/{id}")
    public Faculty update(@PathVariable Long id, @RequestBody Faculty faculty) {
        return facultyService.update(id, faculty);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        facultyService.remove(id);
    }

    @GetMapping("/by-color")
    public List<Faculty> getFacultiesByColor(@RequestParam String color) {
        return facultyService.getFacultiesByColor(color);
    }

    @GetMapping("/by-color-or-name")
    public List<Faculty> getFacultiesByColorOrName(@RequestParam String color, @RequestParam String name) {
        return facultyService.getFacultiesByColorOrName(color, name);
    }

    @GetMapping("/{id}/students")
    public List<Student> getStudents(@PathVariable Long id) {
        return facultyService.getStudents(id);
    }

}
