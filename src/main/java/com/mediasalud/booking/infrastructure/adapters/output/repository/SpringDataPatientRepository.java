package com.mediasalud.booking.infrastructure.adapters.output.repository;

import com.mediasalud.booking.infrastructure.adapters.output.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataPatientRepository extends JpaRepository<PatientEntity, String> {

    Optional<PatientEntity> findByDocumentId(String documentId);
}
