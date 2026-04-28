package com.jobtrackr.server.dto.request;

import com.jobtrackr.server.enums.JobStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobRequest {

    @NotNull
    private String jobTitle;
    private String jobDescription;
    private String jobLink;
    @NotBlank
    private String designation;

    private int salary;
    @NotBlank
    private String companyName;
    private String companyDescription;
    private String location;
    @NotNull
    private JobStatus status;

}
