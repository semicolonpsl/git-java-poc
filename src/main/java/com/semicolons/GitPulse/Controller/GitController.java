package com.semicolons.GitPulse.Controller;

import com.semicolons.GitPulse.Service.GitService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/git")
public class GitController {

    private final GitService gitService;

    public GitController(GitService gitService) {
        this.gitService = gitService;
    }

    @PostMapping("/push")
    public String pushCode(@RequestParam String commitMessage) {
        return gitService.pushToGit(commitMessage);
    }
}

