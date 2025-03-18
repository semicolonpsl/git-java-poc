package com.semicolons.GitPulse.Service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class GitService {

    @Value("${git.local.repo}")
    private String localRepoPath;

    @Value("${git.remote.url}")
    private String remoteRepoUrl;

    @Value("${git.username}")
    private String username;

    @Value("${git.token}")
    private String token;

    public String pushToGit(String commitMessage) {
        try {
            // Open the local repository
            Git git = Git.open(new File(localRepoPath));

            // Add all files to staging
            git.add().addFilepattern(".").call();

            // Commit changes
            git.commit().setMessage(commitMessage).call();

            // Push to remote repository
            Iterable<PushResult> pushResults = git.push()
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, token))
                    .setRemote(remoteRepoUrl)
                    .call();
            for (PushResult result : pushResults) {
                System.out.println("Push messages: " + result.getMessages());
            }

            return "Code pushed successfully  !";
        } catch (Exception e) {
            return "Error pushing to Git: " + e.getMessage();
        }
    }
}
