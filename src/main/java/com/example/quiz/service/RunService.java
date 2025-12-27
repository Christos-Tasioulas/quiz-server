package com.example.quiz.service;

import com.example.quiz.dto.request.RunRequest;
import com.example.quiz.dto.response.RunResponse;
import com.example.quiz.entities.Question;
import com.example.quiz.entities.QuestionAnswered;
import com.example.quiz.entities.Run;
import com.example.quiz.entities.User;
import com.example.quiz.exceptions.RunNotFoundException;
import com.example.quiz.exceptions.UserNotFoundException;
import com.example.quiz.repositories.QuestionRepository;
import com.example.quiz.repositories.RunRepository;
import com.example.quiz.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RunService {
    private final RunRepository runRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public RunResponse createRun(RunRequest runRequest) {
        // 1. Fetch user
        User user = userRepository.findById(runRequest.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + runRequest.userId()));

        // 2. Create run
        Run run = new Run(runRequest.score(), runRequest.totalQuestions());
        run.setUser(user);

        // 3. Fetch random questions from the database
        List<Question> allQuestions = questionRepository.findQuestionsByQuizId(runRequest.quizId());
        Collections.shuffle(allQuestions); // randomize
        List<Question> selectedQuestions = allQuestions.stream()
                .limit(runRequest.totalQuestions())
                .toList();

        // 4. Create QuestionAnswered entries
        for (Question question : selectedQuestions) {
            QuestionAnswered qa = new QuestionAnswered(run, question);
            run.getQuestions().add(qa); // add to run's list
        }

        // 5. Save run (cascade will save QuestionAnswered)
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

    public List<RunResponse> getRunsByQuiz(Long id) {
        List<Run> runs = runRepository.findRunByQuizId(id);
        return runs.stream().map(RunResponse::new).toList();
    }

    public RunResponse updateProgress(Long id) {
        Run run = runRepository.findById(id).orElseThrow(() -> new RunNotFoundException(id));
        List<QuestionAnswered> questionAnswered = run.getQuestions();
        int questionsAnswered = questionAnswered.stream().filter(QuestionAnswered::isQuestionAnswered).toList().size();
        run.setQuestionsAnswered(questionsAnswered);
        runRepository.save(run);
        return new RunResponse(run);
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
