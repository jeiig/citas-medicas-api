package com.mediasalud.booking.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class Penalty {
    private String id;
    private String patientId;
    private LocalDateTime penaltyDateTime;

    public Penalty(String id, String patientId, LocalDateTime penaltyDateTime) {
        this.id = id;
        this.patientId = patientId;
        this.penaltyDateTime = penaltyDateTime;
    }
}
