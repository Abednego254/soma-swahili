package com.abednego.somaSwahili.repository;

import com.abednego.somaSwahili.model.other.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTutorId(Long tutorId);
    List<Review> findByStudentId(Long studentId);
}
