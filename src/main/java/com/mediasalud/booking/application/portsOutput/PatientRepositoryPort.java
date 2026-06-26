package com.mediasalud.booking.application.portsOutput;

import com.mediasalud.booking.domain.model.Patient;

import java.util.Optional;

public interface PatientRepositoryPort {

    Patient save(Patient patient);
    Optional<Patient> findById(String id);
    Optional<Patient> findByDocumentId(String documentId);
}
