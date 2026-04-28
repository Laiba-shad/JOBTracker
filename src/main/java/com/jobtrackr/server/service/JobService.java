package com.jobtrackr.server.service;

import com.jobtrackr.server.dto.request.JobRequest;
import com.jobtrackr.server.dto.response.JobResponse;
import com.jobtrackr.server.model.JobApplication;
import com.jobtrackr.server.model.User;
import com.jobtrackr.server.repo.JobRepo;
import com.jobtrackr.server.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
  @Autowired
  private JobRepo jobRepo;
  @Autowired  private UserRepo userRepo;

public JobResponse saveJob(JobRequest jobRequest, Long userId) {
    User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    //---- convert job request to job application, converting response type(DTO) into entity (model)
    JobApplication job = new JobApplication();
    job.setJobDescription(jobRequest.getJobDescription());
    job.setJobLink(jobRequest.getJobLink());
    job.setJobTitle(jobRequest.getJobTitle());

    job.setCompanyDescription(jobRequest.getCompanyDescription());
    job.setCompanyName(jobRequest.getCompanyName());

    job.setDesignation(jobRequest.getDesignation());
    job.setSalary(jobRequest.getSalary());
    job.setStatus(jobRequest.getStatus());
    job.setLocation(jobRequest.getLocation());

    //-------convert entity to response type(DTO)
    job.setUser(user);JobApplication response = jobRepo.save(job);

    JobResponse jobResponse = new JobResponse();
    jobResponse.setJobId(response.getJobId());
    jobResponse.setJobTitle(response.getJobTitle());
    jobResponse.setJobDescription(response.getJobDescription());
    jobResponse.setJobLink(response.getJobLink());
    jobResponse.setDesignation(response.getDesignation());
    jobResponse.setSalary(response.getSalary());
    jobResponse.setCompanyName(response.getCompanyName());
    jobResponse.setCompanyDescription(response.getCompanyDescription());
    jobResponse.setLocation(response.getLocation());
    jobResponse.setStatus(response.getStatus());
    jobResponse.setUserId(String.valueOf(response.getUser().getUserId())); // adjust getId() to match your User entity

    return jobResponse;

}
    public JobResponse mapToResponse(JobApplication job) {
        JobResponse res = new JobResponse();
        res.setJobId(job.getJobId());
        res.setJobTitle(job.getJobTitle());
        res.setJobDescription(job.getJobDescription());
        res.setJobLink(job.getJobLink());
        res.setDesignation(job.getDesignation());
        res.setSalary(job.getSalary());
        res.setCompanyName(job.getCompanyName());
        res.setCompanyDescription(job.getCompanyDescription());
        res.setLocation(job.getLocation());
        res.setStatus(job.getStatus());
        res.setUserId(String.valueOf(job.getUser().getUserId()));
        return res;
    }
    public JobResponse getJobById(Long id){

        JobApplication job = jobRepo.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        return mapToResponse(job);
    }

    public List<JobResponse> getAllJobs(){
   return jobRepo.findAll().stream().map(this::mapToResponse).toList();
    }
    public void deleteJob(Long id){
    jobRepo.findById(id).ifPresent(jobRepo::delete);
    }
    public JobResponse updateJob(Long id, JobRequest request) {
        JobApplication job = jobRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));

        // Update fields with new data
        job.setJobTitle(request.getJobTitle());
        job.setJobDescription(request.getJobDescription());
        job.setJobLink(request.getJobLink());
        job.setDesignation(request.getDesignation());
        job.setSalary(request.getSalary());
        job.setCompanyName(request.getCompanyName());
        job.setCompanyDescription(request.getCompanyDescription());
        job.setLocation(request.getLocation());
        job.setStatus(request.getStatus());

        // user stays the same, no need to update it

        JobApplication updated = jobRepo.save(job);
        return mapToResponse(updated);
    }

}