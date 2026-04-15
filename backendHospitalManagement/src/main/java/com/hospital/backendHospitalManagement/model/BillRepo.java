package com.hospital.backendHospitalManagement.model;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface BillRepo extends CrudRepository<Bill, Long> {
    List<Bill> findByPatientId(Long patientId);
    List<Bill> findByAppointmentId(Long appointmentId);
}
