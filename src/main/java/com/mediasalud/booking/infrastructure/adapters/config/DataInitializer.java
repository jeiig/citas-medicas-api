package com.mediasalud.booking.infrastructure.adapters.config;


import com.mediasalud.booking.infrastructure.adapters.output.DoctorEntity;
import com.mediasalud.booking.infrastructure.adapters.output.PatientEntity;
import com.mediasalud.booking.infrastructure.adapters.output.repository.SpringDataDoctorRepository;
import com.mediasalud.booking.infrastructure.adapters.output.repository.SpringDataPatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final SpringDataDoctorRepository doctorRepository;
    private final SpringDataPatientRepository patientRepository;

    public DataInitializer(SpringDataDoctorRepository doctorRepository,
                           SpringDataPatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Precargar Médicos de prueba
        if (doctorRepository.count() == 0) {
            doctorRepository.save(new DoctorEntity("doc-01", "Dr. Camilo Restrepo", "Cardiología", "3101234567", "camilo.restrepo@medisalud.com"));
            doctorRepository.save(new DoctorEntity("doc-02", "Dra. Elena Gómez", "Pediatría", "3119876543", "elena.gomez@medisalud.com"));
            doctorRepository.save(new DoctorEntity("doc-03", "Dr. Marcos Pérez", "Medicina General", "3124567890", "marcos.perez@medisalud.com"));
            System.out.println(">> [DataInitializer] Médicos de prueba cargados correctamente en H2.");
        }

        // 2. Precargar Pacientes de prueba
        if (patientRepository.count() == 0) {
            patientRepository.save(new PatientEntity("pat-01", "Juan Carlos Mendoza", "1018456789", "3204087439", "juan.mendoza@email.com", LocalDate.of(1990, 5, 14)));
            patientRepository.save(new PatientEntity("pat-02", "Luisa Fernanda Ríos", "52345678", "3157778899", "luisa.rios@email.com", LocalDate.of(1995, 11, 22)));
            System.out.println(">> [DataInitializer] Pacientes de prueba cargados correctamente en H2.");
        }
    }
}
