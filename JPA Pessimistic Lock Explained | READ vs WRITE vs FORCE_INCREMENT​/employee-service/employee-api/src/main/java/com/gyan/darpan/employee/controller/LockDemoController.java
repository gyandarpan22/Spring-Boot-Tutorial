package com.gyan.darpan.employee.controller;


import com.gyan.darpan.dao.entity.Student;
import com.gyan.darpan.dao.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


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

        Student student = studentRepository.findByIdForForceIncrement(studentId);

        log.info("LockDemoController.getStudentDetail::completed studentRepository method");

//        try {
//            Thread.sleep(120000L);
//        } catch (Exception e) {
//
//        }

        return student;
    }
}
