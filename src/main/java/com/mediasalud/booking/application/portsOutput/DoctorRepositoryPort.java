package com.mediasalud.booking.application.portsOutput;

import com.mediasalud.booking.domain.model.Doctor;

import java.util.Optional;

public interface DoctorRepositoryPort {

    Doctor save(Doctor doctor);
    Optional<Doctor> findById(String id);
}
