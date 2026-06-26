package com.mediasalud.booking.infrastructure.adapters.output.repository;

import com.mediasalud.booking.infrastructure.adapters.output.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataDoctorRepository extends JpaRepository<DoctorEntity, String> {
}
