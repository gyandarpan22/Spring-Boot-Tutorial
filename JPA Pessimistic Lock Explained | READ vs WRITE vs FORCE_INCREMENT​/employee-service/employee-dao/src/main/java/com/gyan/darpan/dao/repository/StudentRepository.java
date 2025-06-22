package com.gyan.darpan.dao.repository;

import com.gyan.darpan.dao.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {

    private final EntityManager entityManager;

    @Autowired
    public StudentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Student findById(Long studentId) {
        return entityManager.find(Student.class, studentId);
    }

    public Student findByIdForRead(Long studentId) {
        return entityManager.find(Student.class, studentId, LockModeType.PESSIMISTIC_READ);
    }

    public Student findByIdForWrite(Long studentId) {
        return entityManager.find(Student.class, studentId, LockModeType.PESSIMISTIC_WRITE);
    }

    public Student findByIdForForceIncrement(Long studentId) {
        return entityManager.find(Student.class, studentId, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
    }

}
