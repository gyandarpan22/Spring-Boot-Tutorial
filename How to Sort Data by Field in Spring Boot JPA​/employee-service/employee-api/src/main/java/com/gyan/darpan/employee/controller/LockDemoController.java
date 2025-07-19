package com.gyan.darpan.employee.controller;


import com.gyan.darpan.dao.entity.Student;
import com.gyan.darpan.dao.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class LockDemoController {

    private final StudentRepository studentRepository;

    public LockDemoController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("lock/demo/{studentId}")
    @Transactional
    public Student getStudentDetail(@PathVariable("studentId") long studentId) {

        log.info("LockDemoController.getStudentDetail::calling studentRepository method");

        Student student = studentRepository.findByIdWithOptimisticForceIncrementLock(studentId);

        log.info("LockDemoController.getStudentDetail::completed studentRepository method");

//        try {
//            Thread.sleep(120000L);
//        } catch (Exception e) {
//
//        }

        return student;
    }

    @PostMapping("lock/demo")
    @Transactional
    public Student updateStudent(@RequestBody Student studentRequest) {

        log.info("LockDemoController.updateStudent::calling studentRepository method");

        Student student = studentRepository.findByIdWithOptimisticLock(studentRequest.getStudentId());

        log.info("LockDemoController.updateStudent::completed studentRepository method");

        try {
            Thread.sleep(30000);
        } catch (Exception e) {

        }

        student.setStudentName(studentRequest.getStudentName());
        return studentRepository.updateStudent(student);
    }


}
