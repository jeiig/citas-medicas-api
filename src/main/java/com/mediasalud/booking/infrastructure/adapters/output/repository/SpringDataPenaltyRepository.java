package com.mediasalud.booking.infrastructure.adapters.output.repository;

import com.mediasalud.booking.infrastructure.adapters.output.PenaltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface SpringDataPenaltyRepository extends JpaRepository<PenaltyEntity, String> {

    long countByPatientIdAndPenaltyDateTimeAfter(String patientId, LocalDateTime dateTime);
}
