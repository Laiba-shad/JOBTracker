package com.jobtrackr.server.repo;

import com.jobtrackr.server.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepo extends JpaRepository<JobApplication, Long> {

}
