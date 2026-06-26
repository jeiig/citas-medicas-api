package com.mediasalud.booking.application.service;

import com.mediasalud.booking.application.portsOutput.AppointmentRepositoryPort;
import com.mediasalud.booking.application.portsOutput.DoctorRepositoryPort;
import com.mediasalud.booking.application.portsOutput.PatientRepositoryPort;
import com.mediasalud.booking.application.portsOutput.PenaltyRepositoryPort;
import com.mediasalud.booking.domain.exception.BusinessException;
import com.mediasalud.booking.domain.model.Appointment;
import com.mediasalud.booking.domain.model.AppointmentStatus;
import com.mediasalud.booking.domain.model.Doctor;
import com.mediasalud.booking.domain.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
 class BookAppointmentServiceTest {

    @Mock
    private AppointmentRepositoryPort appointmentRepository;
    @Mock
    private DoctorRepositoryPort doctorRepository;
    @Mock
    private PatientRepositoryPort patientRepository;
    @Mock
    private PenaltyRepositoryPort penaltyRepository;

    @InjectMocks
    private BookAppointmentService bookAppointmentService;

    private Doctor adminDoctor;
    private Patient adminPatient;
    private LocalDateTime validDateTime;

    @BeforeEach
    void setUp() {
        adminDoctor = new Doctor("doc-01", "Dr. Camilo Restrepo", "Cardiología", "3101234567", "camilo@medisalud.com");
        adminPatient = new Patient("pat-01", "Juan Mendoza", "1018456789", "3204087439", "juan@email.com", LocalDate.of(1990, 5, 14));

        // Un lunes laborable a las 09:00 AM (Cumple RN-01)
        validDateTime = LocalDateTime.of(2026, 6, 29, 9, 0);
    }

    @Test
    @DisplayName("Debería agendar una cita exitosamente cuando pasa todas las reglas de negocio")
    void bookAppointmentSuccess() {
        // Arrange
        when(doctorRepository.findById("doc-01")).thenReturn(Optional.of(adminDoctor));
        when(patientRepository.findById("pat-01")).thenReturn(Optional.of(adminPatient));
        when(penaltyRepository.countByPatientIdAndPenaltyDateTimeAfter(eq("pat-01"), any())).thenReturn(0L);
        when(appointmentRepository.existsByDoctorIdAndSlotDateTimeAndStatus(eq("doc-01"), eq(validDateTime), eq(AppointmentStatus.PROGRAMADA))).thenReturn(false);
        when(appointmentRepository.existsByPatientIdAndDoctorIdAndSlotDateTimeAndStatus(eq("pat-01"), eq("doc-01"), eq(validDateTime), eq(AppointmentStatus.PROGRAMADA))).thenReturn(false);
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Appointment result = bookAppointmentService.execute("pat-01", "doc-01", validDateTime);

        // Assert
        assertNotNull(result);
        assertEquals("pat-01", result.getPatient().getId());
        assertEquals("doc-01", result.getDoctor().getId());
        assertEquals(AppointmentStatus.PROGRAMADA, result.getStatus());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción si el paciente acumula 3 o más penalizaciones (RN-05)")
    void bookAppointmentThrowsWhenPatientIsBlocked() {
        // Arrange
        when(doctorRepository.findById("doc-01")).thenReturn(Optional.of(adminDoctor));
        when(patientRepository.findById("pat-01")).thenReturn(Optional.of(adminPatient));
        // Simulamos 3 penalizaciones activas
        when(penaltyRepository.countByPatientIdAndPenaltyDateTimeAfter(eq("pat-01"), any())).thenReturn(3L);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () ->
                bookAppointmentService.execute("pat-01", "doc-01", validDateTime)
        );

        assertTrue(exception.getMessage().contains("bloqueado debido a que acumula 3 o más penalizaciones"));
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción si el médico ya tiene una cita en esa franja (RN-02)")
    void bookAppointmentThrowsWhenDoctorIsOccupied() {
        // Arrange
        when(doctorRepository.findById("doc-01")).thenReturn(Optional.of(adminDoctor));
        when(patientRepository.findById("pat-01")).thenReturn(Optional.of(adminPatient));
        when(penaltyRepository.countByPatientIdAndPenaltyDateTimeAfter(eq("pat-01"), any())).thenReturn(0L);
        // Simulamos que el médico ya está ocupado
        when(appointmentRepository.existsByDoctorIdAndSlotDateTimeAndStatus(eq("doc-01"), eq(validDateTime), eq(AppointmentStatus.PROGRAMADA))).thenReturn(true);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () ->
                bookAppointmentService.execute("pat-01", "doc-01", validDateTime)
        );

        assertTrue(exception.getMessage().contains("El médico ya tiene una cita programada"));
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción si la fecha no cumple las reglas del Value Object (RN-01)")
    void bookAppointmentThrowsWhenInvalidSlotTime() {
        // Arrange
        when(doctorRepository.findById("doc-01")).thenReturn(Optional.of(adminDoctor));
        when(patientRepository.findById("pat-01")).thenReturn(Optional.of(adminPatient));
        when(penaltyRepository.countByPatientIdAndPenaltyDateTimeAfter(eq("pat-01"), any())).thenReturn(0L);

        // Forzamos un minuto inválido (las 09:15 AM) para que falle el constructor del dominio
        LocalDateTime invalidDateTime = LocalDateTime.of(2026, 6, 29, 9, 15);

        // Act & Assert
        assertThrows(BusinessException.class, () ->
                bookAppointmentService.execute("pat-01", "doc-01", invalidDateTime)
        );
        verify(appointmentRepository, never()).save(any());
    }
}
