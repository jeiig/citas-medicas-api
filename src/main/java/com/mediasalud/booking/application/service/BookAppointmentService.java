package com.mediasalud.booking.application.service;


import com.mediasalud.booking.application.portsInput.BookAppointmentUseCase;
import com.mediasalud.booking.application.portsOutput.AppointmentRepositoryPort;
import com.mediasalud.booking.application.portsOutput.DoctorRepositoryPort;
import com.mediasalud.booking.application.portsOutput.PatientRepositoryPort;
import com.mediasalud.booking.application.portsOutput.PenaltyRepositoryPort;
import com.mediasalud.booking.domain.exception.BusinessException;
import com.mediasalud.booking.domain.model.Appointment;
import com.mediasalud.booking.domain.model.AppointmentStatus;
import com.mediasalud.booking.domain.model.Doctor;
import com.mediasalud.booking.domain.model.Patient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookAppointmentService implements BookAppointmentUseCase {

    private final AppointmentRepositoryPort appointmentRepository;
    private final DoctorRepositoryPort doctorRepository;
    private final PatientRepositoryPort patientRepository;
    private final PenaltyRepositoryPort penaltyRepository;

    public BookAppointmentService(AppointmentRepositoryPort appointmentRepository,
                                  DoctorRepositoryPort doctorRepository,
                                  PatientRepositoryPort patientRepository,
                                  PenaltyRepositoryPort penaltyRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.penaltyRepository = penaltyRepository;
    }

    @Override
    public Appointment execute(String patientId, String doctorId, LocalDateTime dateTime) {
        // 1. Validar existencia del Médico
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new BusinessException("El médico especificado no existe."));

        // 2. Validar existencia del Paciente
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new BusinessException("El paciente especificado no existe."));

        // 3. RN-05: Validar penalizaciones (Máximo 3 en los últimos 30 días)
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        long activePenalties = penaltyRepository.countByPatientIdAndPenaltyDateTimeAfter(patientId, thirtyDaysAgo);
        if (activePenalties >= 3) {
            throw new BusinessException("El paciente se encuentra bloqueado debido a que acumula 3 o más penalizaciones en los últimos 30 días.");
        }

        // 4. Crear la instancia de la cita (esto valida automáticamente RN-01: franjas de 30 min y horarios permitidos)
        Appointment appointment = new Appointment(UUID.randomUUID().toString(), patient, doctor, dateTime);

        // 5. RN-02: Verificar disponibilidad del médico en esa franja
        boolean doctorOccupied = appointmentRepository.existsByDoctorIdAndSlotDateTimeAndStatus(
                doctorId, dateTime, AppointmentStatus.PROGRAMADA);
        if (doctorOccupied) {
            throw new BusinessException("El médico ya tiene una cita programada en la franja horaria solicitada.");
        }

        // 6. RN-04: Verificar que el paciente no duplique cita con el mismo médico en esa franja
        boolean patientHasDuplicate = appointmentRepository.existsByPatientIdAndDoctorIdAndSlotDateTimeAndStatus(
                patientId, doctorId, dateTime, AppointmentStatus.PROGRAMADA);
        if (patientHasDuplicate) {
            throw new BusinessException("El paciente ya cuenta con una cita programada con este médico en la misma franja horaria.");
        }

        // Guardar y retornar la cita agendada con éxito
        return appointmentRepository.save(appointment);
    }
}
