package com.mediasalud.booking.infrastructure.adapters.output;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity {

    @Id
    private String id;
    private String name;
    private String documentId;
    private String phone;
    private String email;
    private LocalDate birthDate;
}
