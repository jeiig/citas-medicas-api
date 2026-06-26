# MediSalud - Sistema de Agendamiento de Citas Médicas

Este proyecto implementa el backend para el sistema de gestión de citas de MediSalud, diseñado bajo los principios de **Arquitectura Hexagonal** y **Domain-Driven Design (DDD)** táctico para garantizar la mantenibilidad, escalabilidad y aislamiento completo de las reglas de negocio.

## 🏛️ Arquitectura y Justificación
Se eligió una **Arquitectura Hexagonal (Ports & Adapters)** estructurada de la siguiente manera:
* **Domain:** El núcleo de la aplicación, libre de dependencias de frameworks (Spring, Hibernate). Contiene los modelos (`Doctor`, `Patient`, `Appointment`), los Value Objects (`AppointmentSlot` que blinda la integridad de las fechas) y las excepciones de negocio.
* **Application:** Define los casos de uso (Puertos de Entrada) y las interfaces de los repositorios (Puertos de Salida).
* **Infrastructure:** Implementa los adaptadores de entrada (Controladores REST con DTOs tipo `record` y validaciones nativas de Jakarta) y de salida (Persistencia con Spring Data JPA y base de datos H2).

**Justificación:** Esta separación garantiza que las reglas de negocio críticas (como la restricción de franjas de 30 minutos o el bloqueo por penalizaciones) estén blindadas contra cambios tecnológicos en la base de datos o el framework web.

## 🛠️ Tecnologías Utilizadas
* Java 21 / 17
* Spring Boot 3.x (Spring Web, Spring Data JPA)
* Base de Datos en memoria H2 (Elegida para facilitar la portabilidad y ejecución inmediata del evaluador sin configuraciones externas).
* JUnit 5 & Mockito (Para pruebas unitarias de alta velocidad sin levantar contexto).
* Jakarta Validation (Seguridad e integridad en inputs).

## 🚀 Instrucciones de Ejecución Local
1. Asegúrese de tener configurado Java 17 o superior en su entorno.
2. Clone el repositorio.
3. Ejecute el proyecto usando Maven desde la raíz:
   ```bash
   ./mvnw spring-boot:run