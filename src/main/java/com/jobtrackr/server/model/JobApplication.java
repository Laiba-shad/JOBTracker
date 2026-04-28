package com.jobtrackr.server.model;

import com.jobtrackr.server.enums.JobStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;
    @Column(nullable = false)
    private String jobTitle;
    private String jobDescription;
    private String jobLink;
    private String designation;
    private int salary;
    @Column(nullable = false)
    private String companyName;
    private String companyDescription;
    private String location;
    @Enumerated(EnumType.STRING)
    private JobStatus status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @CreationTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
