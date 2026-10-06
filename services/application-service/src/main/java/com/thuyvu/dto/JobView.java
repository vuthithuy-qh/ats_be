package com.thuyvu.dto;

import lombok.*;
import java.util.UUID;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobView {

    private UUID jobId;
    private String title;
    private String status;
    private LocalDate applicationDeadline;

}
