package com.example.quiz.web;

import com.example.quiz.dto.request.RunRequest;
import com.example.quiz.dto.response.RunResponse;
import com.example.quiz.service.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    @Autowired
    private final RunService runService;

    public RunController(RunService runService) {
        this.runService = runService;
    }

    @PostMapping
    public ResponseEntity<RunResponse> createRun(@RequestBody RunRequest runRequest) {
        RunResponse runResponse = runService.createRun(runRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(runResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(runResponse);

    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RunResponse>> getAllRuns() {
        List<RunResponse> runs = runService.getAllRuns();
        return ResponseEntity.ok(runs);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @runSecurity.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<RunResponse> getRunById(@PathVariable Long id) {
        RunResponse runResponse = runService.getRunById(id);
        return ResponseEntity.ok(runResponse);
    }

    @GetMapping("/getRunsByUser/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<List<RunResponse>> getRunsByUser(@PathVariable Long id) {
        List<RunResponse> runs = runService.getRunsByUser(id);
        return ResponseEntity.ok(runs);
    }

    @GetMapping("/getRunsByQuiz/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<List<RunResponse>> getRunsByQuiz(@PathVariable Long id) {
        List<RunResponse> runs = runService.getRunsByQuiz(id);
        return ResponseEntity.ok(runs);
    }

    @PutMapping("updateProgress/{id}")
    @PreAuthorize("@runSecurity.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<RunResponse> updateProgress(@PathVariable Long id) {
        RunResponse runResponse = runService.updateProgress(id);
        return ResponseEntity.ok(runResponse);
    }

    @PutMapping("calculateScore/{id}")
    @PreAuthorize("@runSecurity.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<RunResponse> calculateScore(@PathVariable Long id) {
        RunResponse runResponse = runService.calculateScore(id);
        return ResponseEntity.ok(runResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @runSecurity.isOwner(#id, authentication.principal.id)")
    public ResponseEntity<Void> deleteRun(@PathVariable Long id) {
        runService.deleteRun(id);
        return ResponseEntity.noContent().build();
    }
}

