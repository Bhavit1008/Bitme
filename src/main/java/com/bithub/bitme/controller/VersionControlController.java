package com.bithub.bitme.controller;

import com.bithub.bitme.model.FileEntity;
import com.bithub.bitme.service.VersionControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/version-control")
public class VersionControlController {

    @Autowired
    private VersionControlService versionControlService;

    @PostMapping("/init")
    public ResponseEntity<String> initRepository() {
        versionControlService.initRepository();
        return ResponseEntity.ok("Repository initialized.");
    }

    @PostMapping("/file")
    public ResponseEntity<FileEntity> addFile(@RequestParam String name, @RequestParam String content) {
        return ResponseEntity.ok(versionControlService.addFile(name, content));
    }

    @PostMapping("/commit")
    public ResponseEntity<String> commitChanges(@RequestParam String message, @RequestParam List<Long> fileIds) {
        versionControlService.commitChanges(message, fileIds);
        return ResponseEntity.ok("Changes committed to the current branch.");
    }

    @PostMapping("/branch")
    public ResponseEntity<String> createBranch(@RequestParam String name) {
        versionControlService.createBranch(name);
        return ResponseEntity.ok("Branch created.");
    }

    @PostMapping("/switch")
    public ResponseEntity<String> switchBranch(@RequestParam Long targetBranchId) {
        versionControlService.switchBranch(targetBranchId);
        return ResponseEntity.ok("Switched to branch " + targetBranchId);
    }

    @GetMapping("/compare-files")
    public ResponseEntity<String> compareFiles(@RequestParam Long fileId,
                                               @RequestParam Long commitId1,
                                               @RequestParam Long commitId2) {
        String differences = versionControlService.compareFiles(fileId, commitId1, commitId2);
        return ResponseEntity.ok(differences);
    }
}
