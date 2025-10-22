package com.example.task2;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mentors")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    @GetMapping
    public List<Mentor> getAllMentors() {
        return mentorService.getAllMentors();
    }

    @GetMapping("/{id}")
    public Mentor getMentorById(@PathVariable Long id) {
        return mentorService.getMentorById(id);
    }

    @GetMapping("/{id}/students")
    public List<Student> getStudentsByMentorId(@PathVariable Long id) {
        return mentorService.getStudentsByMentorId(id);
    }

    @PostMapping
    public Mentor createMentor(@RequestBody Mentor mentor) {
        return mentorService.saveMentor(mentor);
    }

    @PostMapping("/{mentorId}/students")
    public Mentor assignStudentToMentor(@PathVariable Long mentorId, @RequestBody List<Long> studentIds) {
        Mentor mentor = mentorService.getMentorById(mentorId);
        if (mentor != null) {
            for (Long studentId : studentIds) {
                mentorService.assignStudentToMentor(mentorId, studentId);
            }
        }
        return mentor;
    }
    @DeleteMapping("/{mentorId}/students/{studentId}")
    public Mentor deleteStudentFromMentor(@PathVariable Long mentorId, @PathVariable Long studentId) {
        Mentor mentor = mentorService.getMentorById(mentorId);
        if (mentor != null) {
            mentorService.deleteStudentFromMentor(mentorId, studentId);
        }
        return mentor;
    }
    @PutMapping("/{mentorId}/students/{studentId}")
    public Student updateStudent(@PathVariable Long mentorId, @PathVariable Long studentId, @RequestBody Student updatedStudent) {
        // Call the service to update the student's details
        return mentorService.updateStudentDetails(mentorId, studentId, updatedStudent);
    }


}
