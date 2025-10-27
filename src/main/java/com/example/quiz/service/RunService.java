package com.example.quiz.service;

import com.example.quiz.dto.request.RunRequest;
import com.example.quiz.dto.response.RunResponse;
import com.example.quiz.entities.Run;
import com.example.quiz.entities.User;
import com.example.quiz.exceptions.RunNotFoundException;
import com.example.quiz.exceptions.UserNotFoundException;
import com.example.quiz.repositories.RunRepository;
import com.example.quiz.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RunService {
    private final RunRepository runRepository;
    private final UserRepository userRepository;

    public RunResponse createRun(RunRequest runRequest) {
        User user = userRepository.findById(runRequest.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + runRequest.userId()));
        Run run = new Run(runRequest.score(), runRequest.totalQuestions());
        run.setUser(user);
        runRepository.save(run);
        return new RunResponse(run);
    }

    public List<RunResponse> getAllRuns() {
        return runRepository.findAll().stream().map(RunResponse::new).toList();
    }

    public RunResponse getRunById(Long id) {
        Run run = runRepository.findById(id).orElseThrow(() -> new RunNotFoundException(id));
        return new RunResponse(run);
    }

    public List<RunResponse> getRunsByUser(Long id) {
        List<Run> runs = runRepository.findRunByUserId(id);
        return runs.stream().map(RunResponse::new).toList();
    }

    public RunResponse calculateScore(Long id) {
        Run run = runRepository.findById(id).orElseThrow(() -> new RunNotFoundException(id));

        return new RunResponse(run);
    }

    public void deleteRun(Long id) {
        if (!runRepository.existsById(id)) {
            throw new RunNotFoundException(id);
        }
        runRepository.deleteById(id);
    }
}
