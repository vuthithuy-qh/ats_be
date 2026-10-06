package com.thuyvu.dto;

import com.thuyvu.entity.CandidateStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor@AllArgsConstructor
@ToString
public class CandidateRequest {
    @NotBlank
    private String fullName;
    @NotBlank
    @jakarta.validation.constraints.Email(message = "Email is not valid format")
    private String email;
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number is not valid")
    private String phone;
    private String source;
    private String utmSource;
    private String utmMedium;
    private String utmCampaign;
    private List<UUID> skillIds;

}
