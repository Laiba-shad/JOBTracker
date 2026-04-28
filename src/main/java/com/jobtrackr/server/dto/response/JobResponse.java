package com.jobtrackr.server.dto.response;

import com.jobtrackr.server.enums.JobStatus;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobResponse {
    private Long jobId;
    private String jobTitle;
    private String jobDescription;
    private String jobLink;
    private String designation;
    private int salary;
    private String companyName;
    private String companyDescription;
    private String location;
    private JobStatus status;
    private String userId;
}
