package com.TaskManagement.Client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TaskManagement.Enum.IssueStatus;



@FeignClient(name = "issue_service", url = "${issue.service.url}")
public interface IssueClient {

    @PutMapping("/{id}/status")
    void status(
            @PathVariable Long id,@RequestParam IssueStatus status,@RequestParam String performedBy);

    @PostMapping("/{id}/commit")
    void commit(
            @PathVariable Long id,
            @RequestParam String author,
            @RequestParam String body
    );
}
