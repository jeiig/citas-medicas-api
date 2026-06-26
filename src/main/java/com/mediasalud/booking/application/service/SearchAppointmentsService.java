package com.mediasalud.booking.application.service;


import com.mediasalud.booking.application.portsInput.SearchAppointmentsUseCase;
import com.mediasalud.booking.application.portsOutput.AppointmentRepositoryPort;
import com.mediasalud.booking.domain.model.Appointment;
import com.mediasalud.booking.domain.model.AppointmentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SearchAppointmentsService implements SearchAppointmentsUseCase {

    private final AppointmentRepositoryPort appointmentRepository;

    public SearchAppointmentsService(AppointmentRepositoryPort appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Appointment> execute(String doctorId, String patientId, AppointmentStatus status, LocalDateTime start, LocalDateTime end) {
        // Regla de Negocio: Si no se especifican fechas, por defecto se filtran las citas de los próximos 7 días
        if (start == null && end == null) {
            start = LocalDateTime.now();
            end = start.plusDays(7);
        } else if (start == null) {
            start = LocalDateTime.now(); // Si solo viene fecha fin, asumimos desde hoy
        } else if (end == null) {
            end = start.plusDays(7); // Si solo viene fecha inicio, sumamos 7 días
        }

        return appointmentRepository.findAppointmentsByFilters(doctorId, patientId, status, start, end);
    }
}
