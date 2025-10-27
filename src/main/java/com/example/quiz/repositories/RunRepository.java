package com.example.quiz.repositories;

import com.example.quiz.entities.Run;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RunRepository extends JpaRepository<Run, Long> {
    List<Run> findRunByUserId(Long userId);


}
