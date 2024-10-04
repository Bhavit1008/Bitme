package com.bithub.bitme.service;

import com.bithub.bitme.model.*;
import com.bithub.bitme.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VersionControlService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private CommitRepository commitRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private HeadRepository headRepository;

    @Autowired
    private LogRepository logRepository;

    public void initRepository() {
        BranchEntity masterBranch = new BranchEntity();
        masterBranch.setName("master");
        masterBranch.setActive(true);
        branchRepository.save(masterBranch);

        // Initialize HEAD pointer to point to master branch
        HeadEntity head = new HeadEntity();
        head.setCurrentBranch(masterBranch);
        headRepository.save(head);

        logOperation("INIT", "Initialized repository with master branch and HEAD pointing to master.");
    }

    public FileEntity addFile(String name, String content) {
        FileEntity file = new FileEntity();
        file.setName(name);
        file.setContent(content);
        fileRepository.save(file);
        logOperation("ADD FILE", "Added file: " + name);
        return file;
    }

    public void commitChanges(String message, List<Long> fileIds) {
        // Get the branch to which HEAD is currently pointing
        HeadEntity head = headRepository.findAll().get(0); // Assuming a single HEAD
        BranchEntity activeBranch = head.getCurrentBranch();

        CommitEntity commit = new CommitEntity();
        commit.setMessage(message);
        commit.setTimestamp(LocalDateTime.now());
        commit.setBranch(activeBranch);

        List<FileEntity> files = fileRepository.findAllById(fileIds);
        commit.setFiles(files);

        commitRepository.save(commit);
        logOperation("COMMIT", "Commit made on branch: " + activeBranch.getName());
    }

    public void createBranch(String name) {
        BranchEntity branch = new BranchEntity();
        branch.setName(name);
        branchRepository.save(branch);
        logOperation("CREATE BRANCH", "Branch created: " + name);
    }

    public void switchBranch(Long targetBranchId) {
        // Get the current branch that HEAD points to
        HeadEntity head = headRepository.findAll().get(0); // Assuming a single HEAD
        BranchEntity currentBranch = head.getCurrentBranch();

        BranchEntity targetBranch = branchRepository.findById(targetBranchId)
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        if (!targetBranch.getId().equals(currentBranch.getId())) {
            currentBranch.setActive(false);
            targetBranch.setActive(true);

            branchRepository.save(currentBranch);
            branchRepository.save(targetBranch);

            // Update HEAD pointer to point to the new branch
            head.setCurrentBranch(targetBranch);
            headRepository.save(head);

            logOperation("SWITCH BRANCH", "Switched from " + currentBranch.getName() + " to " + targetBranch.getName());
        }
    }

    public String compareFiles(Long fileId, Long commitId1, Long commitId2) {
        CommitEntity commit1 = commitRepository.findById(commitId1)
                .orElseThrow(() -> new RuntimeException("Commit 1 not found"));
        CommitEntity commit2 = commitRepository.findById(commitId2)
                .orElseThrow(() -> new RuntimeException("Commit 2 not found"));

        FileEntity file1 = commit1.getFiles().stream()
                .filter(file -> file.getId().equals(fileId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("File not found in Commit 1"));

        FileEntity file2 = commit2.getFiles().stream()
                .filter(file -> file.getId().equals(fileId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("File not found in Commit 2"));

        return getFileDifferences(file1.getContent(), file2.getContent());
    }

    private String getFileDifferences(String content1, String content2) {
        StringBuilder differences = new StringBuilder();
        String[] lines1 = content1.split("\n");
        String[] lines2 = content2.split("\n");

        int maxLines = Math.max(lines1.length, lines2.length);
        for (int i = 0; i < maxLines; i++) {
            String line1 = i < lines1.length ? lines1[i] : "";
            String line2 = i < lines2.length ? lines2[i] : "";

            if (!line1.equals(line2)) {
                differences.append("Line ").append(i + 1).append(":\n");
                differences.append("Commit 1: ").append(line1).append("\n");
                differences.append("Commit 2: ").append(line2).append("\n");
            }
        }
        return differences.length() == 0 ? "Files are identical" : differences.toString();
    }

    private void logOperation(String operation, String details) {
        LogEntity log = new LogEntity();
        log.setOperation(operation);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        logRepository.save(log);
    }
}
