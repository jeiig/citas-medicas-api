package com.mediasalud.booking.infrastructure.adapters.input.rest;


import com.mediasalud.booking.application.portsInput.BookAppointmentUseCase;
import com.mediasalud.booking.application.portsInput.CancelAppointmentUseCase;
import com.mediasalud.booking.application.portsInput.SearchAppointmentsUseCase;
import com.mediasalud.booking.domain.model.Appointment;
import com.mediasalud.booking.domain.model.AppointmentStatus;
import com.mediasalud.booking.infrastructure.adapters.input.dto.AppointmentResponse;
import com.mediasalud.booking.infrastructure.adapters.input.dto.BookAppointmentRequest;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final BookAppointmentUseCase bookAppointmentUseCase;
    private final CancelAppointmentUseCase cancelAppointmentUseCase;
    private final SearchAppointmentsUseCase searchAppointmentsUseCase;

    public AppointmentController(BookAppointmentUseCase bookAppointmentUseCase,
                                 CancelAppointmentUseCase cancelAppointmentUseCase,
                                 SearchAppointmentsUseCase searchAppointmentsUseCase) {
        this.bookAppointmentUseCase = bookAppointmentUseCase;
        this.cancelAppointmentUseCase = cancelAppointmentUseCase;
        this.searchAppointmentsUseCase = searchAppointmentsUseCase;
    }

    // RF-03: Agendar Cita
    @PostMapping
    public ResponseEntity<AppointmentResponse> bookAppointment(@Valid @RequestBody BookAppointmentRequest request) {
        Appointment appointment = bookAppointmentUseCase.execute(
                request.patientId(),
                request.doctorId(),
                request.dateTime()
        );
        return new ResponseEntity<>(AppointmentResponse.fromDomain(appointment), HttpStatus.CREATED);
    }

    // RF-05: Cancelar Cita
    @PutMapping("/{id}/cancel")
    public ResponseEntity<AppointmentResponse> cancelAppointment(@PathVariable String id) {
        Appointment appointment = cancelAppointmentUseCase.execute(id);
        return ResponseEntity.ok(AppointmentResponse.fromDomain(appointment));
    }

    // RF-04 y RF-06: Buscar Citas con Filtros Opcionales
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> searchAppointments(
            @RequestParam(required = false) String doctorId,
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) AppointmentStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        List<Appointment> appointments = searchAppointmentsUseCase.execute(doctorId, patientId, status, start, end);
        List<AppointmentResponse> response = appointments.stream()
                .map(AppointmentResponse::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
