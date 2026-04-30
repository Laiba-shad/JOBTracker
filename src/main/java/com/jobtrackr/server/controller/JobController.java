package com.jobtrackr.server.controller;

import com.jobtrackr.server.dto.request.JobRequest;
import com.jobtrackr.server.dto.response.JobResponse;
import com.jobtrackr.server.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/job")
public class JobController {
    private JobResponse jobResponse;
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    //create, update, delete, get/read
    @PostMapping("/{userId}")
    @PreAuthorize("@authorizationService.isVerified(authentication)")
    public ResponseEntity<JobResponse> createJob(@RequestBody JobRequest jobRequest,
                                                 @PathVariable Long userId) {
        JobResponse response = jobService.saveJob(jobRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
@GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJob(@PathVariable Long id){
    JobResponse response = jobService.getJobById(id);
        return ResponseEntity.ok(response);
}
@GetMapping()
    public List<JobResponse> allJobs(){
      return jobService.getAllJobs();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id){
         jobService.deleteJob(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(@PathVariable Long id, @RequestBody JobRequest request){
     JobResponse res = jobService.updateJob(id, request);
     return ResponseEntity.ok(res);
    }

}