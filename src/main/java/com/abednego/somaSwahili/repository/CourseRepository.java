package com.abednego.somaSwahili.repository;

import com.abednego.somaSwahili.model.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTutorId(Long tutorId);
}
