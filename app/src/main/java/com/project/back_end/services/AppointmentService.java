package com.project.back_end.services;

import com.project.back_end.models.Appointment;
import com.project.back_end.models.Doctor;
import com.project.back_end.models.Patient;
import com.project.back_end.Repositories.AppointmentRepository;
import com.project.back_end.Repositories.DoctorRepository;
import com.project.back_end.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    // Get all appointments
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // Get appointment by ID
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    // Create new appointment
    public Appointment createAppointment(Appointment appointment) {
        // Validate doctor exists
        Optional<Doctor> doctor = doctorRepository.findById(appointment.getDoctor().getId());
        if (!doctor.isPresent()) {
            throw new RuntimeException("Doctor not found with id: " + appointment.getDoctor().getId());
        }

        // Validate patient exists
        Optional<Patient> patient = patientRepository.findById(appointment.getPatient().getId());
        if (!patient.isPresent()) {
            throw new RuntimeException("Patient not found with id: " + appointment.getPatient().getId());
        }

        // Check for scheduling conflicts
        if (isAppointmentConflict(appointment.getDoctor().getId(), 
                                 appointment.getAppointmentDate(), 
                                 appointment.getAppointmentTime())) {
            throw new RuntimeException("Appointment time conflict for doctor");
        }

        appointment.setDoctor(doctor.get());
        appointment.setPatient(patient.get());
        return appointmentRepository.save(appointment);
    }

    // Update appointment
    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            
            appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
            appointment.setAppointmentTime(appointmentDetails.getAppointmentTime());
            appointment.setStatus(appointmentDetails.getStatus());
            appointment.setPurpose(appointmentDetails.getPurpose());
            appointment.setNotes(appointmentDetails.getNotes());

            return appointmentRepository.save(appointment);
        } else {
            throw new RuntimeException("Appointment not found with id: " + id);
        }
    }

    // Delete appointment
    public void deleteAppointment(Long id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Appointment not found with id: " + id);
        }
    }

    // Get appointments by doctor ID
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    // Get appointments by patient ID
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    // Get appointments by date
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentDate(date);
    }

    // Get appointments by doctor and date
    public List<Appointment> getAppointmentsByDoctorAndDate(Long doctorId, LocalDate date) {
        return appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date);
    }

    // Check for appointment conflict
    private boolean isAppointmentConflict(Long doctorId, LocalDate date, LocalTime time) {
        List<Appointment> existingAppointments = appointmentRepository
                .findByDoctorIdAndAppointmentDateAndAppointmentTime(doctorId, date, time);
        return !existingAppointments.isEmpty();
    }

    // Update appointment status
    public Appointment updateAppointmentStatus(Long id, String status) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            appointment.setStatus(status);
            return appointmentRepository.save(appointment);
        } else {
            throw new RuntimeException("Appointment not found with id: " + id);
        }
    }

    // Get today's appointments
    public List<Appointment> getTodaysAppointments() {
        return appointmentRepository.findByAppointmentDate(LocalDate.now());
    }
}
