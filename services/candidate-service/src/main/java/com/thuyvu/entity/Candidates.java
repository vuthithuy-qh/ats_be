package com.thuyvu.entity;

import jakarta.persistence.*;
import jakarta.servlet.ServletResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "candidates")
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Candidates {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_name")
    private String fullName;

    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    private String authProvider;

    private String oauthProviderId;

    private  CandidateStatus status;

    private String phone;

    private String source;

    private String utmSource;

    private String utmMedium;

    private String utmCampaign;

    private Boolean isDuplicate;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CandidateSkills> candidateSkills = new ArrayList<>();

}
