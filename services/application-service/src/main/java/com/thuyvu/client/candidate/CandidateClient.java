package com.thuyvu.client.candidate;

import com.thuyvu.dto.CandidateView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "candidate-service", url="${candidate-service.url}")
public interface CandidateClient {

    @GetMapping("/api/v1/candidates/{id}")
    CandidateView findById(@PathVariable("id")UUID id);
}
