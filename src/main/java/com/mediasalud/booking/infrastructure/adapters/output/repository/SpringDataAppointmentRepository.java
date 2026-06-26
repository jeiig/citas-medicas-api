package com.mediasalud.booking.infrastructure.adapters.output.repository;

import com.mediasalud.booking.infrastructure.adapters.output.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SpringDataAppointmentRepository extends JpaRepository<AppointmentEntity, String> {

    boolean existsByDoctorIdAndSlotDateTimeAndStatus(String doctorId, LocalDateTime slotDateTime, String status);

    boolean existsByPatientIdAndDoctorIdAndSlotDateTimeAndStatus(String patientId, String doctorId, LocalDateTime slotDateTime, String status);

    // Query dinámica adaptada para cumplir con los filtros opcionales del RF-06
    @Query("SELECT a FROM AppointmentEntity a WHERE " +
            "(:doctorId IS NULL OR a.doctor.id = :doctorId) AND " +
            "(:patientId IS NULL OR a.patient.id = :patientId) AND " +
            "(:status IS NULL OR a.status = :status) AND " +
            "(a.slotDateTime BETWEEN :start AND :end)")
    List<AppointmentEntity> findByFilters(
            @Param("doctorId") String doctorId,
            @Param("patientId") String patientId,
            @Param("status") String status,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);


}
