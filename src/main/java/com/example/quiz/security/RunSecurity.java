package com.example.quiz.security;

import com.example.quiz.repositories.RunRepository;
import org.springframework.stereotype.Component;

@Component("runSecurity")
public class RunSecurity {

    private final RunRepository runRepository;

    public RunSecurity(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    public boolean isOwner(Long runId, Long userId) {
        return runRepository.findById(runId)
                .map(run -> run.getUser().getId().equals(userId))
                .orElse(false);
    }
}

