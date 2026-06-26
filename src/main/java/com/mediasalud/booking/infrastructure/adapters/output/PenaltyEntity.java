package com.mediasalud.booking.infrastructure.adapters.output;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "penalties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PenaltyEntity {

    @Id
    private String id;
    private String patientId;
    private LocalDateTime penaltyDateTime;
}
