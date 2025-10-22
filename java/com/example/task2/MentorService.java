package com.example.task2;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MentorService {

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    public Mentor getMentorById(Long id) {
        return mentorRepository.findById(id).orElse(null);
    }

    public Mentor saveMentor(Mentor mentor) {
        return mentorRepository.save(mentor);
    }

    public List<Student> getStudentsByMentorId(Long mentorId) {
        return studentRepository.findByMentorId(mentorId);
    }

    public Mentor assignStudentToMentor(Long mentorId, Long studentId) {
        Mentor mentor = mentorRepository.findById(mentorId).orElse(null);
        Student student = studentRepository.findById(studentId).orElse(null);

        if (mentor != null && student != null) {
            student.setMentor(mentor);
            studentRepository.save(student);
        }

        return mentor;
    }
    public void deleteStudentFromMentor(Long mentorId, Long studentId) {
        Mentor mentor = getMentorById(mentorId);
        if (mentor != null) {
            List<Student> students = mentor.getStudents();
            // Remove the student by ID
            students.removeIf(student -> student.getId().equals(studentId));
            mentorRepository.save(mentor); // Save changes to the mentor
        }
    }
 // Inside MentorService class

 // Inside MentorService class

    public Student updateStudentDetails(Long mentorId, Long studentId, Student updatedStudent) {
        // Find the mentor
        Mentor mentor = getMentorById(mentorId);
        if (mentor != null) {
            // Find the student within the mentor's list of students
            List<Student> students = mentor.getStudents();
            for (Student student : students) {
                if (student.getId().equals(studentId)) {
                    // Update the student's name to the new value (Nag)
                    student.setName(updatedStudent.getName());
                    // Save the updated mentor with the updated student
                    mentorRepository.save(mentor);
                    return student; // Return the updated student
                }
            }
        }
        return null; // Return null or throw an exception if student not found
    }



}
